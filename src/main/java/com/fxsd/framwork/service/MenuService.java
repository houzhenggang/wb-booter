package com.fxsd.framwork.service;


import com.fxsd.framwork.entity.Menu;
import com.fxsd.framwork.entity.User;

import java.util.List;

/**
 * MenuService 接口
 * @author ChenJianhui
 *
 */
public interface MenuService extends BaseService<Menu, Long>{

	/**
	 * 加载对应用户的菜单
	 * @param user
	 * @return List<Menu>
	 */
	List<Menu> loadMenus(User user);

}
