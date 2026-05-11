package com.feng.mall.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * purchase info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
@Data
@TableName("wms_purchase")
public class PurchaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * purchase order id
	 */
	@TableId
	private Long id;
	/**
	 * purchaser id
	 */
	private Long assigneeId;
	/**
	 * purchaser name
	 */
	private String assigneeName;
	/**
	 * contact
	 */
	private String phone;
	/**
	 * priority
	 */
	private Integer priority;
	/**
	 * status
	 */
	private Integer status;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * total amount
	 */
	private BigDecimal amount;
	/**
	 * create date
	 */
	private Date createTime;
	/**
	 * update date
	 */
	private Date updateTime;

}
