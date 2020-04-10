package com.bupt.rongsell.interceptor;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.utils.CookieUtil;
import com.bupt.rongsell.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 判断用户是否登录
 * @Author huang xin
 * @Date 2020/4/10 19:19
 * @Version 1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String sessionId = CookieUtil.readLoginCookie(request);
        User user = null;
        if(sessionId != null && !"".equals(sessionId)) {
            String userStr = redisUtil.get(sessionId);
            if(userStr != null && !"".equals(userStr)) {
                user = JsonUtil.string2Obj(userStr, User.class);
                // 已登陆，放行
                if(user != null) {
                    return true;
                }
            }
        }
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(JsonUtil.obj2String(ServerResponse.getFailureByMessage("拦截器拦截，用户未登录或登录已过期")));
        printWriter.flush();
        printWriter.close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
