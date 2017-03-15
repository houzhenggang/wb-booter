package com.fxsd.framwork.controller;

import com.fxsd.framwork.form.LoginForm;
import com.fxsd.framwork.result.Json;
import com.fxsd.framwork.entity.User;
import com.fxsd.framwork.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户控制器
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午7:30:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/login")
	@Json(type = User.class, include = "id,username")
	public Result login(LoginForm form){
		logger.debug(form.getUsername());
		User user = new User();
		user.setId(12L);
		user.setPassword("2333223");
		user.setUsername("sdaf112");
		Result result = Result.success();
		result.putData("user", user);
		return result;
	}



}
