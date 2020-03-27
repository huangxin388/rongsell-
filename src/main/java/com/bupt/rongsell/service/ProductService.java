package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Product;
import com.bupt.rongsell.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/21 15:41
 * @Version 1.0
 */
public interface ProductService {

    /**
     * 添加或更新商品信息
     * @param product
     * @return
     */
    ServerResponse<String> updateOrInsertProduct(Product product);

    /**
     * 更新商品的上下架状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 获取商品详细信息
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 获取商品列表
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 查询商品信息
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    /**
     * 前台方法，根据商品id获取详情信息
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 通过关键词和分类查询商品列表
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize);


}
