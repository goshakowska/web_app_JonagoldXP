package com.jonagoldxp.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.jonagoldxp.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    @Autowired private CustomerRepository repo;

    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1() {

        Customer customer = new Customer();
        customer.setFirstName("David");
        customer.setLastName("Suchet");
        customer.setPassword("HerculesPoirot198");
        customer.setEmail("david.suchet@mystery.com");
        customer.setPhoneNumber("123-456-789");
        customer.setAddressLine1("56B Whitehaven Mansion");
        customer.setCity("London");
        customer.setPostalCode("12345");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCustomer2() {



        Customer customer = new Customer();
        customer.setFirstName("James");
        customer.setLastName("Bond");
        customer.setPassword("imbondjamesbond");
        customer.setEmail("black@suits.com");
        customer.setPhoneNumber("000-000-777");
        customer.setAddressLine1("Some Street");
        customer.setAddressLine2("007JB");
        customer.setCity("London");
        customer.setPostalCode("00-7");
        customer.setCreatedTime(new Date());
        customer.setVerificationCode("007007");

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }
    @Test
    public void testListCustomers() {
        Iterable<Customer> customers = repo.findAll();
        customers.forEach(System.out::println);

        assertThat(customers).hasSizeGreaterThan(1);
    }

    @Test
    public void testUpdateCustomer() {
        Integer customerId = 1;
        String lastName = "Beckham";

        Customer customer = repo.findById(customerId).get();
        customer.setLastName(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = repo.save(customer);
        assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer() {
        Integer customerId = 2;
        Optional<Customer> findById = repo.findById(customerId);

        assertThat(findById).isPresent();

        Customer customer = findById.get();
        System.out.println(customer);
    }

    @Test
    public void testDeleteCustomer() {
        Integer customerId = 2;
        repo.deleteById(customerId);

        Optional<Customer> findById = repo.findById(customerId);
        assertThat(findById).isNotPresent();
    }

    @Test
    public void testFindByEmail() {
        String email = "david.suchet@mystery.com";
        Customer customer = repo.findByEmail(email);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testFindByVerificationCode() {
        String code = "007007";
        Customer customer = repo.findByVerificationCode(code);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testEnableCustomer() {
        Integer customerId = 1;
        repo.enable(customerId);

        Customer customer = repo.findById(customerId).get();
        assertThat(customer.isEnabled()).isTrue();
    }
}
