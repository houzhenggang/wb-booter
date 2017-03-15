package com.fxsd.framwork.validate.annotation;


import com.fxsd.framwork.validate.validator.ChineseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证字符串是否是中文
 * @author ChenJianhui
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = ChineseValidator.class)
public @interface Chinese {

	String message() default "非法字符";  
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
  
}
