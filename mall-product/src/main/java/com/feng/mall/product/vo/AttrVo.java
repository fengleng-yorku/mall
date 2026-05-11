package com.feng.mall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AttrVo {

    /**
     * attribute id
     */
    @TableId
    private Long attrId;
    /**
     * attribute name
     */
    private String attrName;
    /**
     * searchable [0-no, 1-yes]
     */
    private Integer searchType;
    /**
     * value type [0-single value, 1-multiple values]
     */
    private Integer valueType;
    /**
     * attribute icon
     */
    private String icon;
    /**
     * selectable value list [comma-separated]
     */
    private String valueSelect;
    /**
     * attribute type [0-sale attr, 1-base attr, 2-both]
     */
    private Integer attrType;
    /**
     * enable status [0-disabled, 1-enabled]
     */
    private Long enable;
    /**
     * category
     */
    private Long catelogId;
    /**
     * quick display [show in description: 0-no, 1-yes]; still adjustable in SKU
     */
    private Integer showDesc;

    private Long attrGroupId;

}
