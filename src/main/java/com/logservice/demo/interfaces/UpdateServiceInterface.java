package com.logservice.demo.interfaces;

import java.util.Map;

public interface UpdateServiceInterface {
    Map<String,String> getOldValue(String id);

    Map<String,String> getNewValue(String id);
}
