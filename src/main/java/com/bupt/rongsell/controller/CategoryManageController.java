package com.bupt.rongsell.controller;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.dao.CategoryMapper;
import com.bupt.rongsell.entity.Category;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.CategoryService;
import com.bupt.rongsell.service.UserService;
import com.bupt.rongsell.utils.CookieUtil;
import com.bupt.rongsell.utils.JsonUtil;
import com.bupt.rongsell.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/17 19:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/addcategory")
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
                                              String categoryName, HttpServletRequest request) {
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
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return categoryService.addCategory(parentId, categoryName);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }

    @PostMapping("/changecategoryname")
    public ServerResponse<String> changeCategoryName(Integer categoryId, String categoryName, HttpServletRequest request) {
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
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }

    @PostMapping("/getparallelchildrencategory")
    public ServerResponse<List<Category>> getParallelChildrenCategory(HttpServletRequest request,
                                                                      @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
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
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return categoryService.getParallelChildrenCategory(parentId);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }

    @PostMapping("/getrecursivelchildrencategory")
    public ServerResponse<List<CategoryVo>> getRecursiveChildrenCategory(HttpServletRequest request,
                                                                         @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
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
        if(userService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            return categoryService.getRecursiveChildrenCategory(categoryId);
        } else {
            return ServerResponse.getFailureByMessage("权限不够，管理员才能进行此操作");
        }
    }


}
