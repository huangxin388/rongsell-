package com.bupt.core.service;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.entity.Group;

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
