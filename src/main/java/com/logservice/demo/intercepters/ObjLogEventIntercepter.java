package com.logservice.demo.intercepters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.logservice.demo.annotation.Attribute;
import com.logservice.demo.annotation.ObjLogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ObjLogEventIntercepter {

    @Around("@annotation(objLogEvent)")
    public Object around(ProceedingJoinPoint joinPoint, ObjLogEvent objLogEvent) throws Exception,Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ObjLogEvent methodAnnotation = method.getAnnotation(ObjLogEvent.class);

        Map<String,String> keyMap = new HashMap<>();
        //存储attribute进入map
        for (Attribute attribute : methodAnnotation.attributes()) {
            keyMap.put(attribute.key(),attribute.name());
        }

        Object[] args = joinPoint.getArgs();
        JSONArray jsonArrayInput = (JSONArray) JSON.toJSON(args);

        StringBuffer inputDataBuffer = new StringBuffer().append(methodAnnotation.operation()+"信息：");
        for(int i=0,size = jsonArrayInput.size();i<size;i++){
            for(String key : keyMap.keySet()){
               String attributeVal = jsonArrayInput.getJSONObject(i).getString(key);
               String attributeKey = keyMap.get(key);
               String attribute = attributeKey+":"+attributeVal+";";
               inputDataBuffer.append(attribute);
            }
        }
        JSONObject json = new JSONObject();
        json.put("operation",methodAnnotation.operation());
        json.put("type",methodAnnotation.type());
        json.put("info",inputDataBuffer);
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        json.put("date",ft.format(date));
        json.put("user","Carl");
        System.out.println(json.toJSONString());
        Object result = joinPoint.proceed();
        return result;
    }
}
