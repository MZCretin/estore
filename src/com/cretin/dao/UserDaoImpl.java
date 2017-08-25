package com.cretin.dao;

import com.cretin.domain.User;
import com.cretin.util.DaoUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByName(String username) {
        String sql = "select * from users where username = ?";
        try {
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            return runner.query(sql, new BeanHandler<User>(User.class), username);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into users values(null,?,?,?,?,?,?,?,null)";
        try {
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            runner.update(sql, user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail()
                    , user.getRole(), user.getState(), user.getActivecode());
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
