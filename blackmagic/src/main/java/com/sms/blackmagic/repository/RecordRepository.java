package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByRecordId(Long recordId);
}
