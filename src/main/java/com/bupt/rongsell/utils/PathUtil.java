package com.bupt.rongsell.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Author huang xin
 * @Date 2019/11/18 11:25
 * @Version 1.0
 */
@Component
public class PathUtil {
    private static final String WINDOWS_SYSTEM = "win";
    private static int CUT_LENGTH = 3;
    /**
     * 由于静态变量无法从yml中注入，所以这里用几个非静态变量来从yml中注入属性，然后赋值给静态变量
     */
    /**
     * 服务部署在window系统上时图片文件存储的基本路径
     */
    @Value("${staticPath.win.imgBasePath}")
    public String windowsImgBasePath;
    /**
     * 服务部署在window系统上时文件存储的基本路径
     */
    @Value("${staticPath.win.fileBasePath}")
    public String windowsFileBasePath;
    /**
     * 服务部署在linux系统上时图片文件存储的基本路径
     */
    @Value("${staticPath.lin.imgBasePath}")
    public String linuxImgBasePath;
    /**
     * 服务部署在linux系统上时文件存储的基本路径
     */
    @Value("${staticPath.lin.fileBasePath}")
    public String linuxFileBasePath;

    public static String imgBasePath;
    public static String fileBasePath;

    /**
     * 先把yml值读取到普通变量中再转到静态属变量
     * @PostConstruct 修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次;
     */
    @PostConstruct
    public void getApiToken() {
        String os = System.getProperty("os.name");
        //如果运行在windows系统中
        if(os.substring(0,CUT_LENGTH).toLowerCase().equals(WINDOWS_SYSTEM)) {
            imgBasePath = this.windowsImgBasePath;
            fileBasePath = this.windowsFileBasePath;
        } else {
            //如果运行在linux系统中
            imgBasePath = this.linuxImgBasePath;
            fileBasePath = this.linuxFileBasePath;
        }
    }

    public static String getImgBasePath() {
        return imgBasePath;
    }

    public static String getFileBasePath() {
        return fileBasePath;
    }

    public static String getHeadLineImgPath() {
        String imgPath = File.separator + "upload" + File.separator + "item" +File.separator +  "headLine" + File.separator;
        return imgPath;
    }

    public static String getUserImagePath() {
        String imgPath = File.separator + "upload" + File.separator + "item" +File.separator +  "userImage" + File.separator;
        return imgPath;
    }

    public static String getUserFilePath() {
        String imgPath = File.separator + "upload" + File.separator + "item" +File.separator +  "userFile" + File.separator;
        return imgPath;
    }
}
