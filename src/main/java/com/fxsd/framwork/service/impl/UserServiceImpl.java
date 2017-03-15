package com.fxsd.framwork.service.impl;


import com.fxsd.framwork.service.UserService;
import com.fxsd.framwork.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl
 * @author ChenJianhui
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Override
	public User getUser(Long userId) {
		User user = this.findOne(userId);
		user.getRoles().iterator();
		return user;
	}




}
