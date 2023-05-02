package com.sms.blackmagic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="authority")
    private int authority;

    @Column(name="account_name")
    private String accountName;

    @Column(name="password")
    private String password;

    @Column(name="register_date")
    @CreationTimestamp
    private LocalDateTime registerDate;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="contact")
    private String contact;

    @Column(name="note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;
}
