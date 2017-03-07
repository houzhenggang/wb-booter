package com.yaoa.boot.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yaoa.boot.app.exception.ApplicationException;

/**
 * @Description 全局异常处理类
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午8:23:51
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalExceptionController extends BasicErrorController {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);
	
	private ErrorAttributes errorAttributes;
	
	@Autowired
	public GlobalExceptionController(ErrorAttributes errorAttributes) {
		super(errorAttributes, new ErrorProperties());
		this.errorAttributes = errorAttributes;
	}

	@ResponseBody
	@RequestMapping("/500")
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		Throwable throwable = this.errorAttributes.getError(requestAttributes);
		Map<String, Object> body = new HashMap<>();
		body.put("msg", throwable.getMessage());
		body.put("status", 5000);
		if(throwable instanceof ApplicationException){
			ApplicationException ex = (ApplicationException) throwable;
			body.put("status", ex.getCode());
		}else{
			logger.error(throwable.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
	}
}