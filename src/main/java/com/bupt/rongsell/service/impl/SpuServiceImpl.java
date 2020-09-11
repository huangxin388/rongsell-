package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.Const;
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
import com.bupt.rongsell.vo.SpuAndSkuVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public ServerResponse<String> addSpuAndSkuInfo(SpuAndSkuVo spuAndSkuVo) {
        int resultCount = 0;
        if(spuAndSkuVo != null) {
            // 组装产品信息
            Spu spu = new Spu();
            spu.setTitle(spuAndSkuVo.getTitle());
            spu.setSubTitle(spuAndSkuVo.getSubTitle());
            spu.setSpgId(spuAndSkuVo.getSpgId());
            spu.setBrandId(spuAndSkuVo.getBranchId());
            spu.setSaleable(true);
            spu.setValid(true);
            spu.setCreateTime(new Date());
            spu.setLastUpdateTime(new Date());
            resultCount = spuMapper.insert(spu);
            // 添加产品信息成功，继续组装商品信息
            if(resultCount > 0) {
                // TODO 最终版本需要改成添加一个spu并根据信息添加一个至多个sku
                // 组装商品信息
                Sku sku = new Sku();
                sku.setSpuId(spu.getId());
                sku.setTitle(spuAndSkuVo.getTitle());
                sku.setSubTitle(spuAndSkuVo.getSubTitle());
                // 如果有多个子图，则将第一个子图设置为主图
                String[] subImageArray = spuAndSkuVo.getSubImages().split(",");
                if(subImageArray.length > 0) {
                    sku.setMainImage(subImageArray[0]);
                }
                sku.setSubImages(spuAndSkuVo.getSubImages());
                sku.setDetail(spuAndSkuVo.getDetail());
                sku.setPrice(spuAndSkuVo.getPrice());
                sku.setStock(spuAndSkuVo.getStock());
                sku.setParam(spuAndSkuVo.getParam());
                sku.setStatus(Const.SkuStatusEnum.ON_SALE.getCode());
                sku.setCreateTime(new Date());
                sku.setLastUpdateTime(new Date());
                resultCount = skuMapper.insert(sku);
                if(resultCount > 0) {
                    return ServerResponse.getFailureByMessage("添加商品信息成功");
                } else {
                    return ServerResponse.getFailureByMessage("添加商品信息失败");
                }
            } else {
                return ServerResponse.getFailureByMessage("添加产品信息失败");
            }
        } else {
            return ServerResponse.getFailureByMessage("添加产品参数错误");
        }
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
