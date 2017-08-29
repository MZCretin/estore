package com.cretin.web;

import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;
import com.cretin.service.ProductService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "ProductListServlet", urlPatterns = "/ProductListServlet" )
public class ProductListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        //查找所有的产品
        List<Product> list = service.getProductLists();

        //带着数据转发给jsp展示数据
        request.setAttribute("list", list);
        request.getRequestDispatcher("/productList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
