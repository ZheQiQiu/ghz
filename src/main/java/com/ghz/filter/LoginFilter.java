package com.ghz.filter;

import com.ghz.enumeration.StatusCodeEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.ghz.utils.DistUtils.putCode2JsonStr;

@WebFilter(filterName = "LoginFilter",urlPatterns = {"/*"})
public class LoginFilter implements Filter {

    private static final List<String> EXCEPT_STR;
    String NO_LOGIN = "您还未登录";

    static {
        EXCEPT_STR = new ArrayList<>();

        //登录请求排除
        EXCEPT_STR.add("login");
        EXCEPT_STR.add("/favicon.ico");

        EXCEPT_STR.add("/js/");
        EXCEPT_STR.add("/css/");
        EXCEPT_STR.add("layui");
        EXCEPT_STR.add("layer");

        //swagger排除项
        EXCEPT_STR.add("swagger");
        EXCEPT_STR.add("springfox.");
        EXCEPT_STR.add("favicon-");
        EXCEPT_STR.add("/v2/api-docs");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        if(!containsExcept(requestURI)){
            if(session != null && session.getAttribute("user") != null && session.getAttribute("user") != ""){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    response.getWriter().write(this.NO_LOGIN);
                }else{
                    response.sendRedirect(request.getContextPath()+"/login");
                }
                return;
            }

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    private boolean containsExcept(String url){
        for(String s:EXCEPT_STR){
            if(url.contains(s)) return true;
        }
        return false;
    }
}
