package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

    AttachedFile findByAttachedFileId(Long attachedFileId);
}
