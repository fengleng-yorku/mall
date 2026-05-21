package com.feng.mall.search;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import lombok.Data;

@SpringBootTest
class MallSearchApplicationTests {

	@Autowired
	private ElasticsearchClient client;

	@Test
	void contextLoads() throws Exception {
		var info = client.info();
		System.out.println("Cluster Name: " + info.clusterName());
		System.out.println("Cluster UUID: " + info.clusterUuid());
		System.out.println("Elasticsearch Version: " + info.version().number());
	}


	// @Test
	// void testIndex() throws Exception {

	// 	Product product = new Product();
	// 	product.setId(1L);
	// 	product.setName("iPhone 15");
	// 	product.setPrice(new BigDecimal("6999"));

	// 	IndexRequest<Product> request = IndexRequest.of(r -> r
	// 			.index("product")
	// 			.id("1")
	// 			.document(product));

	// 	var response = client.index(request);
	// 	System.out.println("Result: " + response.result()); // CREATED or UPDATED
	// }

	// @Test
	// void testSearch() throws Exception {
	// 	SearchRequest request = SearchRequest.of(r -> r
	// 			.index("product")
	// 			.query(q -> q
	// 					.match(m -> m
	// 							.field("name")
	// 							.query("iPhone")))
	// 			.size(10));

	// 	var response = client.search(request, Product.class);

	// 	for (var hit : response.hits().hits()) {
	// 		System.out.println(hit.source());
	// }

}
