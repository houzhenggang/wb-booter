package com.fxsd.framwork.resolver;

import com.fxsd.framwork.exception.ApplicationException;
import com.fxsd.framwork.form.Form;
import com.fxsd.framwork.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import java.util.List;

/**
 * 请求参数解析器
 * @author cjh
 */
public class FormArgumentResolver extends AbstractMessageConverterMethodArgumentResolver {

	public FormArgumentResolver(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

    /**
     * 判断是否是支持解析的参数
     * @param parameter
     * @return
     */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?>[] interfaces = parameter.getParameterType().getInterfaces();
		for (Class<?> i : interfaces) {
			if(i == Form.class){
				return true;
			}
		}
		return false;
	}

    /**
     * 解析参数
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Object value = this.readWithMessageConverters(webRequest, parameter, parameter.getParameterType());
		if(value == null){
			value = parameter.getParameterType().newInstance();
		}
		WebDataBinder binder = binderFactory.createBinder(webRequest, value, parameter.getParameterName());
		binder.validate();
		BindingResult br = binder.getBindingResult();
		if(br.getFieldErrorCount() > 0){
			throw new ApplicationException(Result.INVALID_PARAM, br.getFieldError().getField() + br.getFieldError().getDefaultMessage());
		}
		return value;
	}

	
	

}
