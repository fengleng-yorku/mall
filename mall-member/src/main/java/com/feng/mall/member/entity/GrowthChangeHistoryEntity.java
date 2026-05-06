package com.feng.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * growth points change history
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:21:11
 */
@Data
@TableName("ums_growth_change_history")
public class GrowthChangeHistoryEntity implements Serializable {
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
	 * create_time
	 */
	private Date createTime;
	/**
	 * change value (positive or negative)
	 */
	private Integer changeCount;
	/**
	 * note
	 */
	private String note;
	/**
	 * source type [0-shopping, 1-admin adjustment]
	 */
	private Integer sourceType;

}
