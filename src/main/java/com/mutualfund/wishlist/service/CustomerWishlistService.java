package com.mutualfund.wishlist.service;

import java.util.List;

import com.mutualfund.wishlist.entity.CustomerWishlist;

public interface CustomerWishlistService {
	CustomerWishlist getCustomerWishlist(int wishlistId) throws Exception;
	List<CustomerWishlist> getCustomerWishlistByCustomerId(Long customerId) throws Exception;
	CustomerWishlist addMutualFundToWishlist(Long customerId, int mfSchemaId, String mfName, String mfFundHouse) throws Exception;
	void removeMutualFundFromWishlist(int wishListId) throws Exception;
	Long validateJwtToken(String jwtToken) throws Exception;
	CustomerWishlist getWishlistByCustomerIdAndWishlistId(Long customerId,int mfSchemaId) throws Exception;
}
