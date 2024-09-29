package com.example.customer_service.services;

import com.example.customer_service.dto.CompanyDTO;
import com.example.customer_service.dto.CustomerDTO;
import com.example.customer_service.entities.Customer;
import com.example.customer_service.models.Product;
import com.example.customer_service.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(this::convertToDTO).orElse(null);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return convertToDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(customerDTO.getName());
            existingCustomer.setEmail(customerDTO.getEmail());
            // يمكنك إضافة المزيد من التحديثات هنا

            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return convertToDTO(updatedCustomer);
        } else {
            return null; // أو يمكن رمي استثناء إذا لم يتم العثور على العميل
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }

//    private RestTemplate restTemplate;
//
//    @HystrixCommand(fallbackMethod = "getProductFallback")
//    public List<Product> getAllProducts() {
//        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
//                "http://company-service/companyService/products",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Product>>() {}
//        );
//
//        return responseEntity.getBody();
//    }


//    public String getProductFallback() {
//        return "خطا كبير";
//    }
//    public CompanyDTO getCompanyDetails(Long companyId) {
////        return new CompanyDTO(null,null,null);
//        return restTemplate.getForObject("http://company-service/companyService/companies/?Id=" + companyId, CompanyDTO.class);
//    }

}
