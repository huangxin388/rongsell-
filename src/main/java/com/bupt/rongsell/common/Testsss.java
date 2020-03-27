package com.bupt.rongsell.common;


import java.util.UUID;

/**
 * @Author huang xin
 * @Date 2020/3/9 11:28
 * @Version 1.0
 */
public class Testsss {

    public static void main(String[] args) {
        String token = UUID.randomUUID().toString().replace("-", "");
        System.out.println(token);
    }
}
