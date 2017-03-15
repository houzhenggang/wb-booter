package com.fxsd.framwork.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fxsd.framwork.util.ConstUtils;
import com.fxsd.framwork.util.ValidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 防表单重复提交拦截器
 * @author ChenJianhui
 */
public class ResubmitInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(ResubmitInterceptor.class);
	
	/**
	 * 防表单重复提交
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String requestId = request.getParameter("requestId");
		if(ValidUtils.isValid(requestId)){
			String sessionRequestId = (String) session.getAttribute(ConstUtils.SESSION_API_REQUESTID);
			session.setAttribute(ConstUtils.SESSION_API_REQUESTID, requestId);
			if(ValidUtils.isValid(sessionRequestId)){
				log.info(requestId);
				return ! requestId.equals(sessionRequestId);
			}
		}
		return true;
	}

}
