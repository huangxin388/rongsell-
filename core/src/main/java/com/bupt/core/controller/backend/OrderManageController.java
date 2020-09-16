package com.bupt.core.controller.backend;

import com.bupt.common.config.cache.RedisUtil;
import com.bupt.common.utils.ServerResponse;
import com.bupt.core.service.OrderService;
import com.bupt.user.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/3/27 13:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/managegetorderall")
    public ServerResponse<PageInfo> manageGetOrderAll(HttpServletRequest request,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
//        // 读取cookie中的sessionId值
//        String sessionId = CookieUtil.readLoginCookie(request);
//        if(sessionId == null || "".equals(sessionId.trim())) {
//            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userStr = redisUtil.get(sessionId);
//        // 读取redis中存储的用户信息，并将其反序列化为User对象
//        User user = JsonUtil.string2Obj(userStr, User.class);
//        if(user == null) {
//            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if(userService.checkAdminRole(user).isSuccess()) {
//            // 是管理员
//            return orderService.manageGetAllOrders(pageNum, pageSize);
//        } else {
//            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
//        }
        return orderService.manageGetAllOrders(pageNum, pageSize);
    }

    @PostMapping("/manageordersearch")
    public ServerResponse<PageInfo> manageOrderSearch(HttpServletRequest request, Long orderNo,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
//        // 读取cookie中的sessionId值
//        String sessionId = CookieUtil.readLoginCookie(request);
//        if(sessionId == null || "".equals(sessionId.trim())) {
//            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userStr = redisUtil.get(sessionId);
//        // 读取redis中存储的用户信息，并将其反序列化为User对象
//        User user = JsonUtil.string2Obj(userStr, User.class);
//        if(user == null) {
//            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if(userService.checkAdminRole(user).isSuccess()) {
//            // 是管理员
//            return orderService.manageOrderSearch(orderNo, pageNum, pageSize);
//        } else {
//            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
//        }
        return orderService.manageOrderSearch(orderNo, pageNum, pageSize);
    }

    @PostMapping("/managesendgoods")
    public ServerResponse<String> manageSendGoods(HttpServletRequest request, Long orderNo) {
//        // 读取cookie中的sessionId值
//        String sessionId = CookieUtil.readLoginCookie(request);
//        if(sessionId == null || "".equals(sessionId.trim())) {
//            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userStr = redisUtil.get(sessionId);
//        // 读取redis中存储的用户信息，并将其反序列化为User对象
//        User user = JsonUtil.string2Obj(userStr, User.class);
//        if(user == null) {
//            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        if(userService.checkAdminRole(user).isSuccess()) {
//            // 是管理员
//            return orderService.manageSendGoods(orderNo);
//        } else {
//            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
//        }
        return orderService.manageSendGoods(orderNo);
    }
}
