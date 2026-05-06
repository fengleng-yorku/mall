package com.feng.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:53:05
 */
@Data
@TableName("oms_order")
public class OrderEntity implements Serializable {
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
	 * order number
	 */
	private String orderSn;
	/**
	 * coupon used
	 */
	private Long couponId;
	/**
	 * create_time
	 */
	private Date createTime;
	/**
	 * username
	 */
	private String memberUsername;
	/**
	 * order total amount
	 */
	private BigDecimal totalAmount;
	/**
	 * amount payable
	 */
	private BigDecimal payAmount;
	/**
	 * freight amount
	 */
	private BigDecimal freightAmount;
	/**
	 * promotion discount amount (promo price, spend-save, tiered price)
	 */
	private BigDecimal promotionAmount;
	/**
	 * points deduction amount
	 */
	private BigDecimal integrationAmount;
	/**
	 * coupon deduction amount
	 */
	private BigDecimal couponAmount;
	/**
	 * admin-adjusted discount amount
	 */
	private BigDecimal discountAmount;
	/**
	 * payment method [1->Alipay; 2->WeChat; 3->UnionPay; 4->COD]
	 */
	private Integer payType;
	/**
	 * order source [0->PC; 1->App]
	 */
	private Integer sourceType;
	/**
	 * order status [0->pending payment; 1->pending shipment; 2->shipped; 3->completed; 4->closed; 5->invalid]
	 */
	private Integer status;
	/**
	 * shipping carrier (delivery method)
	 */
	private String deliveryCompany;
	/**
	 * tracking number
	 */
	private String deliverySn;
	/**
	 * auto-confirm period (days)
	 */
	private Integer autoConfirmDay;
	/**
	 * points to be earned
	 */
	private Integer integration;
	/**
	 * growth points to be earned
	 */
	private Integer growth;
	/**
	 * invoice type [0->no invoice; 1->e-invoice; 2->paper invoice]
	 */
	private Integer billType;
	/**
	 * invoice title
	 */
	private String billHeader;
	/**
	 * invoice content
	 */
	private String billContent;
	/**
	 * invoice recipient phone
	 */
	private String billReceiverPhone;
	/**
	 * invoice recipient email
	 */
	private String billReceiverEmail;
	/**
	 * recipient name
	 */
	private String receiverName;
	/**
	 * recipient phone
	 */
	private String receiverPhone;
	/**
	 * recipient postal code
	 */
	private String receiverPostCode;
	/**
	 * province/municipality
	 */
	private String receiverProvince;
	/**
	 * city
	 */
	private String receiverCity;
	/**
	 * district
	 */
	private String receiverRegion;
	/**
	 * detailed address
	 */
	private String receiverDetailAddress;
	/**
	 * order note
	 */
	private String note;
	/**
	 * receipt confirmation status [0->unconfirmed; 1->confirmed]
	 */
	private Integer confirmStatus;
	/**
	 * delete status [0->not deleted; 1->deleted]
	 */
	private Integer deleteStatus;
	/**
	 * points used when placing order
	 */
	private Integer useIntegration;
	/**
	 * payment time
	 */
	private Date paymentTime;
	/**
	 * shipment time
	 */
	private Date deliveryTime;
	/**
	 * receipt confirmation time
	 */
	private Date receiveTime;
	/**
	 * review time
	 */
	private Date commentTime;
	/**
	 * modification time
	 */
	private Date modifyTime;

}
