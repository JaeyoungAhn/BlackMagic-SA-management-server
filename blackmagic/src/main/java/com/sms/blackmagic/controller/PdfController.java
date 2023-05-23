package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.*;
import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.service.AttachedFileService;
import com.sms.blackmagic.service.AuditLogService;
import com.sms.blackmagic.service.CompanyService;
import com.sms.blackmagic.service.RecordService;
import com.sms.blackmagic.util.PdfUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PdfController {

    private final RecordService recordService;
    private final CompanyService companyService;
    private final AttachedFileService attachedFileService;
    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || !file.getContentType().equalsIgnoreCase("application/pdf")) {
            return ResponseEntity.badRequest().body("PDF file is required");
        }

        File pdfFile = PdfUtils.convertMultipartFileToFile(file);
        Record record = PdfUtils.parsePdf(pdfFile);
        Company company = companyService.findByCompanyName(record.getCompany().getCompanyName());
        record.setCompany(company);

        Record getRecord = recordService.createRecord(record);
        PdfUtils.savePdf(file, getRecord.getRecordId().toString() + ".pdf");

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setUploadState(true);
        attachedFile.setRecord(getRecord);
        attachedFileService.createFile(attachedFile);

        /*
        감사 로그 임시 유저 추가
         */
        User user = new User();
        user.setUserId(1);

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setRecord(getRecord);
        // 로그인 로그아웃에서는 setRecord를 null로 처리
        auditLog.setWorkType("PDF UPLOAD");

        auditLogService.createLog(auditLog);


        return ResponseEntity.ok("success");
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileId) throws IOException {
        // 파일 경로
        String filePath = "src/main/resources/pdf/" + fileId + ".pdf";

        // 파일 리소스 생성
        Resource resource = new FileSystemResource(filePath);

        // 파일이 존재하는지 확인
        if (resource.exists()) {
            String mimeType = Files.probeContentType(new File(filePath).toPath());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileId)
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        } else {
            throw new RuntimeException("File not found");
        }
    }




}
