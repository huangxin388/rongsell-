package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.vo.ProductDetailVo;
import com.bupt.rongsell.vo.SkuDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/2 12:41
 * @Version 1.0
 */
public interface SkuService {
    /**
     * 获取商品信息
     * @return
     */
    ServerResponse<List<Sku>> getSkuInfo();

    /**
     * 添加或更新商品信息
     * @param sku
     * @return
     */
    ServerResponse<String> updateOrInsertSku(Sku sku);

    /**
     * 更新商品的上下架状态
     * @param skuId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer skuId, Integer status);

    /**
     * 获取商品详细信息
     * @param skuId
     * @return
     */
    ServerResponse<SkuDetailVo> manageSkuDetail(Integer skuId);

    /**
     * 获取商品列表
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getSkuList(int pageNum, int pageSize);

    /**
     * 查询商品信息
     * @param skuTitle
     * @param skuId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchSku(String skuTitle, Integer skuId, int pageNum, int pageSize);

    /**
     * 前台方法，根据商品id获取详情信息
     * @param skuId
     * @return
     */
    ServerResponse<SkuDetailVo> getSkuDetail(Integer skuId);

    /**
     * 通过关键词和分类查询商品列表
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getSkuByKeyword(String keyword, int pageNum, int pageSize);

    /**
     * 通过skuId查询spgId
     * @param skuId
     * @return
     */
    ServerResponse<Integer> getSpgIdBySkuId(Integer skuId);

    ServerResponse<List<Sku>> testResolve();

}
