package com.feng.mall.product;

import com.feng.mall.product.entity.BrandEntity;
import com.feng.mall.product.service.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@SpringBootTest
public class MallProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {

		BrandEntity brandEntity = new BrandEntity();

		 //brandEntity.setBrandId(1L);
		// brandEntity.setDescript("canada brand");

		 brandEntity.setName("Lululemon");

		 brandService.save(brandEntity);

		// brandService.updateById(brandEntity);

		List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
		list.forEach((item) -> {
			System.out.println(item.getName());
		});

		// System.out.println("save successfully");

	}

}
