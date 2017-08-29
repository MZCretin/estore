package com.cretin.service;

import com.cretin.domain.Order;

public interface OrderService extends Service{
    /**
     * 添加订单
     *
     * @param order
     */
    void addOrder(Order order);
}
