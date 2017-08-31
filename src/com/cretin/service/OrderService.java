package com.cretin.service;

import com.cretin.annotation.Tran;
import com.cretin.domain.Order;
import com.cretin.domain.OrderListForm;

import java.util.List;

public interface OrderService extends Service {
    /**
     * 添加订单
     *
     * @param order
     */
    @Tran
    void addOrder(Order order);

    /**
     * 获取对应用户id的所有订单信息
     *
     * @param user_id 用户id
     * @return 所有订单信息组成的集合
     */
    List<OrderListForm> getOrderListByUserId(int user_id);

    /**
     * 根据订单id删除订单
     *
     * @param id 订单id
     */
    @Tran
    void deleteOrderById(String id);

}
