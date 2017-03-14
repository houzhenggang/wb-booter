package com.yaoa.boot.app.service;


import com.yaoa.boot.app.entity.Menu;
import com.yaoa.boot.app.entity.User;

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
