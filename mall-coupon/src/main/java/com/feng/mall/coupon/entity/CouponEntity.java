package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * coupon info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * coupon type [0->general gift; 1->member gift; 2->shopping gift; 3->registration gift]
	 */
	private Integer couponType;
	/**
	 * coupon image
	 */
	private String couponImg;
	/**
	 * coupon name
	 */
	private String couponName;
	/**
	 * quantity
	 */
	private Integer num;
	/**
	 * amount
	 */
	private BigDecimal amount;
	/**
	 * per-person claim limit
	 */
	private Integer perLimit;
	/**
	 * minimum spend threshold
	 */
	private BigDecimal minPoint;
	/**
	 * start time
	 */
	private Date startTime;
	/**
	 * end time
	 */
	private Date endTime;
	/**
	 * usage type [0->all products; 1->specific category; 2->specific product]
	 */
	private Integer useType;
	/**
	 * note
	 */
	private String note;
	/**
	 * issued quantity
	 */
	private Integer publishCount;
	/**
	 * used quantity
	 */
	private Integer useCount;
	/**
	 * claimed quantity
	 */
	private Integer receiveCount;
	/**
	 * claim start date
	 */
	private Date enableStartTime;
	/**
	 * claim end date
	 */
	private Date enableEndTime;
	/**
	 * promo code
	 */
	private String code;
	/**
	 * claimable member level [0->all levels; other->corresponding level]
	 */
	private Integer memberLevel;
	/**
	 * publish status [0-unpublished, 1-published]
	 */
	private Integer publish;

}
