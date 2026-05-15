package com.feng.mall.warehouse.Vo;

import lombok.Data;

@Data
public class PurchaseItemDoneVo {

    private Long itemId;

    private Integer status;

    private String reason;

}
