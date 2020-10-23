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
        JSONArray jsonArray = (JSONArray) JSON.toJSON(args);
        StringBuffer inputDataBuffer = new StringBuffer();

        if(jsonArray.get(0) instanceof JSONArray){
            JSONArray jsonArrayListData = (JSONArray) jsonArray.get(0);
            JSONArray resultArray = new JSONArray();
            for(int i=0,size = jsonArrayListData.size();i<size;i++){
                JSONObject jsonObject = new JSONObject();
                for(String key : keyMap.keySet()){
                    String attributeKey = keyMap.get(key);
                    String attributeVal = jsonArrayListData.getJSONObject(i).getString(key);
                    jsonObject.put(attributeKey,attributeVal);
                }
               resultArray.add(jsonObject);
            }
            inputDataBuffer.append(resultArray.toJSONString());
        }
        else if(jsonArray.get(0) instanceof JSONObject){
            JSONObject jsonObject = new JSONObject();
            for (String key : keyMap.keySet()){
                if (((JSONObject) jsonArray.get(0)).get(key) instanceof JSONArray){

                    //执行遍历json array 操作
                    JSONArray jsonArrayData = (JSONArray) ((JSONObject) jsonArray.get(0)).get(key);
                    if (jsonArrayData.size()==0){
                        continue;
                    }

                    String listKey = keyMap.get(key);

                    //若是非对象数组
                    if (!(jsonArrayData.get(0) instanceof JSONObject)){
                        JSONArray jsonArray1 = new JSONArray();
                        for(int i=0,size = jsonArrayData.size();i<size;i++){
                            String Value = jsonArrayData.get(i).toString();
                            jsonArray1.add(Value);
                        }
                        jsonObject.put(listKey,jsonArray1);
                    }else {//对象数组
                        JSONArray jsonArray1 = new JSONArray();
                        for(int i=0,size = jsonArrayData.size();i<size;i++){
                            JSONObject jsonObject1 = new JSONObject();
                            for(String key1 : keyMap.keySet()){
                                String attributeKey = keyMap.get(key1);
                                String attributeVal = jsonArrayData.getJSONObject(i).getString(key1);
                                if(attributeVal!=null) {
//                                    String attribute = attributeKey + ":" + attributeVal + ";";
                                    jsonObject1.put(attributeKey,attributeVal);
                                }
                            }
                            jsonArray1.add(jsonObject1);
                        }
                        jsonObject.put(listKey,jsonArray1);
                    }

                }else {
                    String attributeKey = keyMap.get(key);
                    String attributeVal = jsonArray.getJSONObject(0).getString(key);
                    if(attributeVal!=null) {
                        jsonObject.put(attributeKey,attributeVal);
                    }
                }
            }
            inputDataBuffer.append(jsonObject.toJSONString());
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
