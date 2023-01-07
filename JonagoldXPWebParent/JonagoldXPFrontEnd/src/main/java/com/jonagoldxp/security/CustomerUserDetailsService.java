package com.jonagoldxp.security;

import com.jonagoldxp.common.entity.Customer;
import com.jonagoldxp.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired private CustomerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = repo.findByEmail(email);
        if (customer == null)
            throw new UsernameNotFoundException("No customer found with the email " + email);

        return new CustomerUserDetails(customer);
    }
}
