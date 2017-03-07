package com.yaoa.boot.app.result;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 全局返回结果
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午7:32:05
 */
/**
 * 控制器返回结果类
 * @author ChenJianhui
 */
public class Result implements Serializable{
	
	private static final long serialVersionUID = -5759964467525426508L;
	
	/**
	 * 状态码
	 */
	private int status;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	/**
	 * 返回数据
	 */
	private Map<String, Object> data;
	
	/**
	 * 请求成功结果
	 */
	public static Result SUCCESS = new Result(0, "request success");
	
	public Result(){
		
	}
	
	public Result(int status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	/**
	 * 输出返回数据
	 * @param key
	 * @param data
	 */
	public void putData(String key, Object data){
		if(this.data == null){
			this.data = new HashMap<String, Object>();
		}
		this.data.put(key, data);
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

}
