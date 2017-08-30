package com.cretin.service;

import com.cretin.annotation.Tran;
import com.cretin.domain.User;

public interface UserService extends Service {
    /**
     * 注册用户的方法
     *
     * @param user 封装了用户的User Bean
     */
    @Tran
    void register(User user);

    /**
     * 激活用户
     *
     * @param activeCode 激活码
     */
    User active(String activeCode);

    /**
     * 根据用户名和密码查找用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回找到的用户
     */
    User getUserByNameAndPsw(String username, String password);
}
