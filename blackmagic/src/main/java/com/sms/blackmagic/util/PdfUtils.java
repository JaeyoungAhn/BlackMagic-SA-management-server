package com.sms.blackmagic.util;

import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.service.CompanyService;
import com.sms.blackmagic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;

@Component
@RequiredArgsConstructor
public class PdfUtils {
    public static File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(URLDecoder.decode(file.getOriginalFilename(), "UTF-8"));
        convertedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public static Record parsePdf(File pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);
        Record record = stringToRecord(text);
        document.close();

        return record;
    }

    public static Record stringToRecord(String text) {
        Record record = new Record();

        Company company = new Company();
        String companyName = extractValue(text, "소속기관명");
        company.setCompanyName(companyName);

        record.setCompany(company);
        record.setDeptName(extractValue(text, "부서명"));
        record.setUserName(extractValue(text, "사용자"));
        record.setHddModel(extractValue(text, "HDD 모델명"));
        record.setHddsn(extractValue(text, "HDD SN"));
        record.setHddSize(extractValue(text, "HDD 크기"));
        record.setDeletedDate(extractValue(text, "삭제일자"));
        record.setDeletedStartTime(extractValue(text, "삭제시작시간"));
        record.setDeletedEndTime(extractValue(text, "삭제종료시간"));
        record.setTimeTaken(extractValue(text, "소요시간"));
        record.setDeletionWay(extractValue(text, "삭제 방법"));
        record.setOverwriteCount(extractValue(text, "덮어쓰기 횟수"));
        record.setVerificationCode(extractValue(text, "확인코드"));

        return record;

    }

    public static String extractValue(String input, String item) {
        int startIndex = input.indexOf(item);
        if (startIndex != -1) {
            startIndex += item.length();
            int endIndex = input.indexOf("\n", startIndex);
            if (endIndex != -1) {
                return input.substring(startIndex, endIndex).trim();
            }
        }
        return "";
    }

    public static void savePdf(MultipartFile file, String newName) throws IOException {
        String PDF_FOLDER = "src/main/resources/pdf";

        File pdfDirectory = new File(PDF_FOLDER);
        if (!pdfDirectory.exists()) {
            pdfDirectory.mkdirs(); // 폴더가 존재하지 않을 경우 생성합니다.
        }

        File destinationFile = new File(pdfDirectory, newName);
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }


}