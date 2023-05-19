package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    private final CompanyService companyService;

    // 기관 목록 조회
    @GetMapping("/company/list")
    public List<Company> list() {
        return companyService.getCompanyList();
    }

    // 기관 정보 생성
    @PostMapping("/company")
    public Company createCompany(@RequestBody Company company) {
        Company newCompany = companyService.createCompany(company);
        return newCompany;
    }

    // 기관 정보 수정
    @PatchMapping("/company/{companyId}")
    public Company edit(@PathVariable("companyId") Integer companyId, @RequestBody Company company){
        Company updatedCompany = companyService.updateCompany(companyId, company);
        return updatedCompany;
    }

    // 기관 정보 삭제
    @DeleteMapping("/company/{companyId}")
    public Company delete(@PathVariable("companyId") Integer companyId){
        companyService.deleteCompany(companyId);
        return companyService.getCompanyDetail(companyId);
    }

    // 기관 상세 조회
    @GetMapping("/company/{companyId}")
    public Company detail(@PathVariable("companyId") Integer companyId) {
        Company company = companyService.getCompanyDetail(companyId);
        return company;
    }
}
