package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author huang xin
 * @Date 2020/2/25 16:31
 * @Version 1.0
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/test")
    @ResponseBody
    public ServerResponse<String> test() {
        return ServerResponse.getSuccess("测试2");
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
        return "写cookie22222";
    }

    @GetMapping("/readcookie")
    @ResponseBody
    public String readCookie(HttpServletRequest request) {
        String value = CookieUtil.readLoginCookie(request);
        log.info("cookie value:{}", value);
        return value + "22222";
    }

    @GetMapping("/deletecookie")
    @ResponseBody
    public String deleteCookie(HttpServletRequest request,HttpServletResponse response) {
        CookieUtil.deleteLoginCookie(request, response);
        String value = CookieUtil.readLoginCookie(request);
        log.info("delete cookie");
        redisUtil.del(value);
        return "删除cookie22222";
    }
}
