package com.cretin.dao;

import com.cretin.domain.User;

public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 查到到的用户 如果找不到 返回null
     */
    User findUserByName(String username);

    /**
     * 添加用户的方法
     * @param user 被添加的用户
     */
    void addUser(User user);
}
