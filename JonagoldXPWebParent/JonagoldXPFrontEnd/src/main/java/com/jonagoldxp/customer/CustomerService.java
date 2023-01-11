package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepo;

    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }
}
