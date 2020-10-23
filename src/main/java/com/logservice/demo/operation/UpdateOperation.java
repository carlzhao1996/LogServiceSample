package com.logservice.demo.operation;

import com.logservice.demo.interfaces.UpdateServiceInterface;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateOperation implements UpdateServiceInterface {
    @Override
    public Map<String, String> getOldValue(String id) {
        System.out.println("id: "+id);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId","aaa");
        userMap.put("userName","name");
        userMap.put("password","oldPassword");
        userMap.put("age","24");
        return userMap;
    }

    @Override
    public Map<String, String> getNewValue(String id) {
        return null;
    }
}
