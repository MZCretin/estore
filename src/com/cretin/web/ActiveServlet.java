package com.cretin.web;

import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "ActiveServlet" , urlPatterns = "/ActiveServlet" )
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFactory().getInstance(UserService.class);
        //获取激活码
        String activeCode = request.getParameter("activecode");
        //激活
        User user = service.active(activeCode);

        //登录用户
        request.getSession().setAttribute("user",user);

        //激活成功 3s后回到主页
        response.getWriter().write("激活成功，3s回到主页");
        response.setHeader("Refresh","3;url=/index.jsp");
    }
}
