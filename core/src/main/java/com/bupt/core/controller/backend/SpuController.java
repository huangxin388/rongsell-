package com.bupt.core.controller.backend;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.entity.Spu;
import com.bupt.core.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/1 19:52
 * @Version 1.0
 */
@RestController
@RequestMapping("manage/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @PostMapping("/getspus")
    public ServerResponse<List<Spu>> getSpus(Spu spu) {
        return spuService.getSpusBySpgId(spu);
    }
}
