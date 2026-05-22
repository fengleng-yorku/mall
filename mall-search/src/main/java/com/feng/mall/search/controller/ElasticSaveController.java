package com.feng.mall.search.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feng.common.exception.BizCodeEnume;
import com.feng.common.to.es.SkuEsModel;
import com.feng.common.utils.R;
import com.feng.mall.search.service.ProductSaveService;

@RequestMapping("/search")
@RestController
public class ElasticSaveController {

    private static final Logger log = LoggerFactory.getLogger(ElasticSaveController.class);

    @Autowired
    ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        try {
            productSaveService.productStatusUp(skuEsModels);
            return R.ok();
        } catch (Exception e) {
            log.error("productStatusUp failed", e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }
}
