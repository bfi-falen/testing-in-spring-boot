package com.example.demo.services;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//1. Class name should represent what we are testing.
public class CustomerServicesImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    //2. Mock CustomerRepository
    @Mock
    CustomerRepository customerRepository;

    @Test
    public void when_create_customer_then_return_customer() {
        //3. Given, prepare data
        CustomerDto customerDto = createCustomerDto();
        Customer customerRequest = createCustomerFromDto(customerDto);
        Customer customer = createCustomer(customerDto.getName(), customerDto.getEmail());

        //4. when, condition to trigger mocking and return a result
        when(customerRepository.save(customerRequest)).thenReturn(customer);

        //5. call the actual service
        var result = customerService.createCustomer(customerDto);

        //6. then, assert the result values.
        assertNotNull(result.getId());
        assertEquals(customerDto.getName(), result.getName());
        assertEquals(customerDto.getEmail(), result.getEmail());
    }

    @Test(expected = NullPointerException.class)
    public void when_create_customer_without_name_then_throw_exception() {
        CustomerDto customerDto = CustomerDto.builder().email("test@test.com").build();
        customerService.createCustomer(customerDto);
    }

    @Test
    public void when_create_customer_without_email_then_throw_exception() {
        CustomerDto customerDto = CustomerDto.builder().name("test").build();

        var error = assertThrows(ResponseStatusException.class, () -> customerService.createCustomer(customerDto));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
        assertEquals("Email is required", error.getReason());
    }

    private Customer createCustomerFromDto(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .build();
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
