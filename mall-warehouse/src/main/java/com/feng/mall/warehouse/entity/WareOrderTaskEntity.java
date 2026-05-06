package com.feng.mall.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * warehouse order task
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
@Data
@TableName("wms_ware_order_task")
public class WareOrderTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order_id
	 */
	private Long orderId;
	/**
	 * order_sn
	 */
	private String orderSn;
	/**
	 * consignee
	 */
	private String consignee;
	/**
	 * consignee phone
	 */
	private String consigneeTel;
	/**
	 * delivery address
	 */
	private String deliveryAddress;
	/**
	 * order note
	 */
	private String orderComment;
	/**
	 * payment method [1:online payment, 2:COD]
	 */
	private Integer paymentWay;
	/**
	 * task status
	 */
	private Integer taskStatus;
	/**
	 * order description
	 */
	private String orderBody;
	/**
	 * tracking number
	 */
	private String trackingNo;
	/**
	 * create_time
	 */
	private Date createTime;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * task note
	 */
	private String taskComment;

}
