package com.example.company_service.controllers;

import com.example.company_service.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.company_service.dto.CompanyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

//    @GetMapping
//    public List<CompanyDTO> getAllCompanies() {
//        return companyService.getAllCompanies();
//    }

    @GetMapping("")
    public CompanyDTO getCompanyById(@RequestParam(name = "Id") Long id) {
        CompanyDTO companyDTO = companyService.getCompanyById(id);
        return companyDTO;
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
