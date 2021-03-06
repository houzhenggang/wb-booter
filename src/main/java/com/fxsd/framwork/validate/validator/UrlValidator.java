package com.fxsd.framwork.validate.validator;

import com.fxsd.framwork.util.ValidUtils;
import com.fxsd.framwork.validate.annotation.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 手机号验证器
 * @author ChenJianhui
 */
public class UrlValidator implements ConstraintValidator<URL, String>{

	@Override
	public void initialize(URL url) {
		
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		if(!ValidUtils.isValid(url)){
			return true;
		}else{
			String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
			Pattern p = Pattern.compile(regex);  
			Matcher m = p.matcher(url); 
			return m.find();
		}
	}
}
