package com.cretin.web;

import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;
import com.cretin.service.ProductService;
import com.cretin.util.DaoUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet( name = "AddCartServlet", urlPatterns = "/AddCartServlet" )
public class AddCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        //获取id
        String id = request.getParameter("id");
        //查询出商品
        Product product = service.getProductById(id);
        if ( product == null ) {
            throw new RuntimeException("没有该商品");
        }

        //如果之前没有该商品 则添加 有则改数据
        LinkedHashMap<Product, Integer> map = ( LinkedHashMap<Product, Integer> )
                request.getSession().getAttribute("cartmap");
        map.put(product, map.containsKey(product) ? map.get(product) + 1 : 1);


        DataSource source = DaoUtils.getSource();
        try {
            Connection connection = source.getConnection();
            System.out.println("hahaha");
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        //重定向到购物车页面
        response.sendRedirect("/cart.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
