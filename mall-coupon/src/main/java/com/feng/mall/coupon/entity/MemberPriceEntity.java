package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * product member price
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_member_price")
public class MemberPriceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * member level id
	 */
	private Long memberLevelId;
	/**
	 * member level name
	 */
	private String memberLevelName;
	/**
	 * member price
	 */
	private BigDecimal memberPrice;
	/**
	 * stackable with other promotions [0-not stackable, 1-stackable]
	 */
	private Integer addOther;

}
