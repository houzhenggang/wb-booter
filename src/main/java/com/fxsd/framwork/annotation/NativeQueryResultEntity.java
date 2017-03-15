package com.fxsd.framwork.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解原生SQL查询返回结果映射对应的DTO
 * @author cjh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeQueryResultEntity {
	
}