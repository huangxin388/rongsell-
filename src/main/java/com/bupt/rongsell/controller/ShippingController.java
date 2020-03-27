package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Shipping;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.ShippingService;
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

    @PostMapping("/addaddress")
    ServerResponse<Map<String, Integer>> addAddress(HttpServletRequest request, Shipping shipping) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.addAddress(user.getId(), shipping);
    }

    @PostMapping("/deleteaddress")
    ServerResponse<String> deleteAddress(HttpServletRequest request, Integer shippingId) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.deleteAddress(user.getId(), shippingId);
    }

    @PostMapping("/updateaddress")
    ServerResponse<Map<String, Integer>> updateAddress(HttpServletRequest request, Shipping shipping) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.updateAddress(user.getId(), shipping);
    }

    @PostMapping("/getaddress")
    ServerResponse<Shipping> getAddress(HttpServletRequest request, Integer shippingId) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.getAddress(user.getId(), shippingId);
    }

    @PostMapping("/getaddresslist")
    ServerResponse<PageInfo> getAddressList(HttpServletRequest request,
                                            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return shippingService.getAddressList(user.getId(), pageNum, pageSize);
    }
}
