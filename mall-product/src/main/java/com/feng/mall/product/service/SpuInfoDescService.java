package com.feng.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu info description
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

