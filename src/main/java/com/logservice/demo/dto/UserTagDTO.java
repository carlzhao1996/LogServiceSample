package com.logservice.demo.dto;

import java.util.List;

public class UserTagDTO {
    String userId;

    List<String> userTags;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserTags() {
        return userTags;
    }

    public void setUserTags(List<String> userTags) {
        this.userTags = userTags;
    }
}
