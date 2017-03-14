package com.yaoa.boot.app.dao;

import com.yaoa.boot.app.entity.User;
import org.springframework.stereotype.Repository;


/**
 * UserDao
 * @author ChenJianhui
 */
@Repository
public interface UserDao extends BaseDao<User, Long> {

    /**
     * 根据用户名和密码查询用户
     * @param usernmae
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String usernmae, String password);

}
