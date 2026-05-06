package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sku info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_sku_info")
public class SkuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * skuId
	 */
	@TableId
	private Long skuId;
	/**
	 * spuId
	 */
	private Long spuId;
	/**
	 * sku name
	 */
	private String skuName;
	/**
	 * sku description
	 */
	private String skuDesc;
	/**
	 * category id
	 */
	private Long catalogId;
	/**
	 * brand id
	 */
	private Long brandId;
	/**
	 * default image
	 */
	private String skuDefaultImg;
	/**
	 * title
	 */
	private String skuTitle;
	/**
	 * subtitle
	 */
	private String skuSubtitle;
	/**
	 * price
	 */
	private BigDecimal price;
	/**
	 * sales count
	 */
	private Long saleCount;

}
