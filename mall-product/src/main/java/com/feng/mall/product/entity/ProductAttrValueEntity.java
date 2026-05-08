package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * spu attribute value
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_product_attr_value")
public class ProductAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * product id
	 */
	private Long spuId;
	/**
	 * attribute id
	 */
	private Long attrId;
	/**
	 * attribute name
	 */
	private String attrName;
	/**
	 * attribute value
	 */
	private String attrValue;
	/**
	 * sort order
	 */
	private Integer attrSort;
	/**
	 * quick display [show in description: 0-no, 1-yes]
	 */
	private Integer quickShow;

}
