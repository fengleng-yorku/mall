package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order operation history
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order_operate_history")
public class OrderOperateHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * operator [user; system; admin]
	 */
	private String operateMan;
	/**
	 * operation time
	 */
	private Date createTime;
	/**
	 * order status [0->pending payment; 1->pending shipment; 2->shipped; 3->completed; 4->closed; 5->invalid]
	 */
	private Integer orderStatus;
	/**
	 * note
	 */
	private String note;

}
