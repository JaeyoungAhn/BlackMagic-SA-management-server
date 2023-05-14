package com.sms.blackmagic.service;

import com.sms.blackmagic.model.AttachedFile;
import com.sms.blackmagic.repository.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachedFileService {

    private final AttachedFileRepository attachedFileRepository;

    public AttachedFile createFile(AttachedFile attachedFile) {
        attachedFileRepository.save(attachedFile);
        return attachedFile;
    }

    public AttachedFile readFile(Long fileId) {
        AttachedFile attachedFile = attachedFileRepository.findByAttachedFileId(fileId);
        return attachedFile;
    }

    public void deleteFile(Long fileId) {
        attachedFileRepository.deleteById(fileId);
    }
}
