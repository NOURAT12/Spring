package com.example.customer_service.controllers;

import com.example.customer_service.dto.CustomerDTO;
import com.example.customer_service.models.Product;
import com.example.customer_service.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.customer_service.clients.CompanyServiceClient;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CompanyServiceClient companyServiceClient;

    @GetMapping("")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return customerDTO != null ? ResponseEntity.ok(customerDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getProductFallback")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        String url = "http://COMPANY-SERVICE/companyService/products";
        ResponseEntity<Product[]>  response = restTemplate.getForEntity(url, Product[].class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
            return null;
        List<Product> products = Arrays.asList(response.getBody());
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<Product>> getProductFallback() {
        List<Product> products = Collections.emptyList();
        return ResponseEntity.ok(products);
    }

}
