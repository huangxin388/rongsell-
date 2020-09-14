package com.bupt.core.controller.frontend;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.entity.Sku;
import com.bupt.core.service.SkuService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/2 12:44
 * @Version 1.0
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/getskuinfo")
    public ServerResponse<List<Sku>> getSkuInfo() {
        return skuService.getSkuInfo();
    }

    @PostMapping("getskulist")
    public ServerResponse<PageInfo> getSkuList(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return skuService.getSkuByKeyword(keyword, pageNum, pageSize);
    }

    @PostMapping("/testresolve")
    public ServerResponse<List<Sku>> testResolve() {
        return skuService.testResolve();
    }
}
