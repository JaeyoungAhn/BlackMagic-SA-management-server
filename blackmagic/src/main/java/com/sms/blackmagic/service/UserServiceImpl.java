package com.sms.blackmagic.service;

import com.sms.blackmagic.dto.UserDTO;
import com.sms.blackmagic.model.User;
import com.sms.blackmagic.repository.UserRepository;
import com.sms.blackmagic.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.fromUserDTO(userDTO);
        User savedUser = userRepository.save(user);
        return userConverter.fromUser(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userConverter.fromUserDTO(userDTO);
        User updatedUser = userRepository.save(user);
        return userConverter.fromUser(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO findUserById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? userConverter.fromUser(user) : null;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::fromUser)
                .collect(Collectors.toList());
    }
}
