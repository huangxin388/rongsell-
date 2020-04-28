package com.bupt.rongsell.filter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开发时用于解决跨域问题
 * @Author huang xin
 * @Date 2019/11/18 11:25
 * @Version 1.0
 */
public class CrossFilter implements Filter{
    private static final Logger log = LoggerFactory.getLogger(CrossFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 得到请求的uri和url
        String reqUri = req.getRequestURI();
        String reqUrl = req.getRequestURL().toString();
        log.info(" reqUri:{} ,reqUrl:{} ", reqUri, reqUrl);

        // 跨域处理
        this.crossDomain(req, resp, reqUrl);
        chain.doFilter(req, resp);
        System.out.println("跨域问题解决完成");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化完成");
    }

    /**
     * 处理跨域问题
     */
    private void crossDomain(HttpServletRequest request, HttpServletResponse response, String url) {
        response.setCharacterEncoding("UTF-8");
        log.info("REFERER:{}", request.getHeader("REFERER"));
        String origin = request.getHeader("Origin");
        if(origin == null) {
            origin = request.getHeader("Referer");
        }
        //允许来自所有域的访问
//        response.setHeader("Set-Cookie", response.getHeader("Set-Cookie")+";SameSite=None;Secure");
        response.setHeader("Access-Control-Allow-Origin", origin);
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8088");
        // 允许客户端携带验证信息，例如 cookie 之类的
        response.setHeader("Access-Control-Allow-Credentials","true");
        // 响应标头指定响应访问所述资源到时允许的一种或多种方法预检请求。
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("P3P", "CP=CAO PSA OUR");
        // 允许浏览器header中带有哪些字段
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, isLogin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization");

    }
}
