package com.bupt.user.service.impl;

import com.bupt.common.constant.Const;
import com.bupt.common.utils.ServerResponse;
import com.bupt.common.config.cache.RedisUtil;
import com.bupt.user.dao.UserMapper;
import com.bupt.user.entity.User;
import com.bupt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @Author huang xin
 * @Date 2020/3/5 8:26
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public ServerResponse<User> login(String userName, String password) {
        int resultCount = userMapper.checkUser(userName);
        if(resultCount == 0) {
            return ServerResponse.getFailureByMessage("用户名不存在");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user = userMapper.selectLogin(userName, password);
        if(user == null) {
            return ServerResponse.getFailureByMessage("密码错误");
        }
        user.setPassword(null);
        return ServerResponse.getSuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        // 校验用户名是否已存在
        ServerResponse<String> validResponse = null;
        validResponse = checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()) {
            return validResponse;
        }
        // 校验邮箱是否已存在
        validResponse = checkValid(user.getEmail(), Const.EMAIL);
        if(!validResponse.isSuccess()) {
            return validResponse;
        }

        // 对密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        // 设置角色，默认注册的都是普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int resultCount = userMapper.insert(user);
        if(resultCount == 0) {
            return ServerResponse.getFailureByMessage("注册失败");
        }
        return ServerResponse.getSuccessByMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(type != null && !"".equals(type) && !" ".equals(type)) {
            int resultCount = 0;
            if(Const.USERNAME.equals(type)) {
                resultCount = userMapper.checkUser(str);
                if(resultCount > 0) {
                    return ServerResponse.getFailureByMessage("用户名已存在");
                }
            }

            if(Const.EMAIL.equals(type)) {
                resultCount = userMapper.checkEmail(str);
                if(resultCount > 0) {
                    return ServerResponse.getFailureByMessage("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        return ServerResponse.getSuccessByMessage("校验成功");
    }

    @Override
    public ServerResponse<String> forgetQuestion(String username) {
        ServerResponse<String> validResponse = checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()) {
            // 用户名不存在
            return ServerResponse.getFailureByMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUserName(username);
        if(question == null || "".equals(question)) {
            return ServerResponse.getFailureByMessage("找回密码的问题为空");
        }
        return ServerResponse.getSuccessByMessage(question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkQuestionAndAnswerByUsername(username, question, answer);
        if(resultCount > 0) {
            String token = UUID.randomUUID().toString().replace("-", "");
            // 有效期为12个小时
            redisUtil.setex(Const.REDIS_PREFIX + username, 60*60*12, token);
            return ServerResponse.getSuccess(token);
        }
        return ServerResponse.getFailureByMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        ServerResponse<String> validResponse = checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()) {
            // 用户名不存在
            return ServerResponse.getFailureByMessage("用户名不存在");
        }
        String token = redisUtil.get(Const.REDIS_PREFIX + username);
        if(token == null) {
            return ServerResponse.getFailureByMessage("token不存在或已过期");
        }

        if(token.equals(forgetToken)) {
            String md5Password = DigestUtils.md5DigestAsHex(passwordNew.getBytes());
            int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
            if(resultCount > 0) {
                return ServerResponse.getSuccessByMessage("密码重置成功");
            } else {
                return ServerResponse.getFailureByMessage("密码重置失败");
            }
        }
        return ServerResponse.getFailureByMessage("token不正确，请重新回答问题");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        String md5PasswordOld = DigestUtils.md5DigestAsHex(passwordOld.getBytes());
        int resultCount = userMapper.checkPassword(user.getId(), md5PasswordOld);
        if(resultCount > 0) {
            String md5PasswordNew = DigestUtils.md5DigestAsHex(passwordNew.getBytes());
            user.setPassword(md5PasswordNew);
            resultCount = userMapper.updateByPrimaryKeySelective(user);
            if(resultCount > 0) {
                return ServerResponse.getSuccessByMessage("密码修改成功");
            } else {
                return ServerResponse.getFailureByMessage("密码修改失败");
            }
        }
        return ServerResponse.getFailureByMessage("原密码错误");
    }

    @Override
    public ServerResponse<User> updateUserInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getId(), user.getEmail());
        if(resultCount > 0) {
            return ServerResponse.getFailureByMessage("邮箱已被他人使用，请更换email后重试");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount > 0) {
            return ServerResponse.getSuccess("用户信息更新成功", updateUser);
        } else {
            return ServerResponse.getFailureByMessage("用户信息更新失败");
        }
    }

    @Override
    public ServerResponse<User> getUserInformation(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null) {
            return ServerResponse.getFailureByMessage("找不到当前用户的信息");
        }
        return ServerResponse.getSuccess(user);
    }

    @Override
    public ServerResponse<String> checkAdminRole(User user) {
        if(user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.getSuccess();
        } else {
            return ServerResponse.getFailure();
        }
    }
}
