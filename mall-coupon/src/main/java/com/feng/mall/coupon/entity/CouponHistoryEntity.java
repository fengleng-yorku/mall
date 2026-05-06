package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * coupon claim history
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_coupon_history")
public class CouponHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * coupon id
	 */
	private Long couponId;
	/**
	 * member id
	 */
	private Long memberId;
	/**
	 * member name
	 */
	private String memberNickName;
	/**
	 * acquisition type [0->admin granted; 1->self claimed]
	 */
	private Integer getType;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * usage status [0->unused; 1->used; 2->expired]
	 */
	private Integer useType;
	/**
	 * use time
	 */
	private Date useTime;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * order number
	 */
	private Long orderSn;

}
