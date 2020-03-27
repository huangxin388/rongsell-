package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/3/5 8:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ServerResponse<User> login(String username, String password, HttpServletRequest request) {
        ServerResponse response = userService.login(username, password);
        if(response.isSuccess()) {
            request.getSession().setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }
    @GetMapping("/logout")
    public ServerResponse<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Const.CURRENT_USER);
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
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
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
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld, passwordNew, user);
    }

    @PostMapping("updateuserinformation")
    public ServerResponse<User> updateUserInformation(HttpServletRequest request, User user) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(currentUser == null) {
            return ServerResponse.getFailureByMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateUserInformation(user);
        if(response.isSuccess()) {
            request.getSession().setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @GetMapping("/getuserinformation")
    public ServerResponse<User> getUserInformation(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(currentUser == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录需要强制登录");
        }
        return userService.getUserInformation(currentUser.getId());
    }


}
