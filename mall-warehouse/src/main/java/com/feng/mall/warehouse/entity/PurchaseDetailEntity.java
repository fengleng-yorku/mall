package com.feng.mall.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
@Data
@TableName("wms_purchase_detail")
public class PurchaseDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * purchase order id
	 */
	private Long purchaseId;
	/**
	 * purchase sku id
	 */
	private Long skuId;
	/**
	 * purchase quantity
	 */
	private Integer skuNum;
	/**
	 * purchase amount
	 */
	private BigDecimal skuPrice;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * status [0-new, 1-assigned, 2-purchasing, 3-completed, 4-failed]
	 */
	private Integer status;

}
