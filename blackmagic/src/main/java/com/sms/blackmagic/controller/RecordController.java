package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/list")
    public List<Record> readRecordList() {
        return recordService.getRecordList();
    }

    @GetMapping("/{recordId}")
    public Record readRecordById(@PathVariable("recordId") long recordId) {
        return recordService.readRecord(recordId);
    }

    @GetMapping("/company/{companyId}")
    public List<Record> readRecordByCompany(@PathVariable("companyId") Integer companyId) {
        return recordService.getRecordListByCompany(companyId);
    }
}
