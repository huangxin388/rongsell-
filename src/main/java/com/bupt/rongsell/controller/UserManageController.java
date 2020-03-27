package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/3/17 18:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ServerResponse<User> login(String username, String password, HttpServletRequest request) {
        ServerResponse response = userService.login(username, password);
        if(response.isSuccess()) {
            User user = (User) response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                // 说明当前登录的是管理员
                request.getSession().setAttribute(Const.CURRENT_USER, response.getData());
                return response;
            } else {
                return ServerResponse.getFailureByMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
