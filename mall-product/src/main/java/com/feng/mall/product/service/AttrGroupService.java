package com.feng.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * attribute group
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);
}

