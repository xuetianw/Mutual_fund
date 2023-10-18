package com.mf.mutualFund.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchByCategoryRequest {

	@NotBlank(message = "Category should not be blank")
	private String category;
}
