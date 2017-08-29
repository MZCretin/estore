package com.cretin.service;

import com.cretin.dao.OrderDao;
import com.cretin.dao.ProductDao;
import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;
import com.cretin.factory.BasicFactory;
import com.cretin.util.DaoUtils;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = BasicFactory.getFactory().getInstance(OrderDao.class);
    ProductDao productDao = BasicFactory.getFactory().getInstance(ProductDao.class);

    @Override
    public void addOrder(Order order) {
        Connection connection = DaoUtils.getConn();
        try {
            connection.setAutoCommit(false);
            //生成订单
            orderDao.addOrder(connection, order);

            //生成订单项
            for ( OrderItem orderItem :
                    order.getItemList() ) {
                orderDao.addOrderItem(connection, orderItem);
                //扣除商品数量
                productDao.deleNum(connection, orderItem.getProduct_id(), orderItem.getBuynum());
            }
            DbUtils.commitAndCloseQuietly(connection);
        } catch ( Exception e ) {
            DbUtils.rollbackAndCloseQuietly(connection);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
