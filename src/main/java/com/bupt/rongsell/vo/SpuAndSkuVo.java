package com.bupt.rongsell.vo;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author huang xin
 * @Date 2020/9/11 16:31
 * @Version 1.0
 */
public class SpuAndSkuVo {

    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品副标题
     */
    private String subTitle;
    /**
     * 商品主图
     */
    private String mainImage;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品库存
     */
    private Integer stock;
    /**
     * 商品参数
     */
    private JSONObject param;
    /**
     * 商品上架状态。1：在售  2：下架   3：删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最新修改时间
     */
    private Date lastUpdateTime;
    /**
     * 商品子图
     */
    private String subImages;
    /**
     * 商品详情，富文本中的内容
     */
    private String detail;
    /**
     * 商品所属后台分类
     */
    private Integer spgId;
    /**
     * 商品品牌
     */
    private Integer branchId;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getSpgId() {
        return spgId;
    }

    public void setSpgId(Integer spgId) {
        this.spgId = spgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "SpuAndSkuVo{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", param=" + param +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", subImages='" + subImages + '\'' +
                ", detail='" + detail + '\'' +
                ", spgId=" + spgId +
                ", branchId=" + branchId +
                '}';
    }
}
