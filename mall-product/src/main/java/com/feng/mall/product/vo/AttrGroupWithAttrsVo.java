package com.feng.mall.product.vo;

import java.util.List;

import com.feng.mall.product.entity.AttrEntity;

import lombok.Data;

@Data
public class AttrGroupWithAttrsVo {

    /**
     * group id
     */
    private Long attrGroupId;
    /**
     * group name
     */
    private String attrGroupName;
    /**
     * sort
     */
    private Integer sort;
    /**
     * description
     */
    private String descript;
    /**
     * group icon
     */
    private String icon;
    /**
     * category id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;

}
