package com.feng.mall.product;

import com.feng.mall.product.entity.BrandEntity;
import com.feng.mall.product.service.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MallProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {

		BrandEntity brandEntity = new BrandEntity();

		brandEntity.setName("Lululemon");

		brandService.save(brandEntity);
		
		System.out.println("save successfully");

	}

}
