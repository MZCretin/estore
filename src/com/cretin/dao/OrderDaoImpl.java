package com.cretin.dao;

import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Connection connection, Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?null,?)";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection, sql, order.getId(), order.getMoney(), order.getReceiverinfo(), order.getPaystate(), order.getId());
    }

    @Override
    public void addOrderItem(Connection connection, OrderItem orderItem) throws SQLException {
        String sql = "insert into orderitem values(?,?,?)";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection, sql, orderItem.getOrder_id(), orderItem.getProduct_id(), orderItem.getBuynum());
    }
}
