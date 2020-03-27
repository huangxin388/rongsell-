package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Shipping;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author huang xin
 * @Date 2020/3/23 22:42
 * @Version 1.0
 */
public interface ShippingService {

    /**
     * 新建收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse<Map<String, Integer>> addAddress(Integer userId, Shipping shipping);

    /**
     * 删除地址信息
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<String> deleteAddress(Integer userId, Integer shippingId);


    /**
     * 更新地址信息
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse<Map<String, Integer>> updateAddress(Integer userId, Shipping shipping);

    /**
     * 查询某个地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> getAddress(Integer userId, Integer shippingId);

    /**
     * 查询该用户的所有地址信息
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> getAddressList(Integer userId, int pageNum, int pageSize);
}
