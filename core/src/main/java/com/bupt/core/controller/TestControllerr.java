package com.bupt.core.controller;

import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.constant.Const;
import com.bupt.common.utils.ServerResponse;
import com.bupt.core.dao.HobbyMapper;
import com.bupt.core.entity.Hobby;
import com.bupt.core.feign.UserApi;
import com.bupt.common.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/2/25 16:31
 * @Version 1.0
 */
@Controller
@Slf4j
public class TestControllerr {

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private HobbyMapper hobbyMapper;

    @Resource
    private UserApi userApi;

    @RequestMapping("/test")
    @ResponseBody
    public ServerResponse<String> test() {
        return ServerResponse.getSuccess("测试1");
    }

    @GetMapping("/testjedis")
    @ResponseBody
    public ServerResponse<String> testJedis() {
        redisUtil.set("key","测试redis");
        String value = redisUtil.get("key");
        return ServerResponse.getSuccess(value);
    }

    @GetMapping("/writecookie")
    @ResponseBody
    public String writeCookie(HttpServletResponse response, HttpServletRequest request) {
        CookieUtil.writeLoginCookie(response, request.getSession().getId());
        redisUtil.setex(request.getSession().getId(), Const.RedisCacheExTime.REDIS_SESSION_EX_TIME, "test save");
        return "写cookie11111";
    }

    @GetMapping("/readcookie")
    @ResponseBody
    public String readCookie(HttpServletRequest request) {
        String value = CookieUtil.readLoginCookie(request);
        log.info("cookie value:{}", value);
        return value + "11111";
    }

    @GetMapping("/deletecookie")
    @ResponseBody
    public String deleteCookie(HttpServletRequest request,HttpServletResponse response) {
        CookieUtil.deleteLoginCookie(request, response);
        String value = CookieUtil.readLoginCookie(request);
        log.info("delete cookie");
        redisUtil.del(value);
        return "删除cookie11111";
    }

    @GetMapping("/gethobby")
    @ResponseBody
    public List<Hobby> getHobby() {
        List<Hobby> hobbyList = hobbyMapper.selectHobbys();
        return hobbyList;
    }

    @GetMapping("/testfeign")
    @ResponseBody
    public String testFeign() {
        return userApi.testMethod();
    }

}
