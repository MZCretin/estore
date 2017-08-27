package com.cretin.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@WebFilter( filterName = "Filter1_EncodeFilter", urlPatterns = "/*" )
public class Filter1_EncodeFilter implements Filter {
    private FilterConfig config = null;
    private ServletContext context = null;
    private String encode = null;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //响应乱码解决
        resp.setCharacterEncoding(encode);
        resp.setContentType("text/html;charset=" + encode);
        //解决请求乱码的解决 利用装饰设计模式改变request对象和获取请求参数相关的方法解决参数乱码的问题
        chain.doFilter(new MyHttpServletRequest(( HttpServletRequest ) req), resp);
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.context = config.getServletContext();
        this.encode = context.getInitParameter("encode");
    }

    private class MyHttpServletRequest extends HttpServletRequestWrapper {
        private HttpServletRequest request = null;
        private boolean isFirstIn = true;

        public MyHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public String getParameter(String name) {
            return getParameterValues(name) == null ? null : getParameterValues(name)[0];
        }

        @Override
        public String[] getParameterValues(String name) {
            return getParameterMap().get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            try {
                if ( request.getMethod().equalsIgnoreCase("POST") ) {
                    request.setCharacterEncoding(encode);
                    return request.getParameterMap();
                } else if ( request.getMethod().equalsIgnoreCase("GET") ) {
                    Map<String, String[]> map = request.getParameterMap();
                    if ( isFirstIn ) {
                        for ( Map.Entry<String, String[]> entry :
                                map.entrySet() ) {
                            String[] vs = entry.getValue();
                            for ( int i = 0; i < vs.length; i++ ) {
                                vs[i] = new String(vs[i].getBytes("iso8859-1"), encode);
                            }
                        }
                        isFirstIn = false;
                    }
                    return map;
                } else {
                    return request.getParameterMap();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
