package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * flash sale promotion
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_seckill_promotion")
public class SeckillPromotionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * promotion title
	 */
	private String title;
	/**
	 * start date
	 */
	private Date startTime;
	/**
	 * end date
	 */
	private Date endTime;
	/**
	 * online/offline status
	 */
	private Integer status;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * creator
	 */
	private Long userId;

}
