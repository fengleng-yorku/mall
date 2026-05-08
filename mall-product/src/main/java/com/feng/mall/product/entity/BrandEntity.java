package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * brand
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * brand id
	 */
	@TableId
	private Long brandId;
	/**
	 * brand name
	 */
	private String name;
	/**
	 * brand logo url
	 */
	private String logo;
	/**
	 * description
	 */
	private String descript;
	/**
	 * display status [0-hidden; 1-visible]
	 */
	private Integer showStatus;
	/**
	 * first letter for search
	 */
	private String firstLetter;
	/**
	 * sort
	 */
	private Integer sort;

}
