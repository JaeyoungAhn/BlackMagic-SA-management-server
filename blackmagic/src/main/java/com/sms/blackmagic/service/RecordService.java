package com.sms.blackmagic.service;

import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    public void deleteRecord(Long recordId) {
        recordRepository.deleteById(recordId);
    }
}
