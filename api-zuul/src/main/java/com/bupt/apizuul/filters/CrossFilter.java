package com.bupt.apizuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huang xin
 * @Date 2020/9/15 18:11
 * @Version 1.0
 */
@Slf4j
public class CrossFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器初始化完成");
        RequestContext rtx = RequestContext.getCurrentContext();
        HttpServletRequest req = rtx.getRequest();
        HttpServletResponse resp = rtx.getResponse();

        // 得到请求的uri和url
        String reqUri = req.getRequestURI();
        String reqUrl = req.getRequestURL().toString();
        log.info(" reqUri:{} ,reqUrl:{} ", reqUri, reqUrl);

        // 处理跨域
        this.crossDomain(req, resp, reqUrl);
        System.out.println("zuul跨域问题解决完成");
        return null;
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
        // 允许来自所有域的访问
        response.setHeader("Access-Control-Allow-Origin", origin);
        // 允许客户端携带验证信息，例如 cookie 之类的
        response.setHeader("Access-Control-Allow-Credentials","true");
        // 响应标头指定响应访问所述资源到时允许的一种或多种方法预检请求。
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("P3P", "CP=CAO PSA OUR");
        // 允许浏览器header中带有哪些字段
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, isLogin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, " +
                        "Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization");

    }
}
