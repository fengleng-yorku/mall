package com.feng.mall.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * warehouse info
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-06 11:55:53
 */
@Data
@TableName("wms_ware_info")
public class WareInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * warehouse name
	 */
	private String name;
	/**
	 * warehouse address
	 */
	private String address;
	/**
	 * area code
	 */
	private String areacode;

}
