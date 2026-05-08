package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * order settings
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order_setting")
public class OrderSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * flash sale order timeout close time (minutes)
	 */
	private Integer flashOrderOvertime;
	/**
	 * normal order timeout time (minutes)
	 */
	private Integer normalOrderOvertime;
	/**
	 * auto-confirm receipt after shipment (days)
	 */
	private Integer confirmOvertime;
	/**
	 * auto-complete transaction period, no returns allowed (days)
	 */
	private Integer finishOvertime;
	/**
	 * auto-positive-review period after order completion (days)
	 */
	private Integer commentOvertime;
	/**
	 * member level [0-all levels; other-corresponding member level]
	 */
	private Integer memberLevel;

}
