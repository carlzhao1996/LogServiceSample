package com.logservice.demo.intercepters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.logservice.demo.annotation.AttrLogEvent;
import com.logservice.demo.annotation.Attribute;
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
public class AttrLogEventIntercepter {

    @Around("@annotation(attrLogEvent)")
    public Object around(ProceedingJoinPoint joinPoint, AttrLogEvent attrLogEvent) throws Exception,Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AttrLogEvent methodAnnotation = method.getAnnotation(AttrLogEvent.class);

        Object [] args = joinPoint.getArgs();
        JSONArray jsonArrayArgs = (JSONArray) JSON.toJSON(args);
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();

        Map<String,String> inputArgsMap = new HashMap();
        for (int i=0,size = jsonArrayArgs.size();i<size;i++){
            for (int j=0;j<argNames.length;j++){
                if(i==j){
                    inputArgsMap.put(argNames[j],jsonArrayArgs.get(i).toString());
                }
            }
        }

        Map<String,String> keyMap = new HashMap<>();
        //存储attribute进入map
        for (Attribute attribute : methodAnnotation.attributes()) {
            keyMap.put(attribute.key(),attribute.name());
        }

        StringBuffer inputDataBuffer = new StringBuffer().append(methodAnnotation.operation()+"信息：");
        inputArgsMap.forEach((inputArgKey,inputArgVal)->{
            String attributeKey = keyMap.get(inputArgKey);
            String attributeVal = inputArgVal;
            String attribute = attributeKey+":"+attributeVal+";";
            inputDataBuffer.append(attribute);
        });

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
