package com.logservice.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Carl Zhao
 * 用于对象更新操作
 * **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateLogEvent {
    String Operation() default "Update";
    String type();
    String updateKey(); //更新键 e.g. Id
    Class clazz();
    Attribute [] attributes();
}
