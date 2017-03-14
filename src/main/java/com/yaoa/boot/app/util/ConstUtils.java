package com.yaoa.boot.app.util;

/**
 * 常量工具类
 * @author ChenJianhui
 */
public class ConstUtils {
	
	/**
	 * session中存放的请求标识的key值
	 */
	public static final String SESSION_API_REQUESTID = "sessionApiRequestId";
	
	/**
	 * session中存放的User对象的key值
	 */
	public static final String SESSION_USER = "sessionUser";
	


	/**
	 * 防刷请求时间
	 */
	public static final String SESSION_ANTIBRUSH_TIMESTAMP = "sessionAntibrushTimestamp";
	
	/**
	 * session中存放的用户权限的key值
	 */
	public static final String SESSION_USER_ALLOW_RIGHT = "allowRight";
	
	/**
	 * 系统包含的所有的权限
	 */
	public static final String APP_ALL_RIGHT_MAP = "allRightsMap";

	/**
	 * 系统没有权限控制的请求URL
	 */
	public static final String APP_ALLOW_URLS = "allowUrls";

	/**
	 * 短信验证码
	 */
	public static final String SMS_CAPTCHA = "smsCaptcha";


}
