package com.fxsd.framwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注释DTO中属性对应的查询结果中的列
 * @author cjh
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeQueryResultColumn {
	
    int index();
}
