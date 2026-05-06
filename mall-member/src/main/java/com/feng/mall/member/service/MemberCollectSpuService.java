package com.feng.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * member collected products
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

