package com.feng.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.common.utils.PageUtils;
import com.feng.mall.product.entity.AttrGroupEntity;
import com.feng.mall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * Service interface for attribute group operations.
 *
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /** Paginate all attribute groups with no category filter. */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * Paginate attribute groups filtered by category.
     * Pass catelogId = 0 to skip the category filter and return all groups.
     */
    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
    
}

