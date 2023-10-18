package com.mf.mutualFund.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="mfdetails")
@Data //try to avoid as it's an anti-pattern
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MFDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer schemaId;
	@NotBlank(message = "Fund house cannot be empty")
	private String fundHouse;
	@NotBlank(message = "Schema name should not be empty")
	private String schemaName;
	@NotBlank(message = "Schema category should not be empty")
	private String schemaCategory;
	@NotBlank(message = "Description should not be empty")
	private String description;
	@NotBlank(message = "Current price should not be empty")
	private Double currPrice;
	@NotBlank(message = "Delta should not be empty")
	private Double delta;
	@NotBlank(message = "Mutual fund symbol should not be empty")
	private String symbol;
	
	@JsonManagedReference
	@Transient
	@OneToMany(mappedBy = "mfdetails", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
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
