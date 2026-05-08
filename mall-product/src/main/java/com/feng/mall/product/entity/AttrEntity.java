package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * product attribute
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * attribute id
	 */
	@TableId
	private Long attrId;
	/**
	 * attribute name
	 */
	private String attrName;
	/**
	 * searchable [0-no, 1-yes]
	 */
	private Integer searchType;
	/**
	 * value type [0-single value, 1-multiple values]
	 */
	private Integer valueType;
	/**
	 * attribute icon
	 */
	private String icon;
	/**
	 * selectable value list [comma-separated]
	 */
	private String valueSelect;
	/**
	 * attribute type [0-sale attr, 1-base attr, 2-both]
	 */
	private Integer attrType;
	/**
	 * enable status [0-disabled, 1-enabled]
	 */
	private Long enable;
	/**
	 * category
	 */
	private Long catelogId;
	/**
	 * quick display [show in description: 0-no, 1-yes]; still adjustable in SKU
	 */
	private Integer showDesc;

}
