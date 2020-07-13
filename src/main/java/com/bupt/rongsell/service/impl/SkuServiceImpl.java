package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.SkuMapper;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.entity.SkuExample;
import com.bupt.rongsell.enums.ProductStatusEnum;
import com.bupt.rongsell.service.SkuService;
import com.bupt.rongsell.utils.DatetimeUtil;
import com.bupt.rongsell.utils.PropertyUtil;
import com.bupt.rongsell.vo.ProductDetailVo;
import com.bupt.rongsell.vo.ProductListVo;
import com.bupt.rongsell.vo.SkuDetailVo;
import com.bupt.rongsell.vo.SkuListVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品服务
 * @Author huang xin
 * @Date 2020/6/2 12:41
 * @Version 1.0
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public ServerResponse<List<Sku>> getSkuInfo() {
        List<Sku> skuList = skuMapper.selectByExample(null);
        return ServerResponse.getSuccess(skuList);
    }

    @Override
    public ServerResponse<String> updateOrInsertSku(Sku sku) {
        if(sku != null) {
            // 如果有多个子图，则将第一个子图设置为主图
            String[] subImageArray = sku.getSubImages().split(",");
            if(subImageArray.length > 0) {
                sku.setMainImage(subImageArray[0]);
            }
            sku.setStatus(Const.ProductStatusEnum.ON_SALE.getCode());
            sku.setLastUpdateTime(new Date());
            int resultCount = 0;
            if(sku.getId() == null) {
                // 添加
                sku.setCreateTime(new Date());
                resultCount = skuMapper.insert(sku);
                if(resultCount > 0) {
                    return ServerResponse.getSuccess("添加商品信息成功");
                } else {
                    return ServerResponse.getFailureByMessage("添加商品信息失败");
                }
            } else {
                resultCount = skuMapper.updateByPrimaryKeySelective(sku);
                if(resultCount > 0) {
                    return ServerResponse.getSuccess("更新商品信息成功");
                } else {
                    return ServerResponse.getFailureByMessage("更新商品信息失败");
                }
            }
        } else {
            return ServerResponse.getFailureByMessage("更新或添加商品参数错误");
        }
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer skuId, Integer status) {
        if(skuId == null || status == null) {
            return ServerResponse.getFailureByMessage("设置商品状态参数错误");
        }
        Sku sku = new Sku();
        sku.setId(skuId);
        sku.setStatus(status);
        sku.setLastUpdateTime(new Date());
        int resultCount = skuMapper.updateByPrimaryKeySelective(sku);
        if(resultCount > 0) {
            return ServerResponse.getSuccess("更新商品信息成功");
        }
        return ServerResponse.getFailureByMessage("更新商品信息失败");
    }

    @Override
    public ServerResponse<SkuDetailVo> manageSkuDetail(Integer skuId) {
        if(skuId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Sku sku = skuMapper.selectByPrimaryKey(skuId);
        if(sku == null) {
            return ServerResponse.getFailureByMessage("商品已下架或删除");
        }
        SkuDetailVo skuDetailVo = assembleSkuDetailVo(sku);
        return ServerResponse.getSuccess(skuDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getSkuList(int pageNum, int pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Sku> skuList = skuMapper.selectSkuList();
        List<SkuListVo> skuListVoList = new ArrayList<>();
        for(Sku skuItem : skuList) {
            SkuListVo skuListVo = assembleSkuListVo(skuItem);
            skuListVoList.add(skuListVo);
        }
        PageInfo pageInfo = new PageInfo(skuList);
        pageInfo.setList(skuListVoList);

        return ServerResponse.getSuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> searchSku(String skuTitle, Integer skuId, int pageNum, int pageSize) {
        SkuExample skuExample = new SkuExample();
        SkuExample.Criteria criteria = skuExample.createCriteria();
        if(skuTitle != null) {
            skuTitle = "%" + skuTitle + "%";
            criteria.andTitleLike(skuTitle);
        }
        if(skuId != null) {
            criteria.andIdEqualTo(skuId);
        }
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Sku> skuList = skuMapper.selectByExample(skuExample);
        List<SkuListVo> skuVoList = new ArrayList<>();
        for(Sku skuItem : skuList) {
            SkuListVo skuListVo = assembleSkuListVo(skuItem);
            skuVoList.add(skuListVo);
        }
        PageInfo pageInfo = new PageInfo(skuList);
        pageInfo.setList(skuVoList);

        return ServerResponse.getSuccess(pageInfo);
    }

    @Override
    public ServerResponse<SkuDetailVo> getSkuDetail(Integer skuId) {
        if(skuId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Sku sku = skuMapper.selectByPrimaryKey(skuId);
        if(sku == null) {
            return ServerResponse.getFailureByMessage("商品已下架或删除");
        }
        if(sku.getStatus() != ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.getFailureByMessage("商品已下架或删除");
        }
        SkuDetailVo skuDetailVo = assembleSkuDetailVo(sku);
        return ServerResponse.getSuccess(skuDetailVo);
    }


    private SkuDetailVo assembleSkuDetailVo(Sku sku) {
        SkuDetailVo skuDetailVo = new SkuDetailVo();
        skuDetailVo.setId(sku.getId());
        skuDetailVo.setSubTitle(sku.getSubTitle());
        skuDetailVo.setPrice(sku.getPrice());
        skuDetailVo.setMainImage(sku.getMainImage());
        skuDetailVo.setSubImages(sku.getSubImages());
        skuDetailVo.setDetail(sku.getDetail());
        skuDetailVo.setName(sku.getTitle());
        skuDetailVo.setStatus(sku.getStatus());
        skuDetailVo.setStock(sku.getStock());
        skuDetailVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        skuDetailVo.setCreateTime(DatetimeUtil.dateToStr(sku.getCreateTime()));
        skuDetailVo.setUpdateTime(DatetimeUtil.dateToStr(sku.getLastUpdateTime()));
        return skuDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getSkuByKeyword(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Sku> skuList = skuMapper.customerSelectSku(keyword);
        List<SkuListVo> skuVoList = new ArrayList<>();
        for(Sku skuItem : skuList) {
            SkuListVo skuListVo = assembleSkuListVo(skuItem);
            skuVoList.add(skuListVo);
        }
        PageInfo pageInfo = new PageInfo(skuList);
        pageInfo.setList(skuVoList);
        return ServerResponse.getSuccess(pageInfo);
    }

    private SkuListVo assembleSkuListVo(Sku sku) {
        SkuListVo skuListVo = new SkuListVo();
        skuListVo.setId(sku.getId());
        skuListVo.setMainImage(sku.getMainImage());
        skuListVo.setTitle(sku.getTitle());
        skuListVo.setSubTitle(sku.getSubTitle());
        skuListVo.setStatus(sku.getStatus());
        skuListVo.setPrice(sku.getPrice());
        skuListVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        return skuListVo;
    }

}
