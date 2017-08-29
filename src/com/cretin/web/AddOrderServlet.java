package com.cretin.web;

import com.cretin.domain.Order;
import com.cretin.domain.OrderItem;
import com.cretin.domain.Product;
import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.OrderService;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "AddOrderServlet", urlPatterns = "/AddOrderServlet" )
public class AddOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = BasicFactory.getFactory().getInstance(OrderService.class);
        try {
            //将订单信息存入bean中
            Order order = new Order();
            BeanUtils.populate(order, request.getParameterMap());
            order.setId(UUID.randomUUID().toString());
            order.setPaystate(0);
            //计算金额
            Map<Product, Integer> map =
                    ( Map<Product, Integer> ) request.getSession().getAttribute("cartmap");
            double money = 0;
            List<OrderItem> list = new ArrayList<>();
            for ( Map.Entry<Product, Integer> entry :
                    map.entrySet() ) {
                money += entry.getKey().getPrice() * entry.getValue();

                OrderItem orderItem = new OrderItem();
                orderItem.setBuynum(entry.getValue());
                orderItem.setOrder_id(order.getId());
                orderItem.setProduct_id(entry.getKey().getId());
                list.add(orderItem);
            }
            order.setMoney(money);
            order.setItemList(list);

            User user = ( User ) request.getSession().getAttribute("user");
            order.setUser_id(user.getId());

            //添加订单
            orderService.addOrder(order);

            //清空购物车
            map.clear();

            //回到主页
            response.getWriter().write("订单生成成功，3s后返回主页，请去支付");
            response.setHeader("Rerresh", "3;url=index.jsp");
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
