package com.feng.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * flash sale promotion
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

