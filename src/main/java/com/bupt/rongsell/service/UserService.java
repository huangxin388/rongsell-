package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.User;

/**
 * @Author huang xin
 * @Date 2020/3/5 8:26
 * @Version 1.0
 */
public interface UserService {
    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    ServerResponse<User> login(String userName, String password);

    /**
     * 注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 检验用户名或邮箱有效性
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 忘记密码时获取重置密码的问题
     * @param username
     * @return
     */
    ServerResponse<String> forgetQuestion(String username);

    /**
     * 检验问题对应的答案是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 用户未登录时通过回答问题重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 用户登录后通过提供原密码来重置密码
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    ServerResponse<User> updateUserInformation(User user);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    ServerResponse<User> getUserInformation(int userId);

    ServerResponse<String> checkAdminRole(User user);
}
