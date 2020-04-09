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
        if(sessionId != null && !"".equals(sessionId)) {
            String userStr = redisUtil.get(sessionId);
            User user = JsonUtil.string2Obj(userStr, User.class);
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
