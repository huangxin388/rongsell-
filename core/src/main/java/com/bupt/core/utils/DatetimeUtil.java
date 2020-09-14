package com.bupt.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author huang xin
 * @Date 2020/3/21 16:40
 * @Version 1.0
 */
public class DatetimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * 字符串转换为date
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dateStr, String formatStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    /**
     * date转换为字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        if(date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            String dateStr = simpleDateFormat.format(date);
            return dateStr;
        }
        return "";
    }

    /**
     * 字符串转换为date
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_FORMAT);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    /**
     * date转换为字符串
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if(date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_FORMAT);
            String dateStr = simpleDateFormat.format(date);
            return dateStr;
        }
        return "";
    }
}
