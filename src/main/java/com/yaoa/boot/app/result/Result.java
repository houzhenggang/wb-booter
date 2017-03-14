package com.yaoa.boot.app.result;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 全局返回结果
 * @author cjh
 * @version 1.0
 * @date：2017年3月14日 下午7:32:05
 */
/**
 * 控制器返回结果类
 * @author ChenJianhui
 */
public class Result implements Serializable{

	private static final long serialVersionUID = -5759964467525426508L;

	//**********************返回错误对应的KEY***********************

	private static final String ERROR_CODE_KEY = "errcode";

	private static final String ERROR_MSG_KEY = "errmsg";


	//**********************系统全局错误码***********************

	/**
	 * 请求成功状态码
	 */
	public static final int SUCCESS = 0;

	/**
	 *  未授权（未登录）
	 */
	public static final int NOT_OAUTH = 40000;

	/**
	 * 无权限访问
	 */
	public static final int PERMISSION_DENIED = 40001;

	/**
	 * 非法请求
	 */
	public static final int ILLEGAL_REQUEST = 40002;

	/**
	 * 无效的请求参数
	 */
	public static final int INVALID_PARAM = 40003;

	/**
	 * 请求接口不存在
	 */
	public static final int API_NOT_EXIST = 40004;

	/**
	 * 系统异常
	 */
	public static final int SYSTEM_ERROR = 50000;


	public static Result success(){
		return new Result(SUCCESS, "request success");
	}

	public static Result unauthorized(){
		return new Result(NOT_OAUTH, "unauthorized");
	}

	public static Result permissionDenied(){
		return new Result(PERMISSION_DENIED, "permission denied");
	}

	public static Result illagalRequest(){
		return new Result(ILLEGAL_REQUEST, "illegal request");
	}

	public static Result invalidParam(){
		return new Result(INVALID_PARAM, "invalid param");
	}

	public static Result notFound(){
		return new Result(API_NOT_EXIST, "api not exist");
	}

	public static Result systemError(){
		return new Result(SYSTEM_ERROR, "system error");
	}

	/**
	 * 返回数据
	 */
	private Map<String, Object> data = new LinkedMap();
	
	public Result(){

	}

	public Result(int code, String msg){
		data.put(ERROR_CODE_KEY, code);
		data.put(ERROR_MSG_KEY, msg);
	}
	
	/**
	 * 设置返回数据
	 * @param key
	 * @param data
	 */
	public void putData(String key, Object data){
		this.data.put(key, data);
	}
	
	public int getErrcode() {
		return (int) data.get(ERROR_CODE_KEY);
	}

	public void setErrcode(int code) {
		data.put(ERROR_CODE_KEY, code);
	}

	public String getErrmsg() {
		return (String) data.get(ERROR_MSG_KEY);
	}

	public void setMsg(String msg) {
		data.put(ERROR_MSG_KEY, msg);
	}


}
