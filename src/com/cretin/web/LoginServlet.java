package com.cretin.web;

import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.UserService;
import com.cretin.util.MD5Utils;
import com.cretin.util.TextUtils;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "LoginServlet", urlPatterns = "/LoginServlet" )
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFactory().getInstance(UserService.class);
        try {
            //获取账号和密码参数
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if( TextUtils.isEmpty(username) ){
                request.setAttribute("msg","用户名不能为空");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            if( TextUtils.isEmpty(password) ){
                request.setAttribute("msg","密码不能为空");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            //查找用户
            User user = service.getUserByNameAndPsw(username, MD5Utils.md5(password));
            if(user == null){
                //用户名账号不匹配
                request.setAttribute("msg","用户名和密码不匹配");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            //检查是否激活
            if(user.getState()!=1){
                //用户名账号不匹配
                request.setAttribute("msg","账户未激活，请去邮箱激活账号后再来操作");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            //全部满足  登录成功
            request.getSession().setAttribute("user",user);
            //记住用户名
            if("true".equals(request.getParameter("remename"))){
                Cookie cookie = new Cookie("remename", URLEncoder.encode(user.getUsername(),"utf-8"));
                cookie.setPath("/");
                cookie.setMaxAge(30*24*3600);
                response.addCookie(cookie);
            }else{
                //取消记住用户名
                Cookie cookie = new Cookie("remename", "");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            //30天自动登录
            if("true".equals(request.getParameter("autologin"))){
                Cookie cookie = new Cookie("autologin", URLEncoder.encode(user.getUsername()+":"+user.getPassword(),"utf-8"));
                cookie.setPath("/");
                cookie.setMaxAge(30*24*3600);
                response.addCookie(cookie);
            }else{
                //取消记住用户名
                Cookie cookie = new Cookie("autologin", "");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            response.sendRedirect("/index.jsp");
        }catch ( Exception e ){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
