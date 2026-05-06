package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * home page subject (each subject links to a new page showing subject product info)
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_home_subject")
public class HomeSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * subject name
	 */
	private String name;
	/**
	 * subject title
	 */
	private String title;
	/**
	 * subject subtitle
	 */
	private String subTitle;
	/**
	 * display status
	 */
	private Integer status;
	/**
	 * detail url
	 */
	private String url;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * subject image url
	 */
	private String img;

}
