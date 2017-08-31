package com.cretin.web;

import com.cretin.factory.BasicFactory;
import com.cretin.service.OrderService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "DeleteOrderServlet", urlPatterns = "/DeleteOrderServlet" )
public class DeleteOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
        String id = request.getParameter("id");
        OrderService service = BasicFactory.getFactory().getService(OrderService.class);
        service.deleteOrderById(id);
        response.getWriter().write("订单删除成功,3s后返回");
        response.setHeader("refresh", "3;url=/OrderListServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
