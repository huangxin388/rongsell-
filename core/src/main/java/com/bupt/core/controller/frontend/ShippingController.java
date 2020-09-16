package com.bupt.core.controller.frontend;

import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.utils.ServerResponse;
import com.bupt.core.entity.Shipping;
import com.bupt.common.enums.ResponseCode;
import com.bupt.core.service.ShippingService;
import com.bupt.common.utils.CookieUtil;
import com.bupt.common.utils.JsonUtil;
import com.bupt.user.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author huang xin
 * @Date 2020/3/23 20:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/addaddress")
    ServerResponse<Map<String, Integer>> addAddress(HttpServletRequest request, Shipping shipping) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.addAddress(user.getId(), shipping);
    }

    @PostMapping("/deleteaddress")
    ServerResponse<String> deleteAddress(HttpServletRequest request, Integer shippingId) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.deleteAddress(user.getId(), shippingId);
    }

    @PostMapping("/updateaddress")
    ServerResponse<Map<String, Integer>> updateAddress(HttpServletRequest request, Shipping shipping) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.updateAddress(user.getId(), shipping);
    }

    @PostMapping("/getaddress")
    ServerResponse<Shipping> getAddress(HttpServletRequest request, Integer shippingId) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.getAddress(user.getId(), shippingId);
    }

    @PostMapping("/getaddresslist")
    ServerResponse<PageInfo> getAddressList(HttpServletRequest request,
                                            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.getAddressList(user.getId(), pageNum, pageSize);
    }
}
