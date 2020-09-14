package com.bupt.core.controller.backend;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.entity.Product;
import com.bupt.core.service.FileService;
import com.bupt.core.service.ProductService;
import com.bupt.core.utils.PropertyUtil;
import com.bupt.core.vo.ProductDetailVo;
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
    private FileService fileService;



    @PostMapping("/productsave")
    public ServerResponse<String> productSave(Product product) {
        return productService.updateOrInsertProduct(product);
    }

    @PostMapping("/setsalestatus")
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        return productService.setSaleStatus(productId, status);
    }

    @PostMapping("/getproductdetail")
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        return productService.manageProductDetail(productId);
    }

    @PostMapping("/getproductlist")
    public ServerResponse<PageInfo> getProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return productService.getProductList(pageNum, pageSize);
    }

    @PostMapping("/searchproduct")
    public ServerResponse<PageInfo> searchProduct(@RequestParam(value = "productName", required = false) String productName,
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
    public ServerResponse deleteImage(String fileName) {
        Boolean deleteResult = fileService.deleteImage(fileName);
        if(deleteResult) {
            return ServerResponse.getSuccess();
        }
        return ServerResponse.getFailure();
    }
}
