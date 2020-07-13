package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Spu;
import com.github.pagehelper.PageInfo;

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
}
