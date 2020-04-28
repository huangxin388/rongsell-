package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author huang xin
 * @Date 2020/3/26 12:05
 * @Version 1.0
 */
public interface OrderService {

    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse createOrder(Integer userId, Integer shippingId);
    /**
     * 支付
     * @param userId
     * @param orderNo
     * @param path
     * @return
     */
    ServerResponse<Map<String, String>> pay(Integer userId, Long orderNo, String path);

    /**
     * 支付宝支付回调
     * @param params
     * @return
     */
    ServerResponse alipayCallBack(Map<String, String> params);

    /**
     * 查询用户是否已付款
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<String> cancel(Integer userId, Long orderNo);

    /**
     * 订单填写页下面的商品展示
     * @param userId
     * @return
     */
    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 获取订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    /**
     * 获取订单列表
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);


    /**
     * 管理员获取所有订单信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageGetAllOrders(Integer pageNum, Integer pageSize);

    /**
     * 管理员查询订单详情
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> manageGetOrderDetail(Long orderNo);

    /**
     * 订单查询
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageOrderSearch(Long orderNo, Integer pageNum, Integer pageSize);

    /**
     * 发货
     * @param orderNo
     * @return
     */
    ServerResponse<String> manageSendGoods(Long orderNo);

    /**
     * 关闭在hour小时内未付款的订单
     * @param hour
     */
    void closeOrder(int hour);
}
