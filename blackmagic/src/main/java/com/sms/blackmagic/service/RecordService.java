package com.sms.blackmagic.service;

import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
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
