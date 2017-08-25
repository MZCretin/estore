package com.cretin.service;

import com.cretin.dao.UserDao;
import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao userDao = BasicFactory.getFactory().getInstance(UserDao.class);

    @Override
    public void register(User user) {
        //校验用户名是否已经存在
        if ( userDao.findUserByName(user.getUsername()) != null ) {
            //用户存在
            throw new RuntimeException("用户名已经存在");
        }

        //注册
        user.setRole("user");
        user.setState(0);
        user.setActivecode(UUID.randomUUID().toString());
        userDao.addUser(user);

        //发送激活邮件
    }
}
