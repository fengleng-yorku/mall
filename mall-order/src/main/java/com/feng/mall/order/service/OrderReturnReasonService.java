package com.feng.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * order return reason
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

