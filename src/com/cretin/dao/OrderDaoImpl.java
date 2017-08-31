package com.cretin.dao;

import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;
import com.cretin.util.TransactionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?,null,?)";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
        queryRunner.update(sql, order.getId(), order.getMoney(), order.getReceiverinfo(), order.getPaystate(), order.getUser_id());
    }

    @Override
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "insert into orderitem values(?,?,?)";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
        queryRunner.update(sql, orderItem.getOrder_id(), orderItem.getProduct_id(), orderItem.getBuynum());
    }

    @Override
    public List<Order> findOrderListByUserId(int user_id) throws SQLException {
        String sql = "select * from orders where user_id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
        return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user_id);
    }

    @Override
    public List<OrderItem> findOrderItems(String order_id) throws SQLException {
        String sql = "select * from orderitem where order_id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
        return queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order_id);
    }

    @Override
    public void delOrderItem(String id) {
        String sql = "delete from orderitem where order_id = ?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, id);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delOrder(String id) {
        String sql = "delete from orders where id = ?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, id);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
