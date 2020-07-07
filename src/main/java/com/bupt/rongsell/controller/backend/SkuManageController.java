package com.bupt.rongsell.controller.backend;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.Sku;
import com.bupt.rongsell.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author huang xin
 * @Date 2020/6/3 17:30
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manage/sku")
public class SkuManageController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/skusave")
    public ServerResponse<String> productSave(Sku sku) {
        log.info(sku.toString());
        return skuService.updateOrInsertSku(sku);
    }
}
