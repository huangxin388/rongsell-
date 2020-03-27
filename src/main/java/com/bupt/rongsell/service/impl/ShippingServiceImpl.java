package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.ShippingMapper;
import com.bupt.rongsell.entity.Shipping;
import com.bupt.rongsell.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huang xin
 * @Date 2020/3/23 22:43
 * @Version 1.0
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Map<String, Integer>> addAddress(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        int resultCount = shippingMapper.insert(shipping);
        if(resultCount > 0) {
            Map<String, Integer> modelMap = new HashMap<>();
            modelMap.put("shippingId", shipping.getId());
            return ServerResponse.getSuccess("新建地址成功", modelMap);
        }
        return ServerResponse.getFailureByMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> deleteAddress(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByUserIdShippingId(userId, shippingId);
        if(resultCount > 0) {
            return ServerResponse.getSuccessByMessage("地址删除成功");
        } else {
            return ServerResponse.getSuccessByMessage("地址删除失败");
        }
    }

    @Override
    public ServerResponse<Map<String, Integer>> updateAddress(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        shipping.setUpdateTime(new Date());
        int resultCount = shippingMapper.safeUpdateByPrimaryKey(shipping);
        if(resultCount > 0) {
            return ServerResponse.getSuccessByMessage("地址更新成功");
        } else {
            return ServerResponse.getSuccessByMessage("地址更新失败");
        }
    }

    @Override
    public ServerResponse<Shipping> getAddress(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectShippingById(userId, shippingId);
        if(shipping == null) {
            return ServerResponse.getFailureByMessage("无法找到该地址信息");
        }
        return ServerResponse.getSuccess(shipping);
    }

    @Override
    public ServerResponse<PageInfo> getAddressList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.getSuccess(pageInfo);
    }
}
