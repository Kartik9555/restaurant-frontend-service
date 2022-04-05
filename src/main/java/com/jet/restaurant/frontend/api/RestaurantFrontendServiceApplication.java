package com.jet.restaurant.frontend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class RestaurantFrontendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantFrontendServiceApplication.class, args);
	}

}
