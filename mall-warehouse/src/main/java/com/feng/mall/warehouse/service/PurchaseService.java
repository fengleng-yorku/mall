package com.feng.mall.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.warehouse.entity.PurchaseEntity;

import java.util.Map;

/**
 * purchase info
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

