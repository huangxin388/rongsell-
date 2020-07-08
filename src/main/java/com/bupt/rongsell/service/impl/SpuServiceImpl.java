package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.GroupMapper;
import com.bupt.rongsell.dao.SpuMapper;
import com.bupt.rongsell.entity.Spu;
import com.bupt.rongsell.entity.SpuExample;
import com.bupt.rongsell.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/1 19:46
 * @Version 1.0
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public ServerResponse<List<Spu>> getSpusBySpgId(Spu spu) {
        SpuExample spuExample = new SpuExample();
        SpuExample.Criteria criteria = spuExample.createCriteria();
        if(spu.getSpgId() != null) {
            criteria.andSpgIdEqualTo(spu.getSpgId());
        }
        if(spu.getId() != null) {
            criteria.andIdEqualTo(spu.getId());
        }
        List<Spu> spuList = spuMapper.selectByExample(spuExample);
        return ServerResponse.getSuccess(spuList);
    }
}
