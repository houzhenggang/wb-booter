package com.yaoa.boot.app.config;

import com.yaoa.boot.app.resolver.FormArgumentResolver;
import com.yaoa.boot.app.result.ResultJsonSerializer;
import com.yaoa.boot.app.result.ReturnJsonHandler;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Description web应用配置
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午6:29:49
 */
@EnableAsync
@Configuration
@EnableWebMvc
public class ServletConfigure extends WebMvcConfigurerAdapter {
	
	private List<HttpMessageConverter<?>> converters;
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer(){
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
                container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/error/500"));
            }
        };
    }
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		HandlerMethodArgumentResolver resolver = new FormArgumentResolver(converters);
//		argumentResolvers.add(resolver);
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.setObjectMapper(new ResultJsonSerializer());
		this.converters = converters;
	}
	
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		HandlerMethodReturnValueHandler handler = new ReturnJsonHandler();
		returnValueHandlers.add(handler);
	}
}
