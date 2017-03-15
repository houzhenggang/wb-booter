package com.fxsd.framwork.service;

import java.util.List;

import com.fxsd.framwork.entity.Role;


/**
 * Role Service interface
 * @author ChenJianhui
 */
public interface RoleService extends BaseService<Role, Long>{

	/**
	 * 查询角色及其对应的资源
	 * @param roleId
	 * @return
	 */
	Role getRoleWithRights(Long roleId);

}
