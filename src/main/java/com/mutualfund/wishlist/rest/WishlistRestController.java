package com.mutualfund.wishlist.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mutualfund.wishlist.model.AddMutualFundToWishlistRequestBody;
import com.mutualfund.wishlist.entity.CustomerWishlist;
import com.mutualfund.wishlist.model.MutualFundInWishlistExistenceResponse;
import com.mutualfund.wishlist.model.ValidateRequest;
import com.mutualfund.wishlist.model.ValidateResponse;
import com.mutualfund.wishlist.service.CustomerWishlistService;

@RestController
@CrossOrigin("*")
@RequestMapping("/wishlist")
public class WishlistRestController {
	private static final CustomerWishlist CustomerWishlist = null;
	@Autowired
	private CustomerWishlistService customerWishlistService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/test")
	public String test() {
		return "Test API is working";
	}
	
	@Value("${auth.api.url}")
	String authUrl;
	
	@GetMapping("/get")
	public CustomerWishlist getCustomerWishlist(@RequestParam int wishlistId) throws Exception {
//		RequestHeader(value="Authorization", required = false) String clientToken
		return  customerWishlistService.getCustomerWishlist(wishlistId);
	}
	
	@GetMapping("/getbycustomerid")
	public List<CustomerWishlist> getCustomerWishlistByCustomerId(@RequestParam Long customerId) throws Exception {
		return  customerWishlistService.getCustomerWishlistByCustomerId(customerId);
	}
	
	@PostMapping("/add")
	public ResponseEntity<CustomerWishlist> addMutualFundToWishList(@RequestBody AddMutualFundToWishlistRequestBody body) throws Exception{
		ValidateRequest validateRequest = new ValidateRequest(body.getToken());
		ResponseEntity<ValidateResponse> response = restTemplate.postForEntity(authUrl, validateRequest, ValidateResponse.class);
		
		ValidateResponse validateResponse = response.getBody();
		Long customerId = validateResponse.getId();
		
		CustomerWishlist addedMutualFund= customerWishlistService.addMutualFundToWishlist(customerId, body.getMfSchemaId(), body.getMfName(), body.getMfFundHouse());
		return ResponseEntity.ok(addedMutualFund);		
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity removeMutualFundFromWishlist(@RequestParam int wishlistId) throws Exception {
//		@RequestHeader(value="Authorization", required = false) String clientToken;
		customerWishlistService.removeMutualFundFromWishlist(wishlistId);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/remove2")
	public ResponseEntity removeMutualFundFromWishlist2(@RequestParam int wishlistId) throws Exception {
//		@RequestHeader(value="Authorization", required = false) String clientToken;
		customerWishlistService.removeMutualFundFromWishlist(wishlistId);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/exists")
	public ResponseEntity<MutualFundInWishlistExistenceResponse> checkIfMutualFundAlreadyExists(@RequestParam Long customerId,@RequestParam int mfSchemaId) throws Exception{
//		@RequestHeader(value="Authorization", required = false) String clientToken,
		Optional<CustomerWishlist> customerWishlist=Optional.of(customerWishlistService.getWishlistByCustomerIdAndWishlistId(customerId,mfSchemaId));
		if(customerWishlist.isPresent()) {
			MutualFundInWishlistExistenceResponse response = new MutualFundInWishlistExistenceResponse(true);			
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(null);
	}
}
