package com.bao.spring.myfilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @Title: MyBeforeLoginFilter
 * @Description:
 * @Author: BaoGaoLang
 * @Date: 2018/3/30 9:55
 */
public class MyBeforeLoginFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.print("=======MyBeforeLoginFilter========"+request.getContentType()+"==="+request.getParameter("username"));
        chain.doFilter(request,response);
    }
}
