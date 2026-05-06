package com.feng.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * points change history
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

