package com.bupt.rongsell.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author huang xin
 * @Date 2020/3/21 16:10
 * @Version 1.0
 */
public class PropertyUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    private static Properties props;

    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if("".equals(key.trim())) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key.trim());
        if("".equals(key.trim())) {
            value = defaultValue;
        }
        return value.trim();
    }
}
