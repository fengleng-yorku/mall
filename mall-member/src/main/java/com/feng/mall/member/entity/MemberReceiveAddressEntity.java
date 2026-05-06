package com.feng.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member receive address
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
@Data
@TableName("ums_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member_id
	 */
	private Long memberId;
	/**
	 * recipient name
	 */
	private String name;
	/**
	 * phone
	 */
	private String phone;
	/**
	 * postal code
	 */
	private String postCode;
	/**
	 * province/municipality
	 */
	private String province;
	/**
	 * city
	 */
	private String city;
	/**
	 * district
	 */
	private String region;
	/**
	 * detailed address (street)
	 */
	private String detailAddress;
	/**
	 * region code
	 */
	private String areacode;
	/**
	 * is default
	 */
	private Integer defaultStatus;

}
