package com.example.mfu.service;

import com.example.mfu.model.MFDetail;
import com.example.mfu.model.MFDetailRequest;
import com.example.mfu.model.TokenValidationRequest;
import com.example.mfu.model.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class TokenValidationService {


	@Autowired
	private WebClient.Builder webClientBuilder;

	@Value("${authUrl}")
	private String auth_url;

	public TokenValidationResponse validateToken(String token) {
		return webClientBuilder.baseUrl(auth_url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build().post()
				.uri("/validateTokenV2")
				.body(Mono.just(new TokenValidationRequest(token)), TokenValidationRequest.class)
				.retrieve()
				.bodyToMono(TokenValidationResponse.class)
				.block();
	}



//	void test(String token) {
//
//		WebClient webClient2 = WebClient.builder()
//				.build();
//
//		WebClient webClient = WebClient.builder().baseUrl("http://localhost:8005")
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//
//		Mono<TokenValidationResponse> response2 = webClient.post()
//				.uri("/validateTokenV2")
//				.body(Mono.just(new TokenValidationRequest(token)), TokenValidationRequest.class)
//				.exchangeToMono(response -> response.bodyToMono(TokenValidationResponse.class));
//
//
////		return tokenValidationResponse;
//	}


//	WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080")
//			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//
//	MFDetail mfDetail = webClient.post().uri("/mf/get/details")
//			.body(Mono.just(MFDetailRequest.builder().schemaId(schemaId).build()), MFDetailRequest.class)
//			.retrieve()
//			.bodyToMono(MFDetail.class)
//			.block();
}

