package com.example.mfu.controller;

import com.example.mfu.entity.Portfolio;
import com.example.mfu.model.CustomProjectionWithPriceAndSchemaID;
import com.example.mfu.model.PortfolioProjection;
import com.example.mfu.service.MutualFundInfoService;
import com.example.mfu.service.PortfolioService;
import com.example.mfu.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mfu.model.MFDetail;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin({"FEdomain"})
@CrossOrigin("*")
@RequestMapping("/portfolios")
public class PortfolioController {

	private final PortfolioService portfolioService;
	private final TokenValidationService tokenValidationService;

	@Autowired
	private MutualFundInfoService mutualFundInfoService;

	public PortfolioController(PortfolioService portfolioService,
							   TokenValidationService tokenValidationService) {
		this.portfolioService = portfolioService;
		this.tokenValidationService = tokenValidationService;
	}

//	/* GET all portfolios of a customer */
//	@GetMapping("/customers/{customerId}")
//	public ResponseEntity<?> getPortfolioByCustomerId(@PathVariable Long customerId,
//													  @RequestHeader("Authorization") String token) {
//		TokenValidationResponse validationResponse = tokenValidationService.validateToken(token).block();
//		if (validationResponse != null && validationResponse.isValid()) {
//			Long validatedCustomerId = validationResponse.getCustomerId();
//			if (validatedCustomerId.equals(customerId)) {
//				List<PortfolioProjection> portfolio = portfolioService.getPortfolioByCustomerId(customerId);
//				return ResponseEntity.ok(portfolio);
//			} else {
//				return ResponseEntity.badRequest().body("Invalid customerId");
//			}
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed");
//		}
//	}

	@GetMapping("/test")
	public String test() {
		return "Test API is working";
	}

	@GetMapping("/customersV2/{customerId}")
	public ResponseEntity<?> getPortfolioByCustomerIdV2(@PathVariable Long customerId) {
		List<PortfolioProjection> portfolio = portfolioService.getPortfolioByCustomerId(customerId);
//		mutualFundInfoService.getMutualFundInfo(portfolio);
		return ResponseEntity.ok(portfolio);
	}


	@GetMapping("/customers/{customerId}")
	public ResponseEntity<?> getPortfolioByCustomerId(@PathVariable Long customerId) {
		List<CustomProjectionWithPriceAndSchemaID> customReturn = new ArrayList<>();
		List<PortfolioProjection> portfolio = portfolioService.getPortfolioByCustomerId(customerId);

//		System.out.println(portfolio);


//		portfolio.forEach(portfolioProjection -> {
//			try {
//				MFDetail mfDetail = mutualFundInfoService.getMutualFundInfoByName(portfolioProjection.getMfName());
//				customReturn.add(CustomProjectionWithPriceAndSchemaID.builder()
//						.portfolioProjection(portfolioProjection)
//						.schemaId(mfDetail.getSchemaId())
//						.currPrice(mfDetail.getCurrPrice()).build());
//
//			} catch (URISyntaxException e) {
//				throw new RuntimeException(e);
//			}
//		});
//
//		System.out.println(customReturn);

		return ResponseEntity.ok(portfolio);
	}
}
