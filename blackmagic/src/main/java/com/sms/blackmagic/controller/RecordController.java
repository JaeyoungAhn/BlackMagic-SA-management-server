package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.model.User;
import com.sms.blackmagic.service.RecordService;
import com.sms.blackmagic.util.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/list")
    public List<Record> readRecordList(Authentication authentication) {
        return recordService.getRecordList(authentication);
    }

    @GetMapping("/{recordId}")
    public Record readRecordById(@PathVariable("recordId") long recordId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // JWT 내 company_id와 조회할 record_id의 company_id가 다르면 에러 발생
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER")) &&
                !((User)userDetails).getCompanyId().equals(recordService.readRecord(recordId).getCompany().getCompanyId())){
            System.out.println("authorization failed");
            throw new UnauthorizedAccessException("authorization failed");
        }
        return recordService.readRecord(recordId);
    }

    @GetMapping("/company/{companyId}")
    public List<Record> readRecordByCompany(@PathVariable("companyId") Integer companyId, Authentication authentication) {
        // 인가를 위한 JWT 내 유저 정보
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // JWT 내 company_id와 조회할 company_id가 다르면 에러 발생
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER")) &&
                !((User)userDetails).getCompanyId().equals(companyId)){
            System.out.println("authorization failed");
            throw new UnauthorizedAccessException("authorization failed");
        }
        return recordService.getRecordListByCompany(companyId);
    }
}
