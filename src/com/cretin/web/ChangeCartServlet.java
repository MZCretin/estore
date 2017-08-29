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

@WebServlet( name = "ChangeCartServlet", urlPatterns = "/ChangeCartServlet" )
public class ChangeCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        //获取id
        String id = request.getParameter("id");
        //获取产品
        Product product = service.getProductById(id);
        if ( product == null ) {
            throw new RuntimeException("该商品不存在");
        }
        Map<Product, Integer> map = ( Map<Product, Integer> ) request.getSession().getAttribute("cartmap");
        map.put(product, Integer.parseInt(request.getParameter("buynum")));

        response.sendRedirect("/cart.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
