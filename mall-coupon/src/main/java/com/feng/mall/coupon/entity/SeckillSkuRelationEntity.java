package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * flash sale sku relation
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * promotion id
	 */
	private Long promotionId;
	/**
	 * session id
	 */
	private Long promotionSessionId;
	/**
	 * product id
	 */
	private Long skuId;
	/**
	 * flash sale price
	 */
	private BigDecimal seckillPrice;
	/**
	 * flash sale total quantity
	 */
	private BigDecimal seckillCount;
	/**
	 * per-person purchase limit
	 */
	private BigDecimal seckillLimit;
	/**
	 * sort
	 */
	private Integer seckillSort;

}
