package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.OrderService;
import com.bupt.rongsell.service.UserService;
import com.bupt.rongsell.vo.OrderVo;
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

    @PostMapping("/managegetorderall")
    public ServerResponse<PageInfo> manageGetOrderAll(HttpServletRequest request,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return orderService.manageGetAllOrders(pageNum, pageSize);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }

    @PostMapping("/manageordersearch")
    public ServerResponse<PageInfo> manageOrderSearch(HttpServletRequest request, Long orderNo,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return orderService.manageOrderSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }

    @PostMapping("/managesendgoods")
    public ServerResponse<String> manageSendGoods(HttpServletRequest request, Long orderNo) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return orderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }
}
