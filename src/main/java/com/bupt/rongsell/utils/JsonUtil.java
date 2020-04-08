package com.bupt.rongsell.utils;

import com.bupt.rongsell.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用redis存储session信息时，将对象序列化为字符串
 * @Author huang xin
 * @Date 2020/4/8 19:10
 * @Version 1.0
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        // 将对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // 所有的日期格式都统一为一下的样式，即 yyyy-MM-dd hh:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DatetimeUtil.STANDARD_FORMAT));

        // 忽略 在json字符串中存在，但在java对象中不存在对应属性的情况。防止反序列化失败
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象序列化为字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj) {
        if(obj == null) {
            return null;
        }
        try {
            if(obj instanceof String) {
                return (String)obj;
            }
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parse object to String error", e);
            return null;
        }
    }

    /**
     * 对象序列化为格式比较美观的字符串,调试时使用
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj) {
        if(obj == null) {
            return null;
        }
        try {
            if(obj instanceof String) {
                return (String)obj;
            }
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parse object to String error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if("".equals(str) || clazz == null) {
            return null;
        }
        try {
            if(clazz.equals(String.class)) {
                return (T)str;
            }
            return objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("read string to object error", e);
            return null;
        }
    }

    /**
     * 字符串反序列化为对象，typeReference方式
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if("".equals(str) || typeReference == null) {
            return null;
        }
        try {
            if(typeReference.getType().equals(String.class)) {
                return (T)str;
            }
            return objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            log.warn("read string to object error", e);
            return null;
        }
    }

    /**
     * 字符串反序列化为对象，class方式
     * @param str
     * @param collectionClass
     * @param classes
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... classes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, classes);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("read string to object error", e);
            return null;
        }
    }

//    public static void main(String[] args) {
//        List<User> userList = new ArrayList<>();
//        User user1 = new User();
//        user1.setId(1);
//        user1.setUsername("test1");
//        User user2 = new User();
//        user2.setId(2);
//        user2.setUsername("test2");
//        userList.add(user1);
//        userList.add(user2);
//        String userListStr = obj2StringPretty(userList);
//        log.info("userListStr", userListStr);
//        log.info("=========================");
//        List<User> list = string2Obj(userListStr, new TypeReference<List<User>>(){});
//        List<User> list2 = string2Obj(userListStr, List.class, User.class);
//        log.info("=================");
//    }
}
