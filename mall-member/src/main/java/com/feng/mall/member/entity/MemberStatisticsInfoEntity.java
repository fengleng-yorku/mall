package com.feng.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member statistics info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
@Data
@TableName("ums_member_statistics_info")
public class MemberStatisticsInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member id
	 */
	private Long memberId;
	/**
	 * total consumption amount
	 */
	private BigDecimal consumeAmount;
	/**
	 * total discount amount
	 */
	private BigDecimal couponAmount;
	/**
	 * order count
	 */
	private Integer orderCount;
	/**
	 * coupon count
	 */
	private Integer couponCount;
	/**
	 * comment count
	 */
	private Integer commentCount;
	/**
	 * return order count
	 */
	private Integer returnOrderCount;
	/**
	 * login count
	 */
	private Integer loginCount;
	/**
	 * follow count
	 */
	private Integer attendCount;
	/**
	 * fans count
	 */
	private Integer fansCount;
	/**
	 * collected product count
	 */
	private Integer collectProductCount;
	/**
	 * collected subject count
	 */
	private Integer collectSubjectCount;
	/**
	 * collected comment count
	 */
	private Integer collectCommentCount;
	/**
	 * invited friend count
	 */
	private Integer inviteFriendCount;

}
