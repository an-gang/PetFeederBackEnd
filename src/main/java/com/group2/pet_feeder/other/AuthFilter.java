package com.group2.pet_feeder.other;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/tttttttttttttt/*"})
public class AuthFilter implements Filter {
    private String pathWhiteList[] = new String[]{
            "/lib/jquery-3.6.0.min.js",
            "/favicon.ico",

            "/",
            "/index.html",
            "/index.js",

            "/login"

    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        System.out.println(uri);

        if (isWhiteList(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // login status check
            if (session != null && session.getAttribute("user") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // check Ajax request
                if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                    response.getWriter().write("Haven't login");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.html");
                }
            }
        }


    }

    private boolean isWhiteList(String uri) {
        for (String path : pathWhiteList) {
            if (path.equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
