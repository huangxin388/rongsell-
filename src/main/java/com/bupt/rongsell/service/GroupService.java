package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Group;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/7/8 15:02
 * @Version 1.0
 */
public interface GroupService {

    /**
     * 获取平级的子节点
     * @param parentId
     * @return
     */
    ServerResponse<List<Group>> getParallelChildrenGroup(Integer parentId);
}
