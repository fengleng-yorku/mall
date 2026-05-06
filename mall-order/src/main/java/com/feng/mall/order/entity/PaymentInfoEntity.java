package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * payment info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_payment_info")
public class PaymentInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order number (external business number)
	 */
	private String orderSn;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * Alipay transaction number
	 */
	private String alipayTradeNo;
	/**
	 * total payment amount
	 */
	private BigDecimal totalAmount;
	/**
	 * transaction content
	 */
	private String subject;
	/**
	 * payment status
	 */
	private String paymentStatus;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * confirm time
	 */
	private Date confirmTime;
	/**
	 * callback content
	 */
	private String callbackContent;
	/**
	 * callback time
	 */
	private Date callbackTime;

}
