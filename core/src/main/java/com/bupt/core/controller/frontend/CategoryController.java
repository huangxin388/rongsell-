package com.bupt.core.controller.frontend;

import com.bupt.common.utils.ServerResponse;
import com.bupt.core.entity.Category;
import com.bupt.core.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/5/31 18:02
 * @Version 1.0
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/getparallelchildrencategory")
    public ServerResponse<List<Category>> getParallelChildrenCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        return categoryService.getParallelChildrenCategory(parentId);
    }
}
