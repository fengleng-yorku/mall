package com.feng.mall.product.dao;

import com.feng.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * product three-level category
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
