package com.bupt.core.service.impl;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.dao.SpecParamMapper;
import com.bupt.core.entity.SpecParam;
import com.bupt.core.service.ParamService;
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

    @Override
    public ServerResponse<List<SpecParam>> getSaleParams(Integer groupId) {
        List<SpecParam> specParamList = specParamMapper.selectSaleParamByGroupId(groupId);
        return ServerResponse.getSuccess(specParamList);
    }
}
