package com.example.customer_service.controllers;

import com.example.customer_service.dto.OrderDTO;
import com.example.customer_service.models.Order;
import com.example.customer_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        return orderDTO != null ? ResponseEntity.ok(orderDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/sell")
    public ResponseEntity<Order> sellToCompany(@RequestBody Order order) {
        String url = "http://COMPANY-SERVICE/companyService/orders";
        ResponseEntity<Order>  response = restTemplate.postForEntity(url,order,Order.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
            return null;
        Order order1 = response.getBody();
        return ResponseEntity.ok(order1);
    }

}
