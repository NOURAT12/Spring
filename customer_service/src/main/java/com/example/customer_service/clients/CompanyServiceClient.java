package com.example.customer_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.customer_service.models.Product;


import java.util.List;

@FeignClient(name = "COMPANY-SERVICE", path = "/companyService")
//@HystrixCommand(fallbackMethod = "getEmployeeFallback")

public interface CompanyServiceClient {

    @GetMapping("/products")
    List<Product> getAllProducts();
}
