package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.service.FileService;
import com.bupt.rongsell.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author huang xin
 * @Date 2020/3/26 10:24
 * @Version 1.0
 */
@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString().replace("-", "") + fileExtensionName;
        logger.info("文件开始上传，原文件名：{}，上传路径：{}，现文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()) {
            // 设置可写权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件已经上传成功

            // 将targetFile上传到文件服务器上
            List<File> fileList = new ArrayList<>();
            fileList.add(targetFile);
            FTPUtil.uploadImage(fileList);

            // 图片上传完成后删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传出现异常：" + e);
            return null;
        }

        return targetFile.getName();
    }

    @Override
    public Boolean deleteImage(String fileName) {
        Boolean result = FTPUtil.deleteImage(fileName);
        return result;
    }
}
