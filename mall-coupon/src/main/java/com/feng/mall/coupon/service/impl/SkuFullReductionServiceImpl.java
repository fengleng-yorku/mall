package com.feng.mall.coupon.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.common.to.MemberPrice;
import com.feng.common.to.SkuReductionTo;
import com.feng.common.utils.PageUtils;
import com.feng.common.utils.Query;

import com.feng.mall.coupon.dao.SkuFullReductionDao;
import com.feng.mall.coupon.entity.MemberPriceEntity;
import com.feng.mall.coupon.entity.SkuFullReductionEntity;
import com.feng.mall.coupon.entity.SkuLadderEntity;
import com.feng.mall.coupon.service.MemberPriceService;
import com.feng.mall.coupon.service.SkuFullReductionService;
import com.feng.mall.coupon.service.SkuLadderService;

@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity>
        implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>());

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo reductionTo) {

        if (reductionTo.getFullCount() > 0) {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            skuLadderEntity.setSkuId(reductionTo.getSkuId());
            skuLadderEntity.setFullCount(reductionTo.getFullCount());
            skuLadderEntity.setDiscount(reductionTo.getDiscount());
            skuLadderEntity.setAddOther(reductionTo.getCountStatus());
            skuLadderService.save(skuLadderEntity);
        }

        if (reductionTo.getFullPrice() != null && reductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            SkuFullReductionEntity reductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(reductionTo, reductionEntity);
            this.save(reductionEntity);
        }

        List<MemberPrice> memberPrice = reductionTo.getMemberPrice();
        if (memberPrice == null || memberPrice.isEmpty()) {
            return;
        }
        List<MemberPriceEntity> collction = memberPrice.stream().map(item -> {
            MemberPriceEntity priceEntity = new MemberPriceEntity();
            priceEntity.setSkuId(reductionTo.getSkuId());
            priceEntity.setMemberLevelId(item.getId());
            priceEntity.setMemberLevelName(item.getName());
            priceEntity.setMemberPrice(item.getPrice());
            priceEntity.setAddOther(1);

            return priceEntity;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collction);

    }

}