package com.feng.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * product comment
 * 
 * @author feng
 * @email lengfeng1183@gmail.com
 * @date 2026-05-05 18:55:11
 */
@Data
@TableName("pms_spu_comment")
public class SpuCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * spu_id
	 */
	private Long spuId;
	/**
	 * product name
	 */
	private String spuName;
	/**
	 * member nickname
	 */
	private String memberNickName;
	/**
	 * star rating
	 */
	private Integer star;
	/**
	 * member ip
	 */
	private String memberIp;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * display status [0-hidden, 1-visible]
	 */
	private Integer showStatus;
	/**
	 * attribute combination at purchase
	 */
	private String spuAttributes;
	/**
	 * likes count
	 */
	private Integer likesCount;
	/**
	 * reply count
	 */
	private Integer replyCount;
	/**
	 * comment media (images/videos) [JSON: [{type:file_type, url:resource_url}]]
	 */
	private String resources;
	/**
	 * content
	 */
	private String content;
	/**
	 * member avatar
	 */
	private String memberIcon;
	/**
	 * comment type [0-direct product comment, 1-reply to comment]
	 */
	private Integer commentType;

}
