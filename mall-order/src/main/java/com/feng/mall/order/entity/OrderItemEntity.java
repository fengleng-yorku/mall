package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order item
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order_item")
public class OrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order_id
	 */
	private Long orderId;
	/**
	 * order_sn
	 */
	private String orderSn;
	/**
	 * spu_id
	 */
	private Long spuId;
	/**
	 * spu_name
	 */
	private String spuName;
	/**
	 * spu_pic
	 */
	private String spuPic;
	/**
	 * brand
	 */
	private String spuBrand;
	/**
	 * product category id
	 */
	private Long categoryId;
	/**
	 * product sku id
	 */
	private Long skuId;
	/**
	 * product sku name
	 */
	private String skuName;
	/**
	 * product sku image
	 */
	private String skuPic;
	/**
	 * product sku price
	 */
	private BigDecimal skuPrice;
	/**
	 * product purchase quantity
	 */
	private Integer skuQuantity;
	/**
	 * product sale attribute combination (JSON)
	 */
	private String skuAttrsVals;
	/**
	 * product promotion breakdown amount
	 */
	private BigDecimal promotionAmount;
	/**
	 * coupon discount breakdown amount
	 */
	private BigDecimal couponAmount;
	/**
	 * points discount breakdown amount
	 */
	private BigDecimal integrationAmount;
	/**
	 * product amount after discount breakdown
	 */
	private BigDecimal realAmount;
	/**
	 * bonus points
	 */
	private Integer giftIntegration;
	/**
	 * bonus growth points
	 */
	private Integer giftGrowth;

}
