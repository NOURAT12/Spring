package com.example.customer_service.dto;

public class CompanyDTO {
    private Long id;
    private String name;
    private String address;

    public CompanyDTO(Long id, String address, String name) {
        this.id=id;
        this.name=name;
        this.address=address;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
