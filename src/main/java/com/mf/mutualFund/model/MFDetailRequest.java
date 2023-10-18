package com.mf.mutualFund.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MFDetailRequest {

	@NotBlank(message = "Schema Id should not be empty")
	private Integer schemaId;
	
}
