package com.yaoa.boot.app.service.impl;

import com.yaoa.boot.app.dao.UserDao;
import com.yaoa.boot.app.entity.Menu;
import com.yaoa.boot.app.entity.User;
import com.yaoa.boot.app.service.MenuService;
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
