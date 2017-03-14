package com.yaoa.boot.app.service.impl;

import com.yaoa.boot.app.entity.Role;
import com.yaoa.boot.app.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;



/**
 * RoleServiceImpl
 * @author ChenJianhui
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {
	

	@Override
	public Role getRoleWithRights(Long roleId) {
		Role role = dao.findOne(roleId);
		role.getResources().iterator();
		return role;
	}


	


	
	

}
