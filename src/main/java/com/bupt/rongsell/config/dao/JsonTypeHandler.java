package com.bupt.rongsell.config.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 配置mybatis处理json类型数据
 * @Author huang xin
 * @Date 2020/7/2 11:26
 * @Version 1.0
 */
public class JsonTypeHandler extends BaseTypeHandler<JSONObject> {

    /**
     * 设置非空参数
     * @param preparedStatement
     * @param i
     * @param jsonObject
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject jsonObject, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, String.valueOf(jsonObject.toJSONString()));
    }

    /**
     * 根据列名，获取可以为空的结果
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sqlJson = resultSet.getString(s);
        if (null != sqlJson){
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }

    /**
     * 根据列索引，获取可以为空的结果
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if (null != sqlJson){
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if (null != sqlJson){
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }
}
