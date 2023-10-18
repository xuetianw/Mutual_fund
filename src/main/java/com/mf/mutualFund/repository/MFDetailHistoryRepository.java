package com.mf.mutualFund.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mf.mutualFund.model.MFDetailHistory;

public interface MFDetailHistoryRepository extends JpaRepository<MFDetailHistory, Integer> {
	
	List<MFDetailHistory> findBySchemaId(Integer schemaId);

}
