package com.sms.blackmagic.util;

import com.sms.blackmagic.dto.UserDTO;
import com.sms.blackmagic.model.Company;
import com.sms.blackmagic.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User fromUserDTO(UserDTO userDTO) {
        User user = new User();
        Company company = new Company();
        user.setUserId(userDTO.getUserId());
        user.setAccountName(userDTO.getAccountName());
        if (userDTO.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        } else {
            user.setPassword(userDTO.getPassword());
        }
        user.setAuthority(userDTO.getAuthority());
        user.setIpAddress(userDTO.getIpAddress());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setContact(userDTO.getContact());
        user.setNote(userDTO.getNote());
        company.setCompanyId(userDTO.getCompanyId());
        user.setCompany(company);
        return user;
    }

    public UserDTO fromUser(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getAccountName(),
                "",
                user.getAuthority(),
                user.getIpAddress(),
                user.getName(),
                user.getEmail(),
                user.getContact(),
                user.getNote(),
                user.getCompany().getCompanyId()
        );
    }
}
