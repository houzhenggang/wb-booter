package com.fxsd.framwork.service.impl;

import com.fxsd.framwork.dao.UserDao;
import com.fxsd.framwork.entity.Menu;
import com.fxsd.framwork.entity.User;
import com.fxsd.framwork.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MenuService 实现类
 * 
 * @author ChenJianhui
 */
@Service
@Transactional(readOnly = true)
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<Menu> loadMenus(User user) {
		return null;
	}

}
