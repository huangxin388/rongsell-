package com.bupt.rongsell.controller.frontend;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
