package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * refund info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_refund_info")
public class RefundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * refund order
	 */
	private Long orderReturnId;
	/**
	 * refund amount
	 */
	private BigDecimal refund;
	/**
	 * refund transaction number
	 */
	private String refundSn;
	/**
	 * refund status
	 */
	private Integer refundStatus;
	/**
	 * refund channel [1-Alipay, 2-WeChat, 3-UnionPay, 4-wire transfer]
	 */
	private Integer refundChannel;
	/**
	 * 
	 */
	private String refundContent;

}
