package com.mf.mutualFund.rest;


import java.util.List;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mf.mutualFund.model.MFDetail;
import com.mf.mutualFund.model.MFDetailRequest;
import com.mf.mutualFund.model.SearchByCategoryRequest;
import com.mf.mutualFund.model.SearchByFundHouseRequest;
import com.mf.mutualFund.model.SearchByNameRequest;
import com.mf.mutualFund.service.MFService;

@CrossOrigin(origins="*")
@RestController
@Builder
@RequestMapping("/mf")
public class MFRestController {

	@Autowired
	private MFService mfService;

//	@PostMapping("/get/details2")
//	public ResponseEntity<MFDetail> getDetails2(@RequestBody MFDetailRequest detailRequest) throws Exception {
//		MFDetail mfDetail = MFDetail.builder().description("aa").build();
//		return new ResponseEntity<>(mfDetail, HttpStatus.OK);
//	}
//	@GetMapping("/get/details2")
//	public ResponseEntity<MFDetail> getDetails() throws Exception {
//		MFDetail mfDetail = MFDetail.builder().description("aa").build();
//		return new ResponseEntity<>(mfDetail, HttpStatus.OK);
//	}

	@PostMapping("/get/details")
	public ResponseEntity<MFDetail> getDetails( @RequestBody MFDetailRequest detailRequest ) throws Exception {
		
		Integer schemaId = detailRequest.getSchemaId();
		MFDetail mfDetail = mfService.mfDetailService(schemaId);
//		MFDetail mfDetail = MFDetail.builder().description("aa").build();
		return new ResponseEntity<>(mfDetail, HttpStatus.OK);
	}
	
	@PostMapping("/get/top5")
	public ResponseEntity<List<MFDetail>> getTopFiveDetails() throws Exception {
		
		List<MFDetail> mfDetails = mfService.mfTopFiveDetailService();
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@PostMapping("/get/bottom5")
	public ResponseEntity<List<MFDetail>> getBottomFiveDetails() throws Exception {
		
		List<MFDetail> mfDetails = mfService.mfBottomFiveDetailService();
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@PostMapping("/search/name")
	public ResponseEntity<List<MFDetail>> getMFDetailBasedOnName( @RequestBody SearchByNameRequest request) throws Exception {
		
		String name = request.getName();
		List<MFDetail> mfDetails = mfService.mfSearchByName(name);
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@PostMapping("/search/category")
	public ResponseEntity<List<MFDetail>> getMFDetailBasedOnCategory( @RequestBody SearchByCategoryRequest request) throws Exception {
		
		String category = request.getCategory();
		List<MFDetail> mfDetails = mfService.mfSearchByCategory(category);
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@PostMapping("/search/fundHouse")
	public ResponseEntity<List<MFDetail>> getMFDetailBasedOnFundHouse( @RequestBody SearchByFundHouseRequest request) throws Exception {
		
		String fundHouse = request.getFundHouse();
		List<MFDetail> mfDetails = mfService.mfSearchByFundHouse(fundHouse);
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@PostMapping("/get/all")
	public ResponseEntity<List<MFDetail>> getAllMFDetails() throws Exception {
		
		List<MFDetail> mfDetails = mfService.mfGetAllDetails();
		return new ResponseEntity<>(mfDetails, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "done";
	}
	
}
