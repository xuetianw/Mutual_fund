package com.mf.mutualFund.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchByNameRequest {

	@NotBlank(message = "Name should not be empty")
	private String name;
}
