package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.SpecParamMapper;
import com.bupt.rongsell.entity.SpecParam;
import com.bupt.rongsell.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spu参数服务
 * @Author huang xin
 * @Date 2020/6/2 10:39
 * @Version 1.0
 */
@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public ServerResponse<List<SpecParam>> getSpuParams(Integer groupId) {
        List<SpecParam> specParamList = specParamMapper.selectSpuParamByGroupId(groupId);
        return ServerResponse.getSuccess(specParamList);
    }
}
