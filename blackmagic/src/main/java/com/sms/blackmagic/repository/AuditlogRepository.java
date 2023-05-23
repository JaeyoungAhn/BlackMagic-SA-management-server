package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditlogRepository extends JpaRepository<AuditLog, Long> {
}
