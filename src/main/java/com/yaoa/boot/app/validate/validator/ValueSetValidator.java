package com.yaoa.common.validate.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yaoa.common.validate.annotation.ValueSet;

/**
 * 手机号验证器
 * @author ChenJianhui
 */
public class ValueSetValidator implements ConstraintValidator<ValueSet, Object>{

	private ValueSet valueSet;
	
	/**
	 * 初始化验证参数
	 */
	@Override
	public void initialize(ValueSet valueSet) {
		this.valueSet = valueSet;
	}

	/**
	 * 验证参数是否有效
	 * @param target 验证目标
	 */
	@Override
	public boolean isValid(Object target, ConstraintValidatorContext context) {
		if(target == null){
			return true;
		}
		if(Arrays.asList(valueSet.values()).contains(target.toString())){
			return true;
		}else{
			 String messageTemplate = context.getDefaultConstraintMessageTemplate();  
	         context.disableDefaultConstraintViolation();  
	         context.buildConstraintViolationWithTemplate(messageTemplate).addPropertyNode("values").addConstraintViolation();  
	         return false;
		}
		
	}

}
