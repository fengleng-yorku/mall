package com.feng.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
@Data
@TableName("ums_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member level id
	 */
	private Long levelId;
	/**
	 * username
	 */
	private String username;
	/**
	 * password
	 */
	private String password;
	/**
	 * nickname
	 */
	private String nickname;
	/**
	 * mobile number
	 */
	private String mobile;
	/**
	 * email
	 */
	private String email;
	/**
	 * avatar
	 */
	private String header;
	/**
	 * gender
	 */
	private Integer gender;
	/**
	 * birthday
	 */
	private Date birth;
	/**
	 * city
	 */
	private String city;
	/**
	 * occupation
	 */
	private String job;
	/**
	 * personal signature
	 */
	private String sign;
	/**
	 * registration source
	 */
	private Integer sourceType;
	/**
	 * points
	 */
	private Integer integration;
	/**
	 * growth points
	 */
	private Integer growth;
	/**
	 * enable status
	 */
	private Integer status;
	/**
	 * registration time
	 */
	private Date createTime;

}
