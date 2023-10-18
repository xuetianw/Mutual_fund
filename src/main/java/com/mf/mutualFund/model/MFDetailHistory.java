package com.mf.mutualFund.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="mfdetailhistory")
@Data
@NoArgsConstructor
public class MFDetailHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Date should not be blank")
	private Date date;
	@NotBlank(message = "Schema Id should not be blank")
	private Integer schemaId;
	@NotBlank(message = "Nav should not be blank")
	private Double nav;
	
	public MFDetailHistory(Date date, Integer schemaId, Double nav) {
		super();
		this.date = date;
		this.schemaId = schemaId;
		this.nav = nav;
	}
}
