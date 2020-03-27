package com.bupt.rongsell.utils;

import com.bupt.rongsell.entity.FileHolder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件操作工具类
 * @Author huang xin
 * @Date 2019/11/18 11:25
 * @Version 1.0
 */
public class FileUtil {
    private static String baseContextPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().trim();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random random = new Random();
    public static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 处理图片，并返回相对路径
     */
    public static String generateFile(FileHolder fileHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileHolder.getFileName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relative path:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("real path:" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            FileUtils.copyInputStreamToFile(fileHolder.getFile(),dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
            throw new RuntimeException("文件上传失败：" + e.getMessage());
        }
        return relativeAddr;
    }
    /**
     * 获取输入流的文件扩展名
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，年月日时分秒+随机5位数
     * @return
     */
    public static String getRandomFileName() {
        String nowTimeStr = SIMPLE_DATE_FORMAT.format(new Date());
        int ranNum = random.nextInt(89999) + 10000;
        return nowTimeStr + ranNum;
    }

    /**
     * 创建目标路径的文件夹
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFilePath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFilePath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 删除传过来的目录或文件
     * @param storePath
     */
    public static void deleteFileOrImg(String storePath) {
        File fileOrImg = new File(PathUtil.getImgBasePath() + storePath);
        if(fileOrImg.exists()) {
            if(fileOrImg.isDirectory()) {
                File[] files = fileOrImg.listFiles();
                for(int i = 0;i < files.length;i++) {
                    files[i].delete();
                }
            }
            fileOrImg.delete();
        }
    }
}
