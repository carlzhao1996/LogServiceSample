package com.logservice.demo.controller;

import com.logservice.demo.annotation.AttrLogEvent;
import com.logservice.demo.annotation.Attribute;
import com.logservice.demo.annotation.ObjLogEvent;
import com.logservice.demo.dto.UserDTO;
import com.logservice.demo.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    @ObjLogEvent(operation = "Add",type = "User",attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码")
    })
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return "Success";
    }

    @AttrLogEvent(operation = "Get",type = "User", attributes = {
            @Attribute(key = "id",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名")
    })
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public UserDTO getUser(@RequestParam String id){
        UserDTO user = userService.getUser(id);
        return user;
    }



    @RequestMapping(value = "/UpdateUser",method = RequestMethod.POST)
    public String updateUser(@RequestBody UserDTO userDTO){
        return "Success";
    }


}
