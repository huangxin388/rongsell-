package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.CartService;
import com.bupt.rongsell.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/3/23 15:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    /**
     * 向购物车中添加商品
     * @param request
     * @param productId
     * @param count
     * @return
     */
    @PostMapping("addproduct")
    public ServerResponse<CartVo> addProduct(HttpServletRequest request, Integer productId, Integer count) {

        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.addProduct(user.getId(), productId, count);
    }

    @PostMapping("updateProductInfo")
    public ServerResponse<CartVo> updateProductInfo(HttpServletRequest request, Integer productId, Integer count) {

        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.updateProduct(user.getId(), productId, count);
    }

    @PostMapping("deleteProduct")
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds) {

        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.deleteProduct(user.getId(), productIds);
    }

    @PostMapping("getcartinfo")
    public ServerResponse<CartVo> getCartInfo(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.getCartInfo(user.getId());
    }

    @GetMapping("selectall")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.selectCheckedOrUnCheckedAll(user.getId(), Const.Cart.PRODUCT_CHECKED);
    }

    @GetMapping("unselectall")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.selectCheckedOrUnCheckedAll(user.getId(), Const.Cart.PRODUCT_UNCHECKED);
    }

    @PostMapping("selectproduct")
    public ServerResponse<CartVo> selectProduct(HttpServletRequest request, Integer productId) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.setProductCheckStatus(user.getId(), productId, Const.Cart.PRODUCT_CHECKED);
    }

    @PostMapping("unselectproduct")
    public ServerResponse<CartVo> unSelectProduct(HttpServletRequest request, Integer productId) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getFailureByCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return cartService.setProductCheckStatus(user.getId(), productId, Const.Cart.PRODUCT_UNCHECKED);
    }

    @PostMapping("getcartproductcount")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.getSuccess(0);
        }
        return cartService.getCartProductCount(user.getId());
    }






}
