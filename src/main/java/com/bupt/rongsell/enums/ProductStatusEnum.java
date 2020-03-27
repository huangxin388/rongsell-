package com.bupt.rongsell.enums;

/**
 * @Author huang xin
 * @Date 2020/3/21 19:05
 * @Version 1.0
 */
public enum  ProductStatusEnum {

    ON_SALE(1, "在架");

    private int code;
    private String value;

    ProductStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
