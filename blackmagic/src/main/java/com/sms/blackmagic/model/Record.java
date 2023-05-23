package com.sms.blackmagic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="record")
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "hdd_model")
    private String hddModel;

    @Column(name = "hddsn")
    private String hddsn;

    @Column(name = "hdd_size")
    private String hddSize;

    @Column(name = "deleted_date")
    private String deletedDate;

    @Column(name = "deleted_start_time")
    private String deletedStartTime;

    @Column(name = "deleted_end_time")
    private String deletedEndTime;

    @Column(name = "time_taken")
    private String TimeTaken;

    @Column(name = "deletion_way")
    private String deletionWay;

    @Column(name = "overwrite_count")
    private String overwriteCount;

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;
}
