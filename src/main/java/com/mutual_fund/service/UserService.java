package com.mutual_fund.service;

import com.mutual_fund.entities.Customer;
import com.mutual_fund.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> customer = customerRepository.findByEmail(username);

        customer.orElseThrow(() -> new RuntimeException("customer not found in this repo"));

        return new User(customer.get().getEmail(), customer.get().getPwd(), new ArrayList<>());
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("customer not found in this repo"));
    }
}
