package com.mutualfund.wishlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mutualfund.wishlist.entity.CustomerWishlist;

@Repository
public interface CustomerWishlistRepository extends JpaRepository<CustomerWishlist, Integer> {
	CustomerWishlist findByWishlistId(int wishlistId);
	List<CustomerWishlist> findByCustomerCustomerId(Long customerId);
//	void deleteByWishlistId(int wishlistId);
	Optional<CustomerWishlist> findByCustomerCustomerIdAndMfSchemaId(Long customerId,int mfSchemaId);
}
