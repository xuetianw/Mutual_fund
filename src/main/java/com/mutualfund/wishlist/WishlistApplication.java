package com.mutualfund.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WishlistApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	};

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(WishlistApplication.class);
	}

}
