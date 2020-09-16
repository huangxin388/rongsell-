package com.bupt.core.service.impl;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.dao.GroupMapper;
import com.bupt.core.entity.Group;
import com.bupt.core.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品上架时的后台分类
 * @Author huang xin
 * @Date 2020/7/8 15:03
 * @Version 1.0
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public ServerResponse<List<Group>> getParallelChildrenGroup(Integer parentId) {
        List<Group> groupList = groupMapper.selectParallelChildrenGroup(parentId);
        if(groupList.size() == 0) {
            log.error("未找到该分类下的子分类");
        }
        return ServerResponse.getSuccess(groupList);
    }
}
