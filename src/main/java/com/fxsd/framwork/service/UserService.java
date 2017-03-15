package com.fxsd.framwork.service;


import com.fxsd.framwork.entity.User;

public interface UserService extends BaseService<User, Long>{

	/**
	 * 根据ID获取用户信息
	 * @param userId
	 */
	User getUser(Long userId);



}
