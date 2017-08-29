package com.cretin.dao;

import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDao {
    /**
     * 向数据库中插入订单数据
     *
     * @param connection 数据库连接
     * @param order 订单
     */
    void addOrder(Connection connection, Order order) throws SQLException;

    /**
     * 向数据库中插入订单项数据
     *
     * @param connection
     * @param orderItem 订单项
     */
    void addOrderItem(Connection connection, OrderItem orderItem) throws SQLException;
}
