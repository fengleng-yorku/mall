package com.feng.mall.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.warehouse.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * warehouse order task detail
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

