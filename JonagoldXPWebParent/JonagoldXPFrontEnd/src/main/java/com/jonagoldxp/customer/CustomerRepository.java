package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
    public Customer findByVerificationCode(String code);

    @Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    public void enable(Integer id);

//    @Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
//    @Modifying
//    public void updateAuthenticationType(Integer customerId, AuthenticationType type);
}
