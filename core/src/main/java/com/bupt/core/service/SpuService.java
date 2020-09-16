package com.bupt.core.service;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.entity.Spu;
import com.bupt.core.vo.SpuAndSkuVo;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/1 19:46
 * @Version 1.0
 */
public interface SpuService {

    /**
     * 根据条件查询产品信息
     * @param spu
     * @return
     */
    ServerResponse<List<Spu>> getSpusBySpgId(Spu spu);


    /**
     * 在添加产品信息的同时添加商品信息
     * 目前版本添加一个spu同时添加一个sku
     * 在后面的迭代版本中需要改成，添加一个spu并根据额外信息添加多个sku
     * @param spuAndSkuVo
     * @return
     */
    ServerResponse<String> addSpuAndSkuInfo(SpuAndSkuVo spuAndSkuVo);
}
