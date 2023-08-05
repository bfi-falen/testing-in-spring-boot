package com.example.demo.services;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entities.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(UUID id);
    Customer createCustomer(CustomerDto customer);
}
