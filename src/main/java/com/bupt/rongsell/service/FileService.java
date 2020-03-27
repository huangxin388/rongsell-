package com.bupt.rongsell.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author huang xin
 * @Date 2020/3/26 10:24
 * @Version 1.0
 */
public interface FileService {

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    String uploadFile(MultipartFile file, String path);
}
