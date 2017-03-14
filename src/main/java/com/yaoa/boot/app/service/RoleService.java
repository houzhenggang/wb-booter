package com.yaoa.boot.app.service;

import java.util.List;

import com.yaoa.boot.app.entity.Role;
import com.yaoa.boot.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
