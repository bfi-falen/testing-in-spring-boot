package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    
    @Test
    public void it_should_save_and_retrieve_customer(){
        Customer customer = Customer.builder()
                .name("John Doe")
                .email("john@mail.com")
                .build();
        var createdCustomer = customerRepository.save(customer);
        assertNotNull(createdCustomer.getId());

        var retrievedCustomer = customerRepository.findById(createdCustomer.getId()).get();
        assertEquals(createdCustomer, retrievedCustomer);

    }
}
