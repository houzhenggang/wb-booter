package com.yaoa.boot.app.service;

import com.yaoa.boot.app.entity.Module;
import com.yaoa.boot.app.entity.Resource;
import com.yaoa.boot.app.entity.User;

import java.util.List;


/**
 * Right Service interface
 * @author ChenJianhui
 */
public interface ResourceService extends BaseService<Resource, Long>{

	/**
	 * 获取用户所有的权限
	 * @param user
	 * @return
	 */
	long[] getAllowRightByUser(User user);
	

	/**
	 * 查询最大权限位
	 */
	int getMaxRightPos();


	/**
	 * 保存权限模块
	 * @param module
	 */
	void saveMoudle(Module module);

	/**
	 * 删除模块
	 * @param moduleId
	 */
	void deleteModule(Long moduleId);

	/**
	 * 查询所有的模块
	 * @return
	 */
	List<Module> findAllModule(User user);

	/**
	 * 添加权限
	 * @param r
	 */
	 Resource addResource(Resource r);
	 
	 /**
	  * 根据资源路径查询资源
	  * @param uri
	  * @return
	  */
	 Resource getByUri(String uri);

	/**
	 * 更新权限对象
	 * @param resource
	 */
	void updateResource(Resource resource);

}
