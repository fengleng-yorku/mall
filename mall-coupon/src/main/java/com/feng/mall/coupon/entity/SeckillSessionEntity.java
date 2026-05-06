package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * flash sale session
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_seckill_session")
public class SeckillSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * session name
	 */
	private String name;
	/**
	 * daily start time
	 */
	private Date startTime;
	/**
	 * daily end time
	 */
	private Date endTime;
	/**
	 * enable status
	 */
	private Integer status;
	/**
	 * create time
	 */
	private Date createTime;

}
