package com.bupt.core.controller.backend;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.entity.Sku;
import com.bupt.core.service.SkuService;
import com.bupt.core.vo.SkuDetailVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getskulist")
    public ServerResponse<PageInfo> getSkuList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return skuService.getSkuList(pageNum, pageSize);
    }

    @PostMapping("/setsalestatus")
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        return skuService.setSaleStatus(productId, status);
    }

    @PostMapping("/getskubyid")
    public ServerResponse<SkuDetailVo> getSkuById(Integer skuId) {
        return skuService.getSkuDetail(skuId);
    }
}
