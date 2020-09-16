package com.bupt.common.utils;

import com.bupt.common.enums.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author huang xin
 * @Date 2020/3/4 22:05
 * @Version 1.0
 * @JsonInclude 当某个字段为null时不返回此字段
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> {
    private int code;
    private String msg;
    private T data;

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * @JsonIgnore 返回时不序列化此方法,一般用在属性上
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> getSuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> getSuccess(T date) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), date);
    }

    public static <T> ServerResponse<T> getSuccessByMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> getSuccess(String msg, T date) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, date);
    }

    public static <T> ServerResponse<T> getFailure() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> getFailureByMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ServerResponse<T> getFailureByCodeMessage(int code, String msg) {
        return new ServerResponse<T>(code, msg);
    }
}
