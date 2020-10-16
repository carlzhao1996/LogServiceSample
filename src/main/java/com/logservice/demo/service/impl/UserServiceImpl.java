package com.logservice.demo.service.impl;

import com.logservice.demo.dto.UserDTO;
import com.logservice.demo.service.interf.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(UserDTO userDTO) {
        System.out.println("添加用户: "+ userDTO.getUserName());
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO user = new UserDTO();
        user.setUserId("TestId");
        user.setUserName("TestUserName");
        user.setPassword("TestPassword");
        return user;
    }
}
