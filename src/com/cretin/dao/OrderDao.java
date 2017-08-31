package com.cretin.dao;

import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao {
    /**
     * 向数据库中插入订单数据
     *
     * @param order 订单
     */
    void addOrder(Order order) throws SQLException;

    /**
     * 向数据库中插入订单项数据
     *
     * @param orderItem 订单项
     */
    void addOrderItem(OrderItem orderItem) throws SQLException;

    /**
     * 根据用户id查找所有该用户的所有订单
     *
     * @param user_id 用户id
     * @return 所有订单组成的集合
     */
    List<Order> findOrderListByUserId(int user_id) throws SQLException;

    /**
     * 根据订单id查找所有的订单项
     *
     * @param order_id 订单id
     * @return 返回所有的订单项
     */
    List<OrderItem> findOrderItems(String order_id) throws SQLException;

    /**
     * 删除订单项
     * @param id 被删除的订单项的id
     */
    void delOrderItem(String id);

    /**
     * 删除订单
     * @param id 被删除的订单id
     */
    void delOrder(String id);
}
