package com.yaoa.boot.app.service.impl;


import com.yaoa.boot.app.entity.User;
import com.yaoa.boot.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
