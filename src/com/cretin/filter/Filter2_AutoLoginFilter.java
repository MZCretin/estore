package com.cretin.filter;

import com.cretin.domain.User;
import com.cretin.factory.BasicFactory;
import com.cretin.service.UserService;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter( filterName = "Filter2_AutoLoginFilter", urlPatterns = "/*" )
public class Filter2_AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = ( HttpServletRequest ) req;
        HttpServletResponse response = ( HttpServletResponse ) resp;
        //没有登录的用户需要登录
        if ( request.getSession(false) == null || request.getSession().getAttribute("user") == null ) {
            //点了30天自动登录的用户
            Cookie[] cookies = request.getCookies();
            Cookie findC = null;
            if ( cookies != null ) {
                for ( Cookie c:
                       cookies) {
                    if("autologin".equals(c.getName())){
                        findC = c;
                        break;
                    }
                }
            }
            if(findC !=null){
                //账号和密码正确的用户
                String resStr = URLEncoder.encode(findC.getValue(),"utf-8");
                String username = resStr.split(":")[0];
                String password = resStr.split(":")[1];
                //登录
                UserService service = BasicFactory.getFactory().getInstance(UserService.class);
                User userByNameAndPsw = service.getUserByNameAndPsw(username, password);
                if(userByNameAndPsw!=null){
                    //登录成功
                    request.getSession().setAttribute("user", userByNameAndPsw);
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
