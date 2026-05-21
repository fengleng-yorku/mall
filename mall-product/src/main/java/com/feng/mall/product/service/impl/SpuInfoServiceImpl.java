package com.feng.mall.product.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.to.SkuReductionTo;
import com.feng.common.to.SpuBoundTo;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;
import com.feng.common.utils.R;

import com.feng.mall.product.dao.SpuInfoDao;
import com.feng.mall.product.entity.ProductAttrValueEntity;
import com.feng.mall.product.entity.SkuImagesEntity;
import com.feng.mall.product.entity.SkuInfoEntity;
import com.feng.mall.product.entity.SkuSaleAttrValueEntity;
import com.feng.mall.product.entity.SpuInfoDescEntity;
import com.feng.mall.product.entity.SpuInfoEntity;
import com.feng.mall.product.feign.CouponFeignService;
import com.feng.mall.product.service.AttrService;
import com.feng.mall.product.service.ProductAttrValueService;
import com.feng.mall.product.service.SkuImagesService;
import com.feng.mall.product.service.SkuInfoService;
import com.feng.mall.product.service.SkuSaleAttrValueService;
import com.feng.mall.product.service.SpuImagesService;
import com.feng.mall.product.service.SpuInfoDescService;
import com.feng.mall.product.service.SpuInfoService;
import com.feng.mall.product.vo.Attr;
import com.feng.mall.product.vo.BaseAttrs;
import com.feng.mall.product.vo.Bounds;
import com.feng.mall.product.vo.Images;
import com.feng.mall.product.vo.Skus;
import com.feng.mall.product.vo.SpuSaveVo;

@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService imagesService;

    @Autowired
    AttrService attrService;

    @Autowired
    ProductAttrValueService valueService;

    @Autowired
    SkuInfoService skuService;

    @Autowired
    SkuImagesService skuImageService;

    @Autowired
    SkuSaleAttrValueService saleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>());

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        // 1. save basic SPU info -> pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        // 2. save SPU description info -> pms_spu_desc
        List<String> desc = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",", desc));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        // 3. save SPU images -> pms_spu_images
        List<String> images = vo.getImages();
        imagesService.saveImages(spuInfoEntity.getId(), images);

        // 4. save SPU specification attributes -> pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collection = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();

            valueEntity.setAttrId(attr.getAttrId());
            valueEntity.setAttrName(attrService.getById(attr.getAttrId()).getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());

            return valueEntity;
        }).collect(Collectors.toList());
        valueService.saveProductAttrValue(collection);

        // 5. save SPU bonus/bounds info -> sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());

        R r = couponFeignService.saveSpubounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("Remote save SPU bounds info failed");
        }

        // 5. save SKUs for this SPU
        // 5.1 save basic SKU info -> pms_sku_info
        List<Skus> skus = vo.getSkus();
        if (skus != null && skus.size() != 0) {
            skus.forEach(sku -> {

                List<Images> skuImages = sku.getImages() != null ? sku.getImages() : List.of();

                String defaultImg = skuImages.stream()
                        .filter(img -> img.getDefaultImg() == 1)
                        .map(Images::getImgUrl)
                        .findFirst()
                        .orElse("");

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfoEntity);

                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                // 5.2 save SKU images -> pms_sku_images
                List<SkuImagesEntity> imagesEntities = skuImages.stream()
                        .filter(img -> img.getImgUrl() != null && !img.getImgUrl().isEmpty())
                        .map(img -> {
                            SkuImagesEntity imageEntity = new SkuImagesEntity();
                            imageEntity.setSkuId(skuId);
                            imageEntity.setImgUrl(img.getImgUrl());
                            imageEntity.setDefaultImg(img.getDefaultImg());
                            return imageEntity;
                        }).collect(Collectors.toList());
                skuImageService.saveBatch(imagesEntities);

                // 5.3 save SKU sale attributes -> pms_sku_sale_attr_value
                List<Attr> attrs = sku.getAttr() != null ? sku.getAttr() : List.of();
                List<SkuSaleAttrValueEntity> saleAttrCollection = attrs.stream().map(attr -> {
                    SkuSaleAttrValueEntity saleAttrEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, saleAttrEntity);
                    saleAttrEntity.setSkuId(skuId);
                    return saleAttrEntity;
                }).collect(Collectors.toList());
                saleAttrValueService.saveBatch(saleAttrCollection);

                // 5.4 save SKU discount info -> sms_sku_ladder, sms_sku_full_reduction,
                // sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                if (r1.getCode() != 0) {
                    log.error("Remote save SKU discount info failed");
                }

            });
        }

    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<SpuInfoEntity>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId)) {
            wrapper.eq("brand_id", brandId);
        }

        String catalogId = (String) params.get("catalogId");
        if (!StringUtils.isEmpty(catalogId)) {
            wrapper.eq("catalog_id", catalogId);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'up'");
    }

}