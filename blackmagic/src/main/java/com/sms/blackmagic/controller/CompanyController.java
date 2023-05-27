package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.model.User;
import com.sms.blackmagic.service.CompanyService;
import com.sms.blackmagic.util.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<Company> list(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER"))){
            throw new UnauthorizedAccessException("authorization failed");
        }
        return companyService.getCompanyList();
    }

    // 기관 정보 생성
    @PostMapping("/company")
    public Company createCompany(@RequestBody Company company, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER"))){
            throw new UnauthorizedAccessException("authorization failed");
        }
        Company newCompany = companyService.createCompany(company);
        return newCompany;
    }

    // 기관 정보 수정
    @PatchMapping("/company/{companyId}")
    public Company edit(@PathVariable("companyId") Integer companyId, @RequestBody Company company, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER"))){
            throw new UnauthorizedAccessException("authorization failed");
        }
        Company updatedCompany = companyService.updateCompany(companyId, company);
        return updatedCompany;
    }

    // 기관 정보 삭제
    @DeleteMapping("/company/{companyId}")
    public Company delete(@PathVariable("companyId") Integer companyId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER"))){
            throw new UnauthorizedAccessException("authorization failed");
        }
        companyService.deleteCompany(companyId);
        return companyService.getCompanyDetail(companyId);
    }

    // 기관 상세 조회
    @GetMapping("/company/{companyId}")
    public Company detail(@PathVariable("companyId") Integer companyId, Authentication authentication) {
        // JWT 내 company_id와 조회할 company_id가 다르면 에러 발생
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER")) &&
                !((User)userDetails).getCompanyId().equals(companyId)){
            throw new UnauthorizedAccessException("authorization failed");
        }
        Company company = companyService.getCompanyDetail(companyId);
        return company;
    }
}
