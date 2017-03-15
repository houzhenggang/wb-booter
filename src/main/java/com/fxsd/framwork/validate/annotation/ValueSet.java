package com.fxsd.framwork.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fxsd.framwork.validate.validator.ValueSetValidator;

/**
 * 验证参数在某个集合范围内
 * @author ChenJianhui
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = ValueSetValidator.class)
public @interface ValueSet {

	String message() default "输入的参数不在{values}中";
	
	/**
	 * 值集合
	 * @return
	 */
	public String[] values();
	
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};  
  
}
