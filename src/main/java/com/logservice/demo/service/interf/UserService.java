package com.logservice.demo.service.interf;

import com.logservice.demo.dto.UserDTO;

public interface UserService {
    void addUser(UserDTO userDTO);

    UserDTO getUser(String id);


}
