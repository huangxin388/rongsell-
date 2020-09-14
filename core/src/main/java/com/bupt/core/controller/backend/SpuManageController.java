package com.bupt.core.controller.backend;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.service.SpuService;
import com.bupt.core.vo.SpuAndSkuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huang xin
 * @Date 2020/9/11 16:21
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manage/spu")
public class SpuManageController {

    @Autowired
    private SpuService spuService;

    /**
     * 添加spu同时添加sku
     * @param spuAndSkuVo
     * @return
     */
    @PostMapping("/spusave")
    public ServerResponse<String> productSave(SpuAndSkuVo spuAndSkuVo) {
        return spuService.addSpuAndSkuInfo(spuAndSkuVo);
    }
}
