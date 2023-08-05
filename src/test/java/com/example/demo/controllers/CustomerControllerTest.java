package com.example.demo.controllers;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void it_should_return_created_customer() throws Exception{
        CustomerDto request = createCustomerDto();
        Customer customer = createCustomer(request.getName(), request.getEmail());

        when(customerService.createCustomer(request)).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.email").value(request.getEmail()));
    }

    private Customer createCustomer(String name, String email) {
        return Customer.builder()
                .id(UUID.randomUUID())
                .name(name)
                .email(email)
                .build();
    }

    private CustomerDto createCustomerDto() {
        return CustomerDto.builder()
                .name("Customer Name")
                .email("customer@mail.com")
                .build();
    }
}
