package com.bupt.user.controller.backend;

import com.bupt.common.constant.Const;
import com.bupt.common.utils.JsonUtil;
import com.bupt.common.utils.ServerResponse;
import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.utils.CookieUtil;
import com.bupt.user.entity.User;
import com.bupt.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huang xin
 * @Date 2020/3/17 18:36
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/manage/user")
@Slf4j
public class UserManageController {
    @Autowired
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public ServerResponse<User> login(String username, String password,
                                      HttpServletRequest request,
                                      HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = userService.login(username, password);
        if(response.isSuccess()) {
            User user = (User) response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                // 说明当前登录的是管理员
                CookieUtil.writeLoginCookie(httpServletResponse, request.getSession().getId());
                log.info("登录时写入cookie，sessionId:{}", request.getSession().getId());
                redisUtil.setex(request.getSession().getId(),
                        Const.RedisCacheExTime.REDIS_SESSION_EX_TIME, JsonUtil.obj2String(response.getData()));
                return response;
            } else {
                return ServerResponse.getFailureByMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
