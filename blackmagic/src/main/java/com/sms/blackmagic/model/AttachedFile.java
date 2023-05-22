package com.sms.blackmagic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="attached_file")
@NoArgsConstructor
@AllArgsConstructor
public class AttachedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_file_id")
    private Long attachedFileId;

    @Column(name = "upload_state")
    private Boolean uploadState;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "record_id")
    private Record record;
}
