package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
