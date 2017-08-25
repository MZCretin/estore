package com.cretin.service;

import com.cretin.domain.User;

public interface UserService {
    /**
     * 注册用户的方法
     *
     * @param user 封装了用户的User Bean
     */
    void register(User user);
}
