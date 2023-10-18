package com.example.mfu.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MFDetail {
	
	private Integer schemaId;
	private String fundHouse;
	private String schemaName;
	private String schemaCategory;
	private String description;
	private Double currPrice;
	private Double delta;
	private String symbol;

	private List<MFDetailHistory> mfDetailHistory;

	public MFDetail(String fundHouse, String schemaName, String schemaCategory, String description, Double currPrice,
                    Double delta, String symbol, List<MFDetailHistory> mfDetailHistory) {
		super();
		this.fundHouse = fundHouse;
		this.schemaName = schemaName;
		this.schemaCategory = schemaCategory;
		this.description = description;
		this.currPrice = currPrice;
		this.delta = delta;
		this.symbol = symbol;
		this.mfDetailHistory = mfDetailHistory;
	}
}
