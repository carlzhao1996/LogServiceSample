package com.logservice.demo.intercepters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.logservice.demo.annotation.ObjLogEvent;
import com.logservice.demo.annotation.UpdateLogEvent;
import com.logservice.demo.component.Util;
import com.logservice.demo.interfaces.UpdateServiceInterface;
import com.logservice.demo.operation.UpdateOperation;
import netscape.javascript.JSObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class UpdateLogEventInterceptor {
    @Around("@annotation(updateLogEvent)")
    public Object around(ProceedingJoinPoint joinPoint, UpdateLogEvent updateLogEvent) throws Exception,Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        UpdateLogEvent methodAnnotation = method.getAnnotation(UpdateLogEvent.class);

        String oidKey = methodAnnotation.OidKey();

        Object [] args = joinPoint.getArgs();
        JSONArray jsonArray = (JSONArray) JSON.toJSON(args);
//        System.out.println(json.toJSONString());
        JSONObject jsonData = (JSONObject) jsonArray.get(0);
        String idVal = jsonData.getString(oidKey);

        UpdateServiceInterface updateService = (UpdateServiceInterface) Util.getBean(updateLogEvent.clazz());

        Map<String,String> oldVal = updateService.getOldValue(idVal);
        oldVal.forEach((k,v)->{
            String newVal = jsonData.getString(k);
            if(!v.equals(newVal)){
                System.out.println("key: "+k+" 旧值："+v+" "+"新值："+newVal);
            }
        });

        Object result = joinPoint.proceed();
        return result;
    }
}
