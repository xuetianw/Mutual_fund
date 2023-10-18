package com.example.mfu.repository;

import com.example.mfu.entity.Portfolio;
import com.example.mfu.model.PortfolioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	// Get the portfolios of the current customer
	@Query("SELECT p FROM Portfolio p WHERE p.customer.customerId = :customerId")
	List<PortfolioProjection> findByCustomerId(@Param("customerId") Long customerId);

//	@Query("SELECT p.portfolioId FROM Portfolio p join Customer C ON (C.customerId = p.customer.customerId) WHERE p.customer.customerId = :customerId and p.mfName = :mfName")
//	List<CustomeProjectionWithPriceAndSchemaID> findMFINFOByCustomerIdAndMfName(Long customerId, String mfName);

	// Find customer portfolio by mutual fund ID and customer ID
	@Query("SELECT p FROM Portfolio p join Customer C ON (C.customerId = p.customer.customerId) WHERE p.customer.customerId = :customerId and p.mfName = :mfName")
	Portfolio findByCustomerCustomerIdAndMfName(Long customerId, String mfName);


	@Query("SELECT p FROM Portfolio p join Customer C ON (C.customerId = p.customer.customerId) WHERE p.customer.customerId = :customerId and p.mfDetail.schemaId = :schemaId")
	Portfolio findByCustomerCustomerIdAndMfschemaId(Long customerId, Long schemaId);

	@Query("SELECT p FROM Portfolio p join Customer C ON (C.customerId = p.customer.customerId) WHERE p.customer.customerId = :customerId and p.mfDetail.schemaId = :schemaId")
	Portfolio findByCustomerCustomerIdAndMfschemaId(Long customerId);
}
