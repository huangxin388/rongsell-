package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.ImageHolder;
import com.bupt.rongsell.service.ImageService;
import com.bupt.rongsell.utils.ImageUtil;
import org.springframework.stereotype.Service;

/**
 * @Author huang xin
 * @Date 2020/3/21 18:42
 * @Version 1.0
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String uploadImage(ImageHolder imageHolder, String dest) {
        //将图片存入服务器并返回相对地址
        String imgAddr = null;
        try {
            imgAddr = ImageUtil.generateThumbnail(imageHolder,dest);
            return imgAddr;
        } catch (Exception e) {
            return "文件上传出现异常";
        }
    }
}
