package com.bupt.common.enums;

/**
 * @Author huang xin
 * @Date 2020/3/4 22:09
 * @Version 1.0
 */
public enum  ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");


    private int code;
    private String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
