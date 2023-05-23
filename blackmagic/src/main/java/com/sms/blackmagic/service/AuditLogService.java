package com.sms.blackmagic.service;


import com.sms.blackmagic.model.AuditLog;
import com.sms.blackmagic.repository.AuditlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditlogRepository auditlogRepository;

    public List<AuditLog> getLogList() {
        List<AuditLog> auditLogList = auditlogRepository.findAll();
        return auditLogList;
    }

    public AuditLog createLog(AuditLog auditLog) {
        auditlogRepository.save(auditLog);
        return auditLog;
    }


}
