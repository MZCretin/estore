package com.cretin.service;

import com.cretin.dao.OrderDao;
import com.cretin.dao.ProductDao;
import com.cretin.dao.UserDao;
import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;
import com.cretin.domain.OrderListForm;
import com.cretin.domain.Product;
import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = BasicFactory.getFactory().getDao(OrderDao.class);
    ProductDao productDao = BasicFactory.getFactory().getDao(ProductDao.class);
    UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);

    @Override
    public void addOrder(Order order) {
        try {
            //生成订单
            orderDao.addOrder(order);

            //生成订单项
            for ( OrderItem orderItem :
                    order.getItemList() ) {
                orderDao.addOrderItem(orderItem);
                //扣除商品数量
                productDao.deleNum(orderItem.getProduct_id(), orderItem.getBuynum());
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderListForm> getOrderListByUserId(int user_id) {
        List<OrderListForm> orderListFormList = new ArrayList<>();
        try {
            //先获取用户的所有订单
            List<Order> orderList = orderDao.findOrderListByUserId(user_id);
            User user = userDao.findUserById(user_id);
            String username = user.getUsername();
            for ( Order o :
                    orderList ) {
                System.out.println("订单的金额" + o.getMoney());
                OrderListForm orderListForm = new OrderListForm();
                BeanUtils.copyProperties(orderListForm, o);
                //设置用户名
                orderListForm.setUsername(username);
                //获取该订单的所有产品
                //----查询当前订单所有订单项
                List<OrderItem> itemList = orderDao.findOrderItems(o.getId());
                //----遍历所有订单项,获取商品id,查找对应的商品,存入map
                Map<Product, Integer> prodMap = new HashMap<Product, Integer>();
                for ( OrderItem oi :
                        itemList ) {
                    String prodId = oi.getProduct_id();
                    Product product = productDao.getProductById(prodId);
                    prodMap.put(product, oi.getBuynum());
                }
                //设置金额 先不写 看下效果
                orderListForm.setProdMap(prodMap);
                orderListFormList.add(orderListForm);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderListFormList;
    }
}
