package com.sms.blackmagic.controller;

import java.io.File;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sms.blackmagic.model.AttachedFile;
import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.model.Record;
import com.sms.blackmagic.service.AttachedFileService;
import com.sms.blackmagic.service.CompanyService;
import com.sms.blackmagic.service.RecordService;
import com.sms.blackmagic.util.PdfUtils;
import java.nio.file.Files;
 
@RestController
public class sftpController {
	
	private final RecordService recordService;
    private final CompanyService companyService;
    private final AttachedFileService attachedFileService;
    
    
	
	@Value("${local.pdf.directory}")
    private String localPdfDirectory;
    //프로젝트 경로
    private static final String projPath = "C://sshtest";
    
    private WatchKey watchKey;
    Semaphore semaphore = new Semaphore(1);
    
    public sftpController (RecordService recordService, CompanyService companyService, AttachedFileService attachedFileService) {
    	
    	this.recordService = recordService;
    	this.companyService = companyService;
    	this.attachedFileService = attachedFileService;
		
    }
    
    
    
    
    @PostConstruct
    public void init() throws IOException {
        //watchService 생성
        WatchService watchService = FileSystems.getDefault().newWatchService();
        //경로 생성
        Path path = Paths.get(projPath);
        //해당 디렉토리 경로에 와치서비스와 이벤트 등록
        path.register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.OVERFLOW);
        
        Thread thread = new Thread(()-> {
        	
            while(true) {
            	
                try {
                	semaphore.acquire();
                    watchKey = watchService.take();//이벤트가 오길 대기(Blocking)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                	semaphore.release();
                }
                List<WatchEvent<?>> events = watchKey.pollEvents();//이벤트들을 가져옴
                
                for(WatchEvent<?> event : events) {
                    //이벤트 종류
                    Kind<?> kind = event.kind();
                    //경로 
                   
                    Path paths = (Path)event.context();
                    
                    if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                    	 
                        System.out.println("created something in directory");
                        
                        String fileName = paths.getFileName().toString();
                       
                        
                        File pdfFile =  new File(projPath + '\\' + fileName);
                        File pdfDirectory = new File("src/main/resources/pdf/");
                        if (!pdfDirectory.exists()) {
                            pdfDirectory.mkdirs(); // 폴더가 존재하지 않을 경우 생성합니다.
                        }
                        String localFilePath = "src/main/resources/pdf/"+ fileName;
                        
                        File destFile = new File(localFilePath);
                        

                        // 파일을 로컬 파일로 복사합니다.
                        try {
							Files.copy(pdfFile.toPath(), destFile.toPath());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						try {
							semaphore.acquire();
							
	                        
	                        Record record;
							record = PdfUtils.parsePdf(pdfFile);
							Company company = companyService.findByCompanyName(record.getCompany().getCompanyName());
	                        record.setCompany(company);
	                        
	                        Record getRecord = recordService.createRecord(record);
	                        
	                        
	                        AttachedFile attachedFile = new AttachedFile();
	                        attachedFile.setUploadState(true);
	                        
	                        attachedFile.setRecord(getRecord);
	                        attachedFileService.createFile(attachedFile);
	                        if(pdfFile.delete()){ // f.delete 파일 삭제에 성공하면 true, 실패하면 false
	                            System.out.println("file Deleted okay");
	                        }else{
	                            System.out.println("file Deleted can't");
	                        }
	                        fileName = null;
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							semaphore.release();
						}
							
						
                        
                    	 
                        
                    }else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                        System.out.println("delete something in directory");
                    }else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                        System.out.println("modified something in directory");
                    }else if(kind.equals(StandardWatchEventKinds.OVERFLOW)) {
                        System.out.println("overflow");
                    }else {
                        System.out.println("hello world");
                    }
                }
                if(!watchKey.reset()) {
                    try {
                        watchService.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        	
        	});
        
        thread.start();
        
    }
    @GetMapping("/")
    public String test() {
        System.out.println(projPath);        
        return "hello";
    }
    
    
    
    
}