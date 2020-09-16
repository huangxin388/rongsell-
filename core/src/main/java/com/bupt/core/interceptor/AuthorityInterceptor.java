package com.bupt.core.interceptor;

import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.constant.Const;
import com.bupt.common.utils.ServerResponse;
import com.bupt.common.utils.CookieUtil;
import com.bupt.common.utils.JsonUtil;
import com.bupt.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 判断管理员权限拦截器
 * @Author huang xin
 * @Date 2020/4/10 10:22
 * @Version 1.0
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String className = handlerMethod.getBean().getClass().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        if("UserManageController".equals(className) && "login".equals(methodName)) {
            // 如果是登录请求，直接放行
            return true;
        }
        log.info("拦截器：className:{},methodName:{}",className, methodName);
        User user = null;
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId != null && !"".equals(sessionId.trim())) {
            String userStr = redisUtil.get(sessionId);
            // 我们设置cookie的过期时间是一年，redis中设置用户登录的过期时间是30分钟
            // 有可能用户的登录过期了，但是cookie没过期。也就是可以读到cookie，却读不到redis
            // 因此这里还要判断以下redis中的内容是否为空
            if(userStr != null && !"".equals(userStr)) {
                // 读取redis中存储的用户信息，并将其反序列化为User对象
                user = JsonUtil.string2Obj(userStr, User.class);
            } else {
                // 登录过期
            }
        }
        if(user == null || (user.getRole() != Const.Role.ROLE_ADMIN)) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            if(user == null) {
                printWriter.print(JsonUtil.obj2String(ServerResponse.getFailureByMessage("拦截器拦截，用户未登录或登录已过期")));
            } else {
                printWriter.print(JsonUtil.obj2String(ServerResponse.getFailureByMessage("拦截器拦截，用户无权限操作")));
            }
            printWriter.flush();
            printWriter.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
