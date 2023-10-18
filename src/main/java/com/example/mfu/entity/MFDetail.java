package com.example.mfu.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	private String fundHouse;
	private String schemaName;
	private String schemaCategory;
	private String description;
	private Double currPrice;
	private Double delta;
	private String symbol;
}
