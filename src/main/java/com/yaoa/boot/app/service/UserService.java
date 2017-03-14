package com.yaoa.boot.app.service;


import com.yaoa.boot.app.entity.User;

public interface UserService extends BaseService<User, Long>{

	/**
	 * 根据ID获取用户信息
	 * @param userId
	 */
	User getUser(Long userId);



}
