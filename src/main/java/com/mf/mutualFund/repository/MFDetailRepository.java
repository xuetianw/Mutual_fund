package com.mf.mutualFund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mf.mutualFund.model.MFDetail;

public interface MFDetailRepository extends JpaRepository<MFDetail, Integer> {
	
	MFDetail findBySchemaId(Integer schemaId);
	List<MFDetail> findBySchemaNameContaining(String name);
	List<MFDetail> findBySchemaCategoryContaining(String category);
	List<MFDetail> findByFundHouseContaining(String fundHouse);
}
