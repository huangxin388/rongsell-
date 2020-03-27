package com.bupt.rongsell.service;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.ImageHolder;

/**
 * @Author huang xin
 * @Date 2020/3/21 18:42
 * @Version 1.0
 */
public interface ImageService {

    /**
     * 上传图片
     * @param imageHolder
     * @param dest
     * @return
     */
    String uploadImage(ImageHolder imageHolder, String dest);

}
