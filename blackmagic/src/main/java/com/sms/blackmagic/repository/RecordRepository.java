package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByRecordId(Long recordId);
    List<Record> findByCompanyId(Integer companyId);
}
