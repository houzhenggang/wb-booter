package com.fxsd.framwork.jdbc;


import com.fxsd.framwork.util.ValidUtils;

import javax.persistence.ParameterMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储过程参数 
 * @author cjh
 */
public class SprocParamter {

	/**
	 * 参数位置
	 */
	private int position;
	
	/**
	 * 参数名称
	 */
	private String paramName;
	
	/**
	 * 参数类型
	 */
    private Class<?> paramType;
    
    /**
     * 参数值
     */
    private Object paramValue;
    
    /**
     * 参数模式（in/out）
     */
    private ParameterMode mode;
    
    
	public SprocParamter(int position, String paramName, Class<?> paramType, Object paramValue, ParameterMode mode) {
		this.position = position;
		this.paramName = paramName;
		this.paramType = paramType;
		this.paramValue = paramValue;
		this.mode = mode;
	}
	
	public SprocParamter(int position, Class<?> paramType, Object paramValue) {
		this.position = position;
		this.paramType = paramType;
		this.paramValue = paramValue;
		this.mode = ParameterMode.IN;
	}
	
	/**
	 * 封装存储过程参数(参数只能传基本类型)
	 * @param params
	 * @return
	 */
	public static List<SprocParamter> convert(Object... params){
		List<SprocParamter> list = null;
		if(ValidUtils.isValid(params)){
			list = new ArrayList<>(params.length);
			SprocParamter sp = null;
			for (int i = 0; i < params.length; i++) {
				sp = new SprocParamter(i, params[i].getClass(), params[i]);
				list.add(sp);
			}
		}
		return list;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public ParameterMode getMode() {
		return mode;
	}
	
	public void setMode(ParameterMode mode) {
		this.mode = mode;
	}
	
	public String getParamName() {
		return paramName;
	}
	
	public Class<?> getParamType() {
		return paramType;
	}
	
	public Object getParamValue() {
		return paramValue;
	}
    
	
}
