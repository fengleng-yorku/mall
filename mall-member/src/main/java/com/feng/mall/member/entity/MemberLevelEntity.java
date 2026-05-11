package com.feng.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * member level
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
@Data
@TableName("ums_member_level")
public class MemberLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * level name
	 */
	private String name;
	/**
	 * growth points required for this level
	 */
	private Integer growthPoint;
	/**
	 * is default level [0->no; 1->yes]
	 */
	private Integer defaultStatus;
	/**
	 * free shipping threshold
	 */
	private BigDecimal freeFreightPoint;
	/**
	 * growth points earned per review
	 */
	private Integer commentGrowthPoint;
	/**
	 * free shipping privilege
	 */
	private Integer priviledgeFreeFreight;
	/**
	 * member price privilege
	 */
	private Integer priviledgeMemberPrice;
	/**
	 * birthday privilege
	 */
	private Integer priviledgeBirthday;
	/**
	 * note
	 */
	private String note;

}
