package com.cretin.dao;

import com.cretin.domain.User;

public interface UserDao extends Dao{
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 查到到的用户 如果找不到 返回null
     */
    User findUserByName(String username);

    /**
     * 添加用户的方法
     * @param user 被添加的用户
     *
     */
    void addUser(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    void delete(int id);

    /**
     * 根据邀请码获取用户
     * @param activeCode
     * @return
     */
    User findUserByActivecode(String activeCode);

    /**
     * 修改该id用户的状态为state
     * @param id 用户id
     * @param state 被修改成的状态
     */
    void updateState(int id, int state);

    /**
     * 根据条用户名和密码查找用户
     * @param username 账号
     * @param password 密码
     * @return 找到的用户  没找到则返回null
     */
    User getUserByNameAndPsw(String username, String password);


    /**
     *  根据id查找用户
     * @param user_id 用户id
     * @return 返回用户
     */
    User findUserById(int user_id);
}
