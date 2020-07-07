package com.bupt.rongsell.controller.backend;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.entity.SpecParam;
import com.bupt.rongsell.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/6/2 11:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage/specparam")
public class SpecParamManageController {

    @Autowired
    private ParamService paramService;

    @PostMapping("/getspecparam")
    public ServerResponse<List<SpecParam>> getSpecParams(Integer groupId) {
        return paramService.getSpuParams(groupId);
    }
}
