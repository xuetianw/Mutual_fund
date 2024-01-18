package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class ApiGatewayApplication {

//	@Bean
//	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f
//								.addRequestHeader("MyHeader", "MyURI")
//								.addRequestParameter("Param", "MyValue"))
//						.uri("http://httpbin.org:80"))
//				.route(p -> p.path("/currency-exchange/**")
//						.uri("lb://currency-exchange"))
//				.route(p -> p.path("/currency-conversion/**")
//						.uri("lb://currency-conversion"))
//				.route(p -> p.path("/mutualfund22222/**")
//						.uri("lb://mutualfund"))
//				.route(p -> p.path("/currency-conversion-new/**")
//						.filters(f -> f.rewritePath(
//								"/currency-conversion-new/(?<segment>.*)",
//								"/currency-conversion-feign/${segment}"))
//						.uri("lb://currency-conversion"))
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
