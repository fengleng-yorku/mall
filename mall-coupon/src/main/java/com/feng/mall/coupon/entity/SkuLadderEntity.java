package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * sku tiered price
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_sku_ladder")
public class SkuLadderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * spu_id
	 */
	private Long skuId;
	/**
	 * quantity threshold
	 */
	private Integer fullCount;
	/**
	 * discount rate
	 */
	private BigDecimal discount;
	/**
	 * discounted price
	 */
	private BigDecimal price;
	/**
	 * stackable with other promotions [0-not stackable, 1-stackable]
	 */
	private Integer addOther;

}
