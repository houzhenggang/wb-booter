package com.fxsd.framwork.service.impl;

import com.fxsd.framwork.dao.ModuleDao;
import com.fxsd.framwork.dao.ResourceDao;
import com.fxsd.framwork.dao.UserDao;
import com.fxsd.framwork.entity.Module;
import com.fxsd.framwork.entity.Resource;
import com.fxsd.framwork.entity.Role;
import com.fxsd.framwork.service.ResourceService;
import com.fxsd.framwork.entity.User;
import com.fxsd.framwork.util.ValidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ResourceServiceImpl
 * @author ChenJianhui
 */
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements ResourceService {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);  
	
	@Autowired
	private ModuleDao moduleDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private ResourceDao resourceDao;

	@Override
	@Transactional
	public Resource addResource(Resource r){
		Resource resource = resourceDao.getByUrl(r.getUrl());
		if(resource != null){
			return resource;
		}
		int pos = 0;
		long code = 1L ;
		Integer topPos = resourceDao.getMaxResourcePos();
		Long topCode = resourceDao.getMaxResourceCode(topPos);
		if(topPos != null){
			//权限码是否到达最大值
			if(topCode >= (1L << 60)){
				pos = topPos + 1 ;
				code = 1L ;
			}
			else{
				pos = topPos ;
				code = topCode << 1 ;
			}
		}
		r.setPosition(pos);
		r.setCode(code);
		this.save(r);
		log.info("Add Right " + r.getUrl());
		return r;
	}

	@Override
	public long[] getAllowRightByUser(User user) {
		user = userDao.findOne(user.getId());
		List<Role> roles = user.getRoles();
		int maxRightPos = getMaxRightPos();
		if(ValidUtils.isValid(roles)){
			long[] allowRights =new long[maxRightPos + 1];
			int pos = 0;
			long code = 0 ;
			for(Role role : roles){
				for(Resource r : role.getResources()){
					pos = r.getPosition();
					code = r.getCode();
					allowRights[pos] = allowRights[pos] | code ;
				}
			}
			return allowRights;
		}
		return new long[0];
	}

	@Override
	public int getMaxRightPos() {
		return 0;
	}

	@Override
	@Transactional
	public void saveMoudle(Module module) {
		moduleDao.save(module);
	}

	@Override
	@Transactional
	public void deleteModule(Long moduleId) {
		moduleDao.delete(moduleId);
	}

	@Override
	public List<Module> findAllModule(User user) {
		return null;
	}

	@Override
	public Resource getByUri(String uri) {
		return resourceDao.getByUrl(uri);
	}

	@Override
	@Transactional
	public void updateResource(Resource r) {
		Resource src = this.findOne(r.getId());
		src.setName(r.getName());
		src.setRemark(r.getRemark());
		src.setModuleId(r.getModuleId());
		src.setRightLevel(r.getRightLevel());
		src.setInterval(r.getInterval());
		src.setLimited(r.isLimited());
		src.setFrequency(r.getFrequency());
		this.save(r);
	}


}
