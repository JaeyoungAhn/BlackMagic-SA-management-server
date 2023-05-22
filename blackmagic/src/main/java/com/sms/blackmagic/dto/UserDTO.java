package com.sms.blackmagic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String accountName;
    private String password;
    private int authority;
    private String ipAddress;
    private String name;
    private String email;
    private String contact;
    private String note;
    private Integer companyId;
    private LocalDateTime registerDate;
}
