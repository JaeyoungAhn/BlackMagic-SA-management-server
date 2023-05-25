package com.sms.blackmagic.util;
import com.sms.blackmagic.model.AuditLog;
import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.model.User;
import com.sms.blackmagic.repository.UserRepository;
import com.sms.blackmagic.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditLogUtil {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    public void saveAuditLog(String token, Record record, int type) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByAccountName(username);
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);

        if(record != null) auditLog.setRecord(record);

        switch(type){
            case 1:
                auditLog.setWorkType("PDF UPLOAD");
                break;
            case 2:
                auditLog.setWorkType("LOG-IN");
                break;
            case 3:
                auditLog.setWorkType("LOG-OUT");
                break;
        }
        auditLogService.createLog(auditLog);
    }
}
