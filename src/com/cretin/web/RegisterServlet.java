package com.cretin.web;

import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.UserService;
import com.cretin.util.MD5Utils;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "RegisterServlet", urlPatterns = "/RegisterServlet" )
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = BasicFactory.getFactory().getService(UserService.class);
        try {
            //检验验证码
            String valiStr = request.getParameter("valistr");
            String valiStr2 = ( String ) request.getSession().getAttribute("valistr");
            if ( valiStr == null || valiStr2 == null || !valiStr.equals(valiStr2) ) {
                request.setAttribute("msg", "验证码不正确");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            //封装数据校验
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());

            user.setPassword(MD5Utils.md5(request.getParameter("password")));
            //注册用户
            userService.register(user);

            //回到主页
            response.getWriter().write("注册成功，请到邮箱中进行激活(3s后自动回到主页)");
            response.setHeader("Refresh", "3;url=/index.jsp");
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
