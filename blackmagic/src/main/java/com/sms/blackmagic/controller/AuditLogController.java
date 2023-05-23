package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.AuditLog;
import com.sms.blackmagic.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditLog")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/list")
    public List<AuditLog> list() {
        return auditLogService.getLogList();
    }

}
