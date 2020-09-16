package com.bupt.core.service;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.vo.CartVo;

/**
 * @Author huang xin
 * @Date 2020/3/23 15:16
 * @Version 1.0
 */
public interface CartService {

    /**
     * 向购物车中添加商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> addProduct(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车中的商品信息
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> updateProduct(Integer userId, Integer productId, Integer count);

    /**
     * 删除商品信息
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    /**
     * 获取购物车信息
     * @param userId
     * @return
     */
    ServerResponse<CartVo> getCartInfo(Integer userId);

    /**
     * 全选或全不选
     * @param userId
     * @param check
     * @return
     */
    ServerResponse<CartVo> selectCheckedOrUnCheckedAll(Integer userId, Boolean check);

    /**
     * 购物车中单一商品的选择
     * @param userId
     * @param productId
     * @param check
     * @return
     */
    ServerResponse<CartVo> setProductCheckStatus(Integer userId, Integer productId, Boolean check);

    /**
     * 获取购物车中的商品数量
     * @param userId
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
