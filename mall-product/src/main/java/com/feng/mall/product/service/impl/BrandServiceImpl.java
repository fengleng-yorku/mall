package com.feng.mall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;

import com.feng.mall.product.dao.BrandDao;
import com.feng.mall.product.entity.BrandEntity;
import com.feng.mall.product.service.BrandService;
import com.feng.mall.product.service.CategoryBrandRelationService;

import ch.qos.logback.core.util.StringUtil;

@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        if (key != null && !key.trim().isEmpty()) {
            queryWrapper.eq("brand_id", key).or().like("name", key);
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper);

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        if (!StringUtil.isNullOrEmpty(brand.getName())) {
            // 同步更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }

    }

}