package com.yaoa.boot.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤拦截器
 * @author ChenJianhui
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);  
	
	/**
	 * 权限过滤
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		response.setContentType("application/json;charset=UTF-8");
//		String uri = request.getServletPath().split("\\.")[0];
//		log.info("Request Url : " + uri);
//		@SuppressWarnings("unchecked")
//		Map<String, Resource> allRightMap = (Map<String, Resource>) request.getServletContext().getAttribute(ConstUtils.APP_ALL_RIGHT_MAP);
//		Resource r = allRightMap.get(uri);
//		if(r == null){
//			response.getWriter().write(new ObjectMapper().writeValueAsString(ResultFactory.getResult(ResultFactory.NOT_FOUND)));
//			return false;
//		}else if(r.getRightLevel() != RightLevel.PUBLIC.vlue()){
//			User user = (User) request.getSession().getAttribute(ConstUtils.SESSION_USER);
//			if(user == null){
//				response.getWriter().write(new ObjectMapper().writeValueAsString(ResultFactory.getResult(ResultFactory.NOT_LOGIN)));
//				return false;
//			}
//			if(r.getRightLevel() != RightLevel.LOGIN.vlue() && !user.isAdmin()){
//				long[] allowRight = (long[]) request.getSession().getAttribute(ConstUtils.SESSION_USER_ALLOW_RIGHT);
//				if((allowRight[r.getRightPos()] & r.getRightCode()) == 0){
//					response.getWriter().write(new ObjectMapper().writeValueAsString(ResultFactory.getResult(ResultFactory.NOT_RIGHT)));
//					return false;
//				}
//			}
//		}
		return true;
	}

}