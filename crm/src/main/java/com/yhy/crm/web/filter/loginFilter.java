package com.yhy.crm.web.filter;

import com.yhy.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//后期可以用SpringMVC来替换
        System.out.println("进入到验证没有用登录过的过滤器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //拿路径
        String path = request.getServletPath();

        //不应该被拦截的资源，自动放行请求
       if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            chain.doFilter(req, resp);
            //其他资源必须要验证有没有登录过
        } else {


            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            //如果user不为null，说明登录过
           if (user != null) {
                //请求放行
                chain.doFilter(req, resp);
            } else {
                //没有登录过，重定向到登录页
                //request.getContextPath()可以取到/项目名，把这个写活，以后好再用
                System.out.println("没有登录过，重定向");
                response.sendRedirect(request.getContextPath() + "/login.jsp");

            }
 }


    }
}

