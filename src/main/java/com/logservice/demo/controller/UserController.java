package com.logservice.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.logservice.demo.annotation.AttrLogEvent;
import com.logservice.demo.annotation.Attribute;
import com.logservice.demo.annotation.ObjLogEvent;
import com.logservice.demo.dto.UserDTO;
import com.logservice.demo.dto.UserGroupDTO;
import com.logservice.demo.dto.UserTagDTO;
import com.logservice.demo.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    @ObjLogEvent(operation = "Add",type = "User",attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码"),
            @Attribute(key = "age",name="年龄")
    })
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return "Success";
    }

    @ObjLogEvent(operation = "Add",type = "User",attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码"),
            @Attribute(key = "age",name="年龄")
    })
    @RequestMapping(value = "/addUser1", method = RequestMethod.POST)
    public String addUserOne(@RequestBody JSONObject jsonObject){
//        userService.addUser(userDTO);
        return "Success";
    }

    @ObjLogEvent(operation = "Add",type = "User",attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码"),
            @Attribute(key = "age",name="年龄")
    })
    @RequestMapping(value = "/addoid", method = RequestMethod.POST)
    public String testAddSingleValueList(@RequestBody List<String> ids){
        return "Success";
    }


    @ObjLogEvent(operation = "批量添加",type = "User",attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码"),
            @Attribute(key = "age",name="年龄")
    })
    @RequestMapping(value = "/addMultiUser", method = RequestMethod.POST)
    public String addMultiUser(@RequestBody List<UserDTO> userDTO){
        return "Success";
    }

    @ObjLogEvent(operation = "Add",type = "User",attributes = {
            @Attribute(key="groupId",name="用户组名"),
            @Attribute(key = "users",name = "用户组"),
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名"),
            @Attribute(key = "password",name = "用户密码"),
            @Attribute(key = "age",name="年龄")
    })
    @RequestMapping(value = "/addUserGroup", method = RequestMethod.POST)
    public String addUserAndGroup(@RequestBody UserGroupDTO userGroupDTO){
        return "Success";
    }

    @AttrLogEvent(operation = "Get",type = "User", attributes = {
            @Attribute(key = "id",name = "用户Id"),
            @Attribute(key = "userName",name = "用户名")
    })
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public UserDTO getUser(@RequestParam String id,@RequestParam String userName){
        UserDTO user = userService.getUser(id);
        return user;
    }

    @ObjLogEvent(operation = "Add", type = "User", attributes = {
            @Attribute(key = "userId",name = "用户Id"),
            @Attribute(key="userTags",name = "用户tag")
    })
    @RequestMapping(value = "/addUserTags")
    public String addUserAndCustomTag(@RequestBody UserTagDTO userTagDTO){
        return "Success";
    }



    @RequestMapping(value = "/UpdateUser",method = RequestMethod.POST)
    public String updateUser(@RequestBody UserDTO userDTO){
        return "Success";
    }


}
