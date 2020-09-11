package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.SpecParam;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/2 10:39
 * @Version 1.0
 */
public interface ParamService {
    /**
     * 通过品类id获取该spu对应的参数
     * @param groupId
     * @return
     */
    ServerResponse<List<SpecParam>> getSpuParams(Integer groupId);

    /**
     * 通过品类id获取该spu对应的销售参数
     * @param groupId
     * @return
     */
    ServerResponse<List<SpecParam>> getSaleParams(Integer groupId);
}
