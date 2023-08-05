package com.example.demo.services;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        requireNonNull(customerDto.getName());
        if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        Customer customer = Customer
                .builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .build();
        return customerRepository.save(customer);
    }
}