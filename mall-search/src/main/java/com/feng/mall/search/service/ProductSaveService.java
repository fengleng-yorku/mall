package com.feng.mall.search.service;

import java.util.List;

import com.feng.common.to.es.SkuEsModel;

public interface ProductSaveService {

    void productStatusUp(List<SkuEsModel> skuEsModels);

}
