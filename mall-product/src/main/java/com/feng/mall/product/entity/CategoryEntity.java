package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * product three-level category
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * category id
	 */
	@TableId
	private Long catId;
	/**
	 * category name
	 */
	private String name;
	/**
	 * parent category id
	 */
	private Long parentCid;
	/**
	 * level
	 */
	private Integer catLevel;
	/**
	 * display status [0-hidden, 1-visible]
	 */
	private Integer showStatus;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * icon url
	 */
	private String icon;
	/**
	 * unit of measure
	 */
	private String productUnit;
	/**
	 * product count
	 */
	private Integer productCount;

}
