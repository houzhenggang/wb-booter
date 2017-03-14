package com.yaoa.boot.app.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.txw2.output.ResultFactory;
import com.yaoa.boot.app.entity.Resource;
import com.yaoa.boot.app.result.Result;
import com.yaoa.boot.app.service.ResourceService;
import com.yaoa.boot.app.util.ConstUtils;
import com.yaoa.boot.app.util.ServletUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 恶意IP拦截器
 * @author ChenJianhui
 */
public class ApiLimitInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(ApiLimitInterceptor.class);
	
	@Autowired
	private RedissonClient redisson;

	/**
	 * 恶意IP拦截
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI().split("\\.")[0];
		String clientIp = ServletUtils.getClientIp(request);
		log.info(clientIp);
		Map<String, Resource> allRightMap = (Map<String, Resource>)request.getServletContext().getAttribute(ConstUtils.APP_ALL_RIGHT_MAP);
		Resource r = allRightMap.get(uri);
		response.setContentType("application/json;charset=UTF-8");
		if(r == null){
			response.getWriter().write(new ObjectMapper().writeValueAsString(Result.API_NOT_EXIST));
			return false;
		} 
		if(! r.isLimited()){
			return true;
		}
		String limitIpKey = "limited-ip_" + clientIp;
		RBucket<String> limitBucket = redisson.getBucket(limitIpKey);
		if(limitBucket.isExists()){
			response.getWriter().write(new ObjectMapper().writeValueAsString(Result.illagalRequest()));
			return false;
		}
		String accessKey = ServletUtils.getClientIp(request) + uri;
		RBucket<Integer> accessBucket = redisson.getBucket(accessKey);
		if(accessBucket.trySet(1, r.getInterval(), TimeUnit.SECONDS)){
			return true;
		}
		Integer count = accessBucket.get();
		if(count > r.getFrequency()){
			limitBucket.set(clientIp, 7, TimeUnit.DAYS);
			response.getWriter().write(new ObjectMapper().writeValueAsString(Result.illagalRequest()));
			return false;
		}
		return true;
	}

}
