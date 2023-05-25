package com.sms.blackmagic.service;

import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.model.User;
import com.sms.blackmagic.repository.RecordRepository;
import com.sms.blackmagic.util.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record createRecord(Record record) {
        recordRepository.save(record);
        return record;
    }

    public Record readRecord(Long recordId) {
        Record record = recordRepository.findByRecordId(recordId);
        return record;
    }

    public List<Record> getRecordList() {
        List<Record> recordList = recordRepository.findAll();
        return recordList;
    }

    public List<Record> getRecordList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // MASTER가 JWT 내 company_id와 조회할 list의 company_id가 다른 조회 요청 시 에러
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MASTER"))) {
            Integer companyId = ((User) userDetails).getCompanyId();
            List<Record> recordList = recordRepository.findByCompanyId(companyId);
            return recordList;
        }


        List<Record> recordList = recordRepository.findAll();
        return recordList;
    }

    public List<Record> getRecordListByCompany(Integer companyId) {
        List<Record> recordList = recordRepository.findAll();
        List<Record> recordListByCompany = new ArrayList<>();

        for(Record record: recordList) {
            if(Objects.equals(record.getCompany().getCompanyId(), companyId))
                recordListByCompany.add(record);
        }

        return recordListByCompany;
    }

    public void deleteRecord(Long recordId) {
        recordRepository.deleteById(recordId);
    }
}
