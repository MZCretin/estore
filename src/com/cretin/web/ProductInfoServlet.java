package com.cretin.web;

import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;
import com.cretin.service.ProductService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "ProductInfoServlet", urlPatterns = "/ProductInfoServlet" )
public class ProductInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id 查找商品
        String id = request.getParameter("id");
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        Product product = service.getProductById(id);
        request.setAttribute("product",product);
        request.getRequestDispatcher("/productInfo.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
