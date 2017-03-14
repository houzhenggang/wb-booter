package com.yaoa.boot.app.validate.validator;

import com.yaoa.boot.app.util.StringUtils;
import com.yaoa.boot.app.util.ValidUtils;
import com.yaoa.boot.app.validate.annotation.Chinese;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号验证器
 * @author ChenJianhui
 */
public class ChineseValidator implements ConstraintValidator<Chinese, String> {

	@Override
	public void initialize(Chinese chinese) {
		
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {
		if(!ValidUtils.isValid(str)){
			return true;
		}
		return StringUtils.isChinese(str);
	}

}
