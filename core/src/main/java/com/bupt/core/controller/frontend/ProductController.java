package com.bupt.core.controller.frontend;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.service.ProductService;
import com.bupt.core.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huang xin
 * @Date 2020/3/21 19:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/getproductdetail")
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        return productService.getProductDetail(productId);
    }

    @PostMapping("getproductlist")
    public ServerResponse<PageInfo> getProductList(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize);
    }
}
