package com.feng.mall.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MallWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallWarehouseApplication.class, args);
	}

}
