package com.fxsd.framwork.dao;

import com.fxsd.framwork.entity.Role;
import org.springframework.stereotype.Repository;


/**
 * RoleDao
 * @author ChenJianhui
 */
@Repository
public interface RoleDao extends BaseDao<Role, Long> {

    /**
     * 根据角色名查询角色
     * @param name
     * @return
     */
    Role getByName(String name);

}
