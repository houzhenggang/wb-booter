package com.fxsd.framwork.result;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by administer on 2017/2/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {
    /**
     * 过滤对象类型
     *
     * @return
     */
    Class<?> type();

    /**
     * 包含字段
     *
     * @return
     */
    String include() default "";

    /**
     * 排除字段
     *
     * @return
     */
    String exclude() default "";
}
