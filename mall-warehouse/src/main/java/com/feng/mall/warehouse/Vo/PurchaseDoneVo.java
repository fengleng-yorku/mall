package com.feng.mall.warehouse.Vo;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseDoneVo {

    @NotNull(message = "id can not be null")
    private Long id;

    @NotNull(message = "items can not be null")
    private List<PurchaseItemDoneVo> items;

}
