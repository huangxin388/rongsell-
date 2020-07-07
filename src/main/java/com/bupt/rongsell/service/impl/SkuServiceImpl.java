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
    public ServerResponse<ProductDetailVo> manageSkuDetail(Integer skuId) {
        if(skuId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Sku sku = skuMapper.selectByPrimaryKey(skuId);
        if(sku == null) {
            return ServerResponse.getFailureByMessage("商品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleSkuDetailVo(sku);
        return ServerResponse.getSuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getSkuList(int pageNum, int pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Sku> skuList = skuMapper.selectSkuList();
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Sku skuItem : skuList) {
            ProductListVo productListVo = assembleSkuListVo(skuItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(skuList);
        pageInfo.setList(productListVoList);

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
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Sku skuItem : skuList) {
            ProductListVo productListVo = assembleSkuListVo(skuItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(skuList);
        pageInfo.setList(productListVoList);

        return ServerResponse.getSuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProductDetailVo> getSkuDetail(Integer skuId) {
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
        ProductDetailVo productDetailVo = assembleProductDetailVo(sku);
        return ServerResponse.getSuccess(productDetailVo);
    }

    private ProductDetailVo assembleSkuDetailVo(Sku sku) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(sku.getId());
        productDetailVo.setSubTitle(sku.getSubTitle());
        productDetailVo.setPrice(sku.getPrice());
        productDetailVo.setMainImage(sku.getMainImage());
        productDetailVo.setSubImages(sku.getSubImages());
        productDetailVo.setDetail(sku.getDetail());
        productDetailVo.setName(sku.getTitle());
        productDetailVo.setStatus(sku.getStatus());
        productDetailVo.setStock(sku.getStock());
        productDetailVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        productDetailVo.setCreateTime(DatetimeUtil.dateToStr(sku.getCreateTime()));
        productDetailVo.setUpdateTime(DatetimeUtil.dateToStr(sku.getLastUpdateTime()));
        return productDetailVo;
    }

    private ProductListVo assembleSkuListVo(Sku sku) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(sku.getId());
        productListVo.setMainImage(sku.getMainImage());
        productListVo.setName(sku.getTitle());
        productListVo.setSubTitle(sku.getSubTitle());
        productListVo.setStatus(sku.getStatus());
        productListVo.setPrice(sku.getPrice());
        productListVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        return productListVo;
    }
    private ProductDetailVo assembleProductDetailVo(Sku sku) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(sku.getId());
        productDetailVo.setSubTitle(sku.getSubTitle());
        productDetailVo.setPrice(sku.getPrice());
        productDetailVo.setMainImage(sku.getMainImage());
        productDetailVo.setSubImages(sku.getSubImages());
        productDetailVo.setDetail(sku.getDetail());
        productDetailVo.setName(sku.getTitle());
        productDetailVo.setStatus(sku.getStatus());
        productDetailVo.setStock(sku.getStock());
        productDetailVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        productDetailVo.setCreateTime(DatetimeUtil.dateToStr(sku.getCreateTime()));
        productDetailVo.setUpdateTime(DatetimeUtil.dateToStr(sku.getLastUpdateTime()));
        return productDetailVo;
    }

}
