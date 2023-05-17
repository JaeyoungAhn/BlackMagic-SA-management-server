package com.sms.blackmagic.service;

import com.sms.blackmagic.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Integer userId);
    UserDTO findUserById(Integer userId);
    List<UserDTO> findAllUsers();
}
