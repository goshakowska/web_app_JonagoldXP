package com.jonagoldxp.admin.customer;

import com.jonagoldxp.admin.paging.PagingAndSortingHelper;
import com.jonagoldxp.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CustomerService {
    public static final int CUSTOMERS_PER_PAGE = 4;

    @Autowired
    private CustomerRepository customerRepo;

    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepo.updateEnabledStatus(id, enabled);
    }

    public Customer get(Integer id) {
            return customerRepo.findById(id).get();
    }

    public List<Customer> listAll() {
        return (List<Customer>)  customerRepo.findAll(Sort.by("firstName").ascending());
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
    }

    public boolean isEmailUnique(Integer id, String email) {
        Customer existCustomer = customerRepo.findByEmail(email);

        if (existCustomer != null && existCustomer.getId() != id) {
            // found another customer having the same email
            return false;
        }

        return true;
    }

    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    public void delete(Integer id) {
        customerRepo.deleteById(id);
    }
}
