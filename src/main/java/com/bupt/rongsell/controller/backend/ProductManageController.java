package com.bupt.rongsell.controller.backend;

import com.bupt.rongsell.common.Const;
import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.entity.Product;
import com.bupt.rongsell.entity.User;
import com.bupt.rongsell.enums.ResponseCode;
import com.bupt.rongsell.service.FileService;
import com.bupt.rongsell.service.ProductService;
import com.bupt.rongsell.service.UserService;
import com.bupt.rongsell.utils.CookieUtil;
import com.bupt.rongsell.utils.JsonUtil;
import com.bupt.rongsell.utils.PropertyUtil;
import com.bupt.rongsell.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huang xin
 * @Date 2020/3/19 22:15
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private RedisUtil redisUtil;



    @PostMapping("/productsave")
    public ServerResponse<String> productSave(HttpServletRequest request, Product product) {
        return productService.updateOrInsertProduct(product);
    }

    @PostMapping("/setsalestatus")
    public ServerResponse<String> setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
        return productService.setSaleStatus(productId, status);
    }

    @PostMapping("/getproductdetail")
    public ServerResponse<ProductDetailVo> getProductDetail(HttpServletRequest request, Integer productId) {
        return productService.manageProductDetail(productId);
    }

    @PostMapping("/getproductlist")
    public ServerResponse<PageInfo> getProductList(HttpServletRequest request,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return productService.getProductList(pageNum, pageSize);
    }

    @PostMapping("/searchproduct")
    public ServerResponse<PageInfo> searchProduct(HttpServletRequest request,
                                                   @RequestParam(value = "productName", required = false) String productName,
                                                   @RequestParam(value = "productId", required = false) Integer productId,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return productService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @PostMapping("/uploadimage")
    public ServerResponse<Map<String, String>> uploadImage(HttpServletRequest request,
                                                @RequestParam("imageFile") MultipartFile imageFile) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        Map<String, String> modelMap = new HashMap();
        String targetFileName = fileService.uploadImage(imageFile, path);
        String url = PropertyUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        modelMap.put("uri", targetFileName);
        modelMap.put("url", url);
        return ServerResponse.getSuccess(modelMap);
    }

    @PostMapping("/deleteimage")
    public ServerResponse deleteImage(HttpServletRequest request, String fileName) {
        Boolean deleteResult = fileService.deleteImage(fileName);
        if(deleteResult) {
            return ServerResponse.getSuccess();
        }
        return ServerResponse.getFailure();
    }
}
