package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huang xin
 * @Date 2020/2/25 16:31
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/test")
    public ServerResponse<String> test() {
        return ServerResponse.getSuccess("测试");
    }

    @GetMapping("/testjedis")
    public ServerResponse<String> testJedis() {
        redisUtil.set("key","测试redis");
        String value = redisUtil.get("key");
        return ServerResponse.getSuccess(value);

    }
}
