package com.bupt.rongsell.filter;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.utils.CookieUtil;
import com.bupt.rongsell.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录的用户每次请求都需要重置session过期时间
 * @Author huang xin
 * @Date 2020/4/9 18:38
 * @Version 1.0
 */
public class SessionExpireFilter implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String sessionId = CookieUtil.readLoginCookie(httpServletRequest);
        User user = null;
        if(sessionId != null && !"".equals(sessionId)) {
            String userStr = redisUtil.get(sessionId);
            // 我们设置cookie的过期时间是一年，redis中设置用户登录的过期时间是30分钟
            // 有可能用户的登录过期了，但是cookie没过期。也就是可以读到cookie，却读不到redis
            // 因此这里还要判断以下redis中的内容是否为空
            if(userStr != null && !"".equals(userStr)) {
                user = JsonUtil.string2Obj(userStr, User.class);
            }
            if(user != null) {
                redisUtil.setex(sessionId, Const.RedisCacheExTime.REDIS_SESSION_EX_TIME, userStr);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
