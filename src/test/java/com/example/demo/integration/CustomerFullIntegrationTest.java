package com.example.demo.integration;

import com.example.demo.DemoApplication;
import com.example.demo.dto.CustomerDto;
import com.example.demo.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerFullIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void should_create_sample_and_find_all_customers() {
        String url = "http://localhost:" + port + "/api/customers";

        CustomerDto request = CustomerDto.builder().name("John").email("john@mail.com").build();

        var result = this.restTemplate.postForObject(url, request, Customer.class);

        assertEquals(result.getName(), request.getName());
        assertEquals(result.getEmail(), request.getEmail());

        assertTrue(
                this.restTemplate
                        .getForObject(url, List.class)
                        .size() == 1);
    }
}
