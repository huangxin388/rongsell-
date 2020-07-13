package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.CategoryMapper;
import com.bupt.rongsell.dao.GroupMapper;
import com.bupt.rongsell.dao.SkuMapper;
import com.bupt.rongsell.dao.SpuMapper;
import com.bupt.rongsell.entity.Category;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.entity.Spu;
import com.bupt.rongsell.entity.SpuExample;
import com.bupt.rongsell.service.CategoryService;
import com.bupt.rongsell.service.SpuService;
import com.bupt.rongsell.utils.PropertyUtil;
import com.bupt.rongsell.vo.CategoryVo;
import com.bupt.rongsell.vo.SkuListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

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

    private SkuListVo assembleSkuListVo(Sku sku) {
        SkuListVo productListVo = new SkuListVo();
        productListVo.setId(sku.getId());
        productListVo.setMainImage(sku.getMainImage());
        productListVo.setTitle(sku.getTitle());
        productListVo.setSubTitle(sku.getSubTitle());
        productListVo.setStatus(sku.getStatus());
        productListVo.setPrice(sku.getPrice());
        productListVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        return productListVo;
    }
}
