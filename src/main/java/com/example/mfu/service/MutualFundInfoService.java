package com.example.mfu.service;

import com.example.mfu.model.MFDetail;
import com.example.mfu.model.MFDetailRequest;
import com.example.mfu.model.MFSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

//import static jdk.jfr.internal.EventWriterKey.block;


@Service
public class MutualFundInfoService {
    private String schemaName;
    private String fundHouse;
    private Double currPrice;
    private String symbol;
    private Long schemaId;

    @Value("${mf_service}")
    private String mf_url;

    public MFDetail getMutualFundInfo(Long schemaId) throws URISyntaxException {
        WebClient webClient = WebClient.builder().baseUrl(mf_url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        MFDetail mfDetail = webClient.post().uri("/mf/get/details")
                .body(Mono.just(MFDetailRequest.builder().schemaId(schemaId).build()), MFDetailRequest.class)
                .retrieve()
                .bodyToMono(MFDetail.class)
                .block();

            System.out.println(mfDetail);
            return mfDetail;
    }



    public MFDetail getMutualFundInfoByName(String name) throws URISyntaxException {
        WebClient webClient = WebClient.builder().baseUrl(mf_url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        MFDetail[] mfDetails = webClient.post().uri("/mf/search/name")
                .body(Mono.just(MFSearchRequest.builder().name(name).build()), MFSearchRequest.class)
                .retrieve()
                .bodyToMono(MFDetail[].class)
                .block();

        System.out.println(mfDetails);
        return mfDetails[0];
    }

    private Long getSchemaId() {
        return schemaId;
    }

    public String getSymbol() {
        return symbol;
    }
    
    public String getSchemaName() {
        return schemaName;
    }
    
    public Double getCurrPrice() {
        return currPrice;
    }
    
    public String getFundHouse() {
        return fundHouse;
    }
}
