package com.bupt.rongsell.utils;

import java.math.BigDecimal;

/**
 * 解决浮点数运算丢失精度的问题
 * @Author huang xin
 * @Date 2020/3/23 15:54
 * @Version 1.0
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {}

    /**
     * 加
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2= new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 减
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2= new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 乘
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2= new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 除
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2= new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP); // 保留两位小数，四舍五入
    }

}
