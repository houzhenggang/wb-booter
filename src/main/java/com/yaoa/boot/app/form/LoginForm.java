package com.yaoa.boot.app.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description 登录表单
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午7:40:18
 */
public class LoginForm implements Form{
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
