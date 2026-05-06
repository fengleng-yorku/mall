package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order return application
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order_return_apply")
public class OrderReturnApplyEntity implements Serializable {
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
	 * return item id
	 */
	private Long skuId;
	/**
	 * order number
	 */
	private String orderSn;
	/**
	 * application time
	 */
	private Date createTime;
	/**
	 * member username
	 */
	private String memberUsername;
	/**
	 * refund amount
	 */
	private BigDecimal returnAmount;
	/**
	 * returner name
	 */
	private String returnName;
	/**
	 * returner phone
	 */
	private String returnPhone;
	/**
	 * status [0->pending; 1->returning; 2->completed; 3->rejected]
	 */
	private Integer status;
	/**
	 * handling time
	 */
	private Date handleTime;
	/**
	 * product image
	 */
	private String skuImg;
	/**
	 * product name
	 */
	private String skuName;
	/**
	 * product brand
	 */
	private String skuBrand;
	/**
	 * product sale attributes (JSON)
	 */
	private String skuAttrsVals;
	/**
	 * return quantity
	 */
	private Integer skuCount;
	/**
	 * product unit price
	 */
	private BigDecimal skuPrice;
	/**
	 * actual paid unit price
	 */
	private BigDecimal skuRealPrice;
	/**
	 * reason
	 */
	private String reason;
	/**
	 * description
	 */
	private String description;
	/**
	 * proof images, comma-separated
	 */
	private String descPics;
	/**
	 * handling note
	 */
	private String handleNote;
	/**
	 * handler
	 */
	private String handleMan;
	/**
	 * receiver
	 */
	private String receiveMan;
	/**
	 * receive time
	 */
	private Date receiveTime;
	/**
	 * receive note
	 */
	private String receiveNote;
	/**
	 * receive phone
	 */
	private String receivePhone;
	/**
	 * company receive address
	 */
	private String companyAddress;

}
