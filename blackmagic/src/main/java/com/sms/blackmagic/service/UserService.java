package com.sms.blackmagic.service;

import com.sms.blackmagic.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Integer userId);
    UserDTO findUserById(Integer userId);
}
