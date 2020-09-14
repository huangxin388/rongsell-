package com.bupt.core.utils;

import com.bupt.core.common.Const;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/26 11:11
 * @Version 1.0
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertyUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertyUtil.getProperty("ftp.user");
    private static String ftpPass = PropertyUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    public static boolean uploadImage(List<File> fileList) {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile(Const.FTP_IMAGE_DIRECTORY, fileList);
        logger.info("文件上传完毕，上传结果为{}", result);
        return result;
    }

    public static boolean deleteImage(String fileName) {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.deleteFile(Const.FTP_IMAGE_DIRECTORY, fileName);
        logger.info("文件删除完毕，删除结果为{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) {
        boolean uploaded = true;
        FileInputStream fis = null;
        // 连接FTP服务器
        if(connectServer(ip, port, user, pass)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 二进制防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 打开本地被动模式
                ftpClient.enterLocalPassiveMode();
                for(File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    uploaded = ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                uploaded = false;
            } finally {
                try {
                    fis.close();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uploaded;
    }

    private boolean deleteFile(String remotePath, String fileName) {
        boolean uploaded = true;
        // 连接FTP服务器
        if(connectServer(ip, port, user, pass)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 二进制防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 打开本地被动模式
                ftpClient.enterLocalPassiveMode();
                String curDirectory = ftpClient.printWorkingDirectory();
                logger.info("当前目录为：" + curDirectory);
                uploaded = ftpClient.deleteFile(new String(fileName.getBytes("UTF-8")));
            } catch (IOException e) {
                logger.error("删除文件异常", e);
                uploaded = false;
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String pass) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pass);
        } catch (IOException e) {
            logger.error("ftp服务器连接异常", e);
        }
        return isSuccess;
    }





    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
