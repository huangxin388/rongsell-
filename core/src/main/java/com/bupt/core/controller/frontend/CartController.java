package com.bupt.core.controller.frontend;

import com.bupt.core.common.Const;
import com.bupt.core.common.ServerResponse;
import com.bupt.core.config.cache.RedisUtil;
import com.bupt.core.entity.User;
import com.bupt.core.enums.ResponseCode;
import com.bupt.core.service.CartService;
import com.bupt.core.utils.CookieUtil;
import com.bupt.core.utils.JsonUtil;
import com.bupt.core.vo.CartVo;
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

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 向购物车中添加商品
     * @param request
     * @param productId
     * @param count
     * @return
     */
    @PostMapping("addproduct")
    public ServerResponse<CartVo> addProduct(HttpServletRequest request, Integer productId, Integer count) {

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
        return cartService.addProduct(user.getId(), productId, count);
    }

    @PostMapping("updateProductInfo")
    public ServerResponse<CartVo> updateProductInfo(HttpServletRequest request, Integer productId, Integer count) {

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
        return cartService.updateProduct(user.getId(), productId, count);
    }

    @PostMapping("deleteProduct")
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds) {

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
        return cartService.deleteProduct(user.getId(), productIds);
    }

    @PostMapping("getcartinfo")
    public ServerResponse<CartVo> getCartInfo(HttpServletRequest request) {

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
        return cartService.getCartInfo(user.getId());
    }

    @GetMapping("selectall")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {

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
        return cartService.selectCheckedOrUnCheckedAll(user.getId(), Const.Cart.PRODUCT_CHECKED);
    }

    @GetMapping("unselectall")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
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
        return cartService.selectCheckedOrUnCheckedAll(user.getId(), Const.Cart.PRODUCT_UNCHECKED);
    }

    @PostMapping("selectproduct")
    public ServerResponse<CartVo> selectProduct(HttpServletRequest request, Integer productId) {
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
        return cartService.setProductCheckStatus(user.getId(), productId, Const.Cart.PRODUCT_CHECKED);
    }

    @PostMapping("unselectproduct")
    public ServerResponse<CartVo> unSelectProduct(HttpServletRequest request, Integer productId) {
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
        return cartService.setProductCheckStatus(user.getId(), productId, Const.Cart.PRODUCT_UNCHECKED);
    }

    @PostMapping("getcartproductcount")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
        // 读取cookie中的sessionId值
        String sessionId = CookieUtil.readLoginCookie(request);
        if(sessionId == null || "".equals(sessionId.trim())) {
            return ServerResponse.getFailureByMessage("用户未登录，无法获取当前用户信息");
        }
        String userStr = redisUtil.get(sessionId);
        // 读取redis中存储的用户信息，并将其反序列化为User对象
        User user = JsonUtil.string2Obj(userStr, User.class);
        if(user == null) {
            return ServerResponse.getSuccess(0);
        }
        return cartService.getCartProductCount(user.getId());
    }






}
