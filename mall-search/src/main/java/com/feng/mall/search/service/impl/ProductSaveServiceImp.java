package com.feng.mall.search.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.common.to.es.SkuEsModel;
import com.feng.mall.search.constant.EsConstant;
import com.feng.mall.search.service.ProductSaveService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;

@Service
public class ProductSaveServiceImp implements ProductSaveService {

    private static final Logger log = LoggerFactory.getLogger(ProductSaveServiceImp.class);

    @Autowired
    ElasticsearchClient client;

    @Override
    public void productStatusUp(List<SkuEsModel> skuEsModels) {

        List<BulkOperation> operations = skuEsModels.stream()
                .map(model -> BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index(EsConstant.PRODUCT_INDEX)
                                .id(model.getSkuId().toString())
                                .document(model))))
                .collect(Collectors.toList());

        try {
            BulkResponse response = client.bulk(b -> b.operations(operations));
            if (response.errors()) {
                response.items().stream()
                        .filter(item -> item.error() != null)
                        .forEach(item -> log.error("Bulk index error, id: {}, reason: {}",
                                item.id(), item.error().reason()));
            } else {
                log.info("productStatusUp success, count: {}", skuEsModels.size());
            }
        } catch (IOException e) {
            log.error("productStatusUp failed", e);
            throw new RuntimeException("ES bulk index failed", e);
        }

    }

}
