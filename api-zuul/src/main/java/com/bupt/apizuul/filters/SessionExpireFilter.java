package com.bupt.apizuul.filters;

import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.utils.CookieUtil;
import com.bupt.common.constant.Const;
import com.bupt.common.utils.JsonUtil;
import com.bupt.user.entity.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/9/15 18:37
 * @Version 1.0
 */
@Slf4j
public class SessionExpireFilter extends ZuulFilter {

    @Resource
    private RedisUtil redisUtil;

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
        log.info("登录过期过滤器初始化");
        RequestContext rtx = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = rtx.getRequest();
        String sessionId = CookieUtil.readLoginCookie(httpServletRequest);
        User user = null;
        if(sessionId != null && !"".equals(sessionId)) {
            String userStr = redisUtil.get(sessionId);
            log.info("zuul filter, userStr = " + userStr);
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
        return null;
    }
}
