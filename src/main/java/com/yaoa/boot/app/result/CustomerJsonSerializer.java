package com.yaoa.boot.app.result;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.text.SimpleDateFormat;

/**
 * 自定义JSON序列化器
 * Created by cjh on 2017/2/27.
 */
public class CustomerJsonSerializer extends ObjectMapper {

    static final String DYNC_INCLUDE = "DYNC_INCLUDE";

    static final String DYNC_EXCLUDE = "DYNC_EXCLLUDE";

    public CustomerJsonSerializer() {
        //不序列化空对象
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //序列化日期时以timestamps输出，默认true，
        // 比如如果一个类中有private Date date;这种日期属性，序列化后为：{"date" : 1413800730456}，
        // 若不为true，则为{"date" : "2014-10-20T10:26:06.604+0000"}
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //支持JSON格式提交可以多字段或少字段
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonFilter(DYNC_EXCLUDE)
    interface DynamicExclude {
    }

    @JsonFilter(DYNC_INCLUDE)
    interface DynamicInclude {
    }

    /**
     * @param clazz   需要设置规则的Class
     * @param includes 转换时包含哪些字段
     * @param excludes 转换时过滤哪些字段
     */
    public void filter(Class<?> clazz, String includes, String excludes) {
        if (clazz == null) return;
        if (includes != null && includes.length() > 0) {
            this.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_INCLUDE,
                    SimpleBeanPropertyFilter.filterOutAllExcept(includes.split(","))));
            this.addMixIn(clazz, DynamicInclude.class);
        } else if (excludes != null && excludes.length() > 0) {
            this.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_EXCLUDE,
                    SimpleBeanPropertyFilter.serializeAllExcept(excludes.split(","))));
            this.addMixIn(clazz, DynamicExclude.class);
        }
    }
}
