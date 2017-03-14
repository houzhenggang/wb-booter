package com.yaoa.boot.app.controller;

import com.yaoa.boot.app.entity.User;
import com.yaoa.boot.app.form.LoginForm;
import com.yaoa.boot.app.result.Json;
import com.yaoa.boot.app.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户控制器
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午7:30:53
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Json(type = User.class, include = "id,username")
	@RequestMapping("/login")
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
