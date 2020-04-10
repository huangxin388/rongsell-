package com.bupt.rongsell.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户请求访问到不同的服务器会携带不同的JSESSIONID，
 * 为了解决每次访问携带的JSESSIONID不同导致登录失效的问题
 * 登录后将登录时的JSESSIONID写入cookie
 * @Author huang xin
 * @Date 2020/4/8 22:21
 * @Version 1.0
 */
@Slf4j
public class CookieUtil {
    /**
     * 注意前面不要加".",老版本喜欢在前边加一个".",tomcat8.5以后不能再加"."
     */
    public static final String COOKIE_DOMAIN = "localserver.com";
//    public static final String COOKIE_DOMAIN = "localhost";
    public static final String COOKIE_NAME = "haitao_login_token";

    /**
     * 写入cookie
     * @param response
     * @param token
     */
    public static void writeLoginCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        // 存在根目录
        cookie.setPath("/");
        // 不允许脚本读取cookie
        cookie.setHttpOnly(true);
        // cookie的有效期是一年,单位是秒；-1代表永久
        cookie.setMaxAge(60*60*24*365);
        response.addCookie(cookie);
        log.info("write cookie,cookie name:{}, cookie value:{}", cookie.getName(), cookie.getValue());
    }

    /**
     * 读取cookie
     * @param request
     * @return
     */
    public static String readLoginCookie(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for(Cookie ck : cks) {
                if(COOKIE_NAME.equals(ck.getName())) {
                    log.info("read cookie name:{}, cookie value:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除cookie
     * @param request
     * @param response
     * @return
     */
    public static void deleteLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for(Cookie ck : cks) {
                if(COOKIE_NAME.equals(ck.getName())) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    // 设置成0，代表删除此cookie
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                    log.info("delete cookie,cookie name:{}, cookie value:{}", ck.getName(), ck.getValue());
                    return;
                }
            }
        }
    }

}
