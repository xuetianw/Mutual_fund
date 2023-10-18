package com.example.mfu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MFDetailHistory {
	
	private Integer id;
	private Date date;
	private Integer schemaId;
	private Double nav;
	
	public MFDetailHistory(Date date, Integer schemaId, Double nav) {
		super();
		this.date = date;
		this.schemaId = schemaId;
		this.nav = nav;
	}
}
