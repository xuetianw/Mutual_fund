package com.mf.mutualFund.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import com.mf.mutualFund.model.MFDetailRequest;
import com.mf.mutualFund.model.SearchByCategoryRequest;
import com.mf.mutualFund.model.SearchByFundHouseRequest;
import com.mf.mutualFund.model.SearchByNameRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class MutualFundRestControllerTest {

//	@Value(value = "${local.server.port}")
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Value(value = "${api.test.url}")
//	private String testUrl;
//
//	@Value(value = "${api.getdetails.url}")
//	private String getDetailsUrl;
//
//	@Value(value = "${api.top5.url}")
//	private String topFiveUrl;
//
//	@Value(value = "${api.bottom5.url}")
//	private String bottomFiveUrl;
//
//	@Value(value = "${api.searchByName.url}")
//	private String searchByNameUrl;
//
//	@Value(value = "${api.searchByCategory.url}")
//	private String searchByCategoryUrl;
//
//	@Value(value = "${api.searchByFundHouse.url}")
//	private String searchByFundHouseUrl;
//
//	//Test for the Test API in MFRestController
//	@Test
//	public void testShouldReturnDefaultMessage() throws Exception {
//		assertThat(this.restTemplate.getForObject(testUrl, String.class)).contains("done");
//	}
//
//	//Tests for getDetails API in MFRestController
//	@Test
//	public void testShouldGetDetails() throws Exception {
//		MFDetailRequest request = new MFDetailRequest(2);
//		int result = this.restTemplate.postForEntity(getDetailsUrl, new HttpEntity<MFDetailRequest>(request), MFDetailRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	@Test
//	public void testNotExistingMutualFundGetDetails() throws Exception {
//		MFDetailRequest request = new MFDetailRequest(200);
//		int result = this.restTemplate.postForEntity(getDetailsUrl, new HttpEntity<MFDetailRequest>(request), MFDetailRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	@Test
//	public void testNoParameterGetDetails() throws Exception {
//		int result = this.restTemplate.postForEntity(getDetailsUrl, null, MFDetailRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	//Tests for get top 5 performing mutual funds API in MFRestController
//	@Test
//	public void testShouldGetTopFiveFunds() throws Exception {
//		int result = this.restTemplate.postForEntity(topFiveUrl, null, null).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	//Tests for get bottom 5 performing mutual funds API in MFRestController
//	@Test
//	public void testShouldGetBottomFiveFunds() throws Exception {
//		int result = this.restTemplate.postForEntity(bottomFiveUrl, null, null).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	//Tests for search funds by name API in MFRestController
//	@Test
//	public void testShouldReturnFundsByName() throws Exception {
//		SearchByNameRequest request = new SearchByNameRequest("ema1");
//		int result = this.restTemplate.postForEntity(searchByNameUrl, new HttpEntity<SearchByNameRequest>(request), SearchByNameRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	@Test
//	public void testShouldNotReturnFundsByName() throws Exception {
//		SearchByNameRequest request = new SearchByNameRequest("100");
//		int result = this.restTemplate.postForEntity(searchByNameUrl, new HttpEntity<SearchByNameRequest>(request), SearchByNameRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	@Test
//	public void testInvalidParamSearchByName() throws Exception {
//		int result = this.restTemplate.postForEntity(searchByNameUrl, null, null).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	//Tests for search funds by category API in Rest controller
//	@Test
//	public void testShouldReturnFundsByCategory() throws Exception {
//		SearchByCategoryRequest request = new SearchByCategoryRequest("cat1");
//		int result = this.restTemplate.postForEntity(searchByCategoryUrl, new HttpEntity<SearchByCategoryRequest>(request), SearchByCategoryRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	@Test
//	public void testShouldNotReturnFundsByCategory() throws Exception {
//		SearchByCategoryRequest request = new SearchByCategoryRequest("cat22");
//		int result = this.restTemplate.postForEntity(searchByCategoryUrl, new HttpEntity<SearchByCategoryRequest>(request), SearchByCategoryRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	@Test
//	public void testInvalidParamSearchByCategory() throws Exception {
//		int result = this.restTemplate.postForEntity(searchByCategoryUrl, null, null).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	//Tests for search funds by fund house
//	@Test
//	public void testShouldReturnFundsByFundHouse() throws Exception {
//		SearchByFundHouseRequest request = new SearchByFundHouseRequest("hdfc");
//		int result = this.restTemplate.postForEntity(searchByFundHouseUrl, new HttpEntity<SearchByFundHouseRequest>(request), SearchByFundHouseRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.OK.value());
//	}
//
//	@Test
//	public void testShouldNotReturnFundsByFundHouse() throws Exception {
//		SearchByFundHouseRequest request = new SearchByFundHouseRequest("hh");
//		int result = this.restTemplate.postForEntity(searchByFundHouseUrl, new HttpEntity<SearchByFundHouseRequest>(request), SearchByFundHouseRequest.class).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
//
//	@Test
//	public void testInvalidParamSearchByFundHouse() throws Exception {
//		int result = this.restTemplate.postForEntity(searchByFundHouseUrl, null, null).getStatusCode().value();
//		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND.value());
//	}
}
