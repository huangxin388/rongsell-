package com.bupt.core.service.impl;

import com.bupt.common.constant.Const;
import com.bupt.common.utils.ServerResponse;
import com.bupt.core.dao.CategoryMapper;
import com.bupt.core.dao.ProductMapper;
import com.bupt.core.entity.Category;
import com.bupt.core.entity.Product;
import com.bupt.core.entity.ProductExample;
import com.bupt.core.enums.ProductStatusEnum;
import com.bupt.core.service.CategoryService;
import com.bupt.core.service.ProductService;
import com.bupt.common.utils.DatetimeUtil;
import com.bupt.core.utils.PropertyUtil;
import com.bupt.core.vo.CategoryVo;
import com.bupt.core.vo.ProductDetailVo;
import com.bupt.core.vo.ProductListVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/21 15:41
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ServerResponse<String> updateOrInsertProduct(Product product) {
        if(product != null) {
            // 如果有多个子图，则将第一个子图设置为主图
            String[] subImageArray = product.getSubImages().split(",");
            if(subImageArray.length > 0) {
                product.setMainImage(subImageArray[0]);
            }
            product.setStatus(Const.ProductStatusEnum.ON_SALE.getCode());
            product.setUpdateTime(new Date());
            int resultCount = 0;
            if(product.getId() == null) {
                // 添加
                product.setCreateTime(new Date());
                resultCount = productMapper.insert(product);
                if(resultCount > 0) {
                    return ServerResponse.getSuccess("添加商品信息成功");
                } else {
                    return ServerResponse.getFailureByMessage("添加商品信息失败");
                }
            } else {
                resultCount = productMapper.updateByPrimaryKeySelective(product);
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
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null) {
            return ServerResponse.getFailureByMessage("设置商品状态参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        product.setUpdateTime(new Date());
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if(resultCount > 0) {
            return ServerResponse.getSuccess("更新商品信息成功");
        }
        return ServerResponse.getFailureByMessage("更新商品信息失败");
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if(productId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null) {
            return ServerResponse.getFailureByMessage("产品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.getSuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectProductList();
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);

        return ServerResponse.getSuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        if(productName != null) {
            productName = "%" + productName + "%";
            criteria.andNameLike(productName);
        }
        if(productId != null) {
            criteria.andIdEqualTo(productId);
        }
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectByExample(productExample);
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);

        return ServerResponse.getSuccess(pageInfo);

    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null) {
            return ServerResponse.getFailureByMessage("产品已下架或删除");
        }
        if(product.getStatus() != ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.getFailureByMessage("产品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.getSuccess(productDetailVo);
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setSubTitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        productListVo.setPrice(product.getPrice());
        productListVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));
        return productListVo;
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubTitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null) {
            productDetailVo.setParentCategoryId(0); // 默认为根节点
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DatetimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DatetimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize) {
        if(keyword == null && categoryId == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }

        List<CategoryVo> categoryIdList = new ArrayList<>();

        if(categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && keyword == null) {
                // 没有该分类，并且还没有关键字，这个时候返回一个空的结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVoList = new ArrayList<>();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.getSuccess(pageInfo);
            }
            categoryIdList = categoryService.getRecursiveChildrenCategory(category.getId()).getData();
        }
        if(keyword != null) {
            keyword = "%" + keyword + "%";
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectProductByNameAndCategoryIds("".equals(keyword.trim())?null:keyword, categoryIdList.size() == 0?null:categoryIdList);
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.getSuccess(pageInfo);
    }


}
