package com.feng.mall.product.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.feng.common.to.es.SkuEsModel;
import com.feng.common.utils.R;

@FeignClient("mall-search")
public interface SearchFeignService {

    @PostMapping("/search/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
