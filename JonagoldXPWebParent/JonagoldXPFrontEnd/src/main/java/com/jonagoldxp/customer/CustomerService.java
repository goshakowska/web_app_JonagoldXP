package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import java.util.Date;

@Service
@Transactional
public class CustomerService {
    @Autowired private CustomerRepository customerRepo;

    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }

    public void registerCustomer(Customer customer) {
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());

        customerRepo.save(customer);
    }

    public void save(Customer customer) {
        customerRepo.save(customer);
    }
}
