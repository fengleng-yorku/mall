package com.feng.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * spu points bounds settings
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:01:06
 */
@Data
@TableName("sms_spu_bounds")
public class SpuBoundsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long spuId;
	/**
	 * growth points bounds
	 */
	private BigDecimal growBounds;
	/**
	 * shopping points bounds
	 */
	private BigDecimal buyBounds;
	/**
	 * promotion effect flags [4-bit, right to left: 0-no promo, growth points granted; 1-no promo, shopping points granted; 2-with promo, growth points granted; 3-with promo, shopping points granted; bit 0=not granted, 1=granted]
	 */
	private Integer work;

}
