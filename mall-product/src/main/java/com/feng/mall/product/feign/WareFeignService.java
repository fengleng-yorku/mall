package com.feng.mall.product.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.feng.common.to.SkuHasStockTo;
import com.feng.common.utils.R;

@FeignClient("mall-warehouse")
public interface WareFeignService {

    @PostMapping("/ware/waresku/hasstock")
    R<List<SkuHasStockTo>> getSkusHasStock(@RequestBody List<Long> skuIds);

}
