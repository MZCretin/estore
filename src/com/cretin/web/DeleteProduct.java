package com.cretin.web;

import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;
import com.cretin.service.ProductService;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "DeleteProduct", urlPatterns = "/DeleteProduct" )
public class DeleteProduct extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        //获取id 获取商品
        String id = request.getParameter("id");
        //删除商品
        Product product = service.getProductById(id);
        Map<Product, Integer> map = ( Map ) request.getSession().getAttribute("cartmap");
        map.remove(product);
        //重定向到购物车页面
        response.sendRedirect("/cart.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
