package com.cretin.web;

import com.cretin.domain.Product;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "ClearCartServlet", urlPatterns = "/ClearCartServlet" )
public class ClearCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Product,Integer> map = ( Map<Product, Integer> ) request.getSession().getAttribute("cartmap");
        map.clear();
        response.sendRedirect("/cart.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
