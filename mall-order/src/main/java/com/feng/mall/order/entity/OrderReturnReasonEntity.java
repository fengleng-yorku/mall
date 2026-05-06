package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order return reason
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order_return_reason")
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * return reason name
	 */
	private String name;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * enable status
	 */
	private Integer status;
	/**
	 * create_time
	 */
	private Date createTime;

}
