package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 기관 정보 생성
    @PostMapping("/company")
    public Company createCompany(@RequestBody Company company) {
        Company newCompany = companyService.createCompany(company);
        return newCompany;
    }

    // 기관 정보 수정


    // 기관 정보 삭제


    // 기관 상세 조회
    @GetMapping("/company/{companyId}")
    public Company detail(@PathVariable("companyId") Integer companyId) {
        Company company = companyService.getCompanyDetail(companyId);
        return company;
    }
}
