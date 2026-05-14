package com.feng.mall.warehouse.Vo;

import java.util.List;

import lombok.Data;

@Data
public class MergeVo {

    private Long purchaseId;

    private List<Long> items;

}