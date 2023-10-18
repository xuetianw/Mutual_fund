package com.mutualfund.wishlist.service;

import com.mutualfund.wishlist.entity.Customer;

public interface CustomerService {
	Customer getCustomer(Long customerId) throws Exception;
}	
