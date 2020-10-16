package com.logservice.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Carl Zhao
 * 用于变量传递操作
 * **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttrLogEvent {
    String operation();
    String type();
    Attribute [] attributes();
}
