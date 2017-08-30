package com.cretin.web;

import com.cretin.domain.OrderListForm;
import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.OrderService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "OrderListServlet", urlPatterns = "/OrderListServlet" )
public class OrderListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户id
        User user = ( User ) request.getSession().getAttribute("user");
        int user_id = user.getId();
        //获取订单列表
        OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);
        List<OrderListForm> listFormList = orderService.getOrderListByUserId(user_id);
        //带到页面显示
        request.setAttribute("list", listFormList);
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
