package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * home page carousel ad
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_home_adv")
public class HomeAdvEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * name
	 */
	private String name;
	/**
	 * image url
	 */
	private String pic;
	/**
	 * start time
	 */
	private Date startTime;
	/**
	 * end time
	 */
	private Date endTime;
	/**
	 * status
	 */
	private Integer status;
	/**
	 * click count
	 */
	private Integer clickCount;
	/**
	 * ad detail url
	 */
	private String url;
	/**
	 * note
	 */
	private String note;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * publisher
	 */
	private Long publisherId;
	/**
	 * reviewer
	 */
	private Long authId;

}
