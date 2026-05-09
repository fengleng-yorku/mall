package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import jakarta.validation.constraints.*;
import com.feng.common.valid.*;
import java.io.Serializable;

import org.hibernate.validator.constraints.URL;

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
	@NotNull(groups = { UpdateGroup.class }, message = "brandId must not be null when updating")
	@Null(groups = { AddGroup.class }, message = "brandId must be null when creating")
	@TableId
	private Long brandId;
	/**
	 * brand name
	 */
	@NotBlank(groups = { UpdateGroup.class, AddGroup.class }, message = "brand name must not be empty")
	private String name;
	/**
	 * brand logo url
	 */
	@NotEmpty(groups = { AddGroup.class }, message = "logo must not be empty when creating")
	@URL(message = "logo must be a valid URL")
	private String logo;
	/**
	 * description
	 */
	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class }, message = "description must not be empty when creating")
	private String descript;
	/**
	 * display status [0-hidden; 1-visible]
	 */
	@NotNull(groups = { UpdateGroup.class, AddGroup.class }, message = "showStatus must not be null")
	@ListValue(vals = { 0, 1 }, groups = {
			AddGroup.class,UpdateGroup.class,UpdateStatusGroup.class }, message = "showStatus must be either 0 (hidden) or 1 (visible)")
	private Integer showStatus;
	/**
	 * first letter for search
	 */
	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class }, message = "firstLetter must not be empty when creating")
	@Pattern(regexp = "^[a-zA-Z]$", message = "first letter must be a single alphabet character")
	private String firstLetter;
	/**
	 * sort
	 */
	@NotNull(groups = { UpdateGroup.class, AddGroup.class }, message = "sort must not be null")
	@Min(value = 0, message = "sort must be a non-negative integer")
	private Integer sort;

}
