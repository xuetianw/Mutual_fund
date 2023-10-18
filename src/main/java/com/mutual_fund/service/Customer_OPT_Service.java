package com.mutual_fund.service;

import com.mutual_fund.entities.Customer_OTP;
import com.mutual_fund.repository.Customer_OTP_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Customer_OPT_Service {

    @Autowired
    private Customer_OTP_Repository customerOtpRepository;


    public void addOPT(Customer_OTP customerOtp) {
        Customer_OTP returned_opt = customerOtpRepository.save(customerOtp);
        if (returned_opt.getId() > 0) {
            System.out.println("suc");
        }
//        return customerOtpRepository.findById(customer.getCustomerOtp().getId()).get().getCustomer_id();
    }

    public Customer_OTP findOPTbyCustomerId(Long id) {
        return customerOtpRepository.findByCustomerid(id);
    }

    public void deleteOPT(Customer_OTP otp) {
        customerOtpRepository.delete(otp);
    }

    public Customer_OTP findByCustomerEmail(String email) {
        return customerOtpRepository.findByCustomerEmail(email).orElseThrow(() -> new RuntimeException("customer email not found"));
    }

    public void saveUserOTP(Customer_OTP optCustomer) {
        customerOtpRepository.save(optCustomer);
    }

//    private String findStatus(Customer customer) {
//        return "aa";
////        return customerOtpRepository.findById(customer.getCustomerOtp().getId()).get().getCustomer_id();
//    }



}
