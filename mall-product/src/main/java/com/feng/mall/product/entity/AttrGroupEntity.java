package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * attribute group
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * group id
	 */
	@TableId
	private Long attrGroupId;
	/**
	 * group name
	 */
	private String attrGroupName;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * description
	 */
	private String descript;
	/**
	 * group icon
	 */
	private String icon;
	/**
	 * category id
	 */
	private Long catelogId;

}
