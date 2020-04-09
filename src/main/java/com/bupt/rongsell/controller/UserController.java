package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.UserService;
import com.bupt.rongsell.utils.CookieUtil;
import com.bupt.rongsell.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huang xin
 * @Date 2020/3/5 8:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public ServerResponse<User> login(String username, String password,
                                      HttpServletRequest request,
                                      HttpServletResponse httpServletResponse) {
        ServerResponse response = userService.login(username, password);
        if(response.isSuccess()) {
            CookieUtil.writeLoginCookie(httpServletResponse, request.getSession().getId());
            log.info("登录时写入cookie，sessionId:{}", request.getSession().getId());
            redisUtil.setex(request.getSession().getId(),
                    Const.RedisCacheExTime.REDIS_SESSION_EX_TIME, JsonUtil.obj2String(response.getData()));
        }
        return response;
    }
    @GetMapping("/logout")
    public ServerResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        // 删除cookie
        CookieUtil.deleteLoginCookie(request, response);
        log.info("删除cookie");
        // 删除redis中存储的sessionId
        redisUtil.del(sessionId);
        return ServerResponse.getSuccess();
    }

    @PostMapping("/register")
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    /**
     * 用户登录时实时校验用户名和邮箱是否合法(是否已存在)
     * @param str
     * @param type
     * @return
     */
    @PostMapping("/checkValid")
    public ServerResponse<String> checkValid(String str, String type) {
        return userService.checkValid(str, type);
    }

    @GetMapping("/getuserinfo")
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user != null) {
            return ServerResponse.getSuccess(user);
        }
        return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
    }

    @PostMapping("/forgetquestion")
    public ServerResponse<String> forgetQuestion(String username) {
        return userService.forgetQuestion(username);
    }

    @PostMapping("/checkanswer")
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 用户未登录时通过回答问题重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @PostMapping("/forgetresetpassword")
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 用户在登录状态下通过提供原密码进行重置密码
     * @param request
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @PostMapping("resetpassword")
    public ServerResponse<String> resetPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld, passwordNew, user);
    }

    @PostMapping("updateuserinformation")
    public ServerResponse<User> updateUserInformation(HttpServletRequest request, User user) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User currentUser = JsonUtil.string2Obj(userStr, User.class);
        if(currentUser == null) {
            return ServerResponse.getFailureByMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateUserInformation(user);
        if(response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            redisUtil.setex(sessionId,
                    Const.RedisCacheExTime.REDIS_SESSION_EX_TIME, JsonUtil.obj2String(response.getData()));
        }
        return response;
    }

    @GetMapping("/getuserinformation")
    public ServerResponse<User> getUserInformation(HttpServletRequest request) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User currentUser = JsonUtil.string2Obj(userStr, User.class);
        if(currentUser == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，无法获取当前用户信息");
        }
        return userService.getUserInformation(currentUser.getId());
    }


}
