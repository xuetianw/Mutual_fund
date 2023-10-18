package com.mf.mutualFund.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchByFundHouseRequest {
	
	@NotBlank(message = "Fund house cannot be empty")
	private String fundHouse;

}
