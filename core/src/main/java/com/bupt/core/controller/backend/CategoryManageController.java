package com.bupt.core.controller.backend;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.entity.Category;
import com.bupt.core.service.CategoryService;
import com.bupt.core.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/17 19:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addcategory")
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
                                              @RequestParam(value = "ifParent", defaultValue = "true") Boolean ifParent,
                                              String categoryName) {
        return categoryService.addCategory(parentId, categoryName, ifParent);
    }

    @PostMapping("/changecategoryname")
    public ServerResponse<String> changeCategoryName(Integer categoryId, String categoryName) {
        return categoryService.updateCategoryName(categoryId, categoryName);
    }

    @PostMapping("/getparallelchildrencategory")
    public ServerResponse<List<Category>> getParallelChildrenCategory(@RequestParam(value = "parentId",
            defaultValue = "0") Integer parentId) {
        return categoryService.getParallelChildrenCategory(parentId);
    }

    @PostMapping("/getrecursivelchildrencategory")
    public ServerResponse<List<CategoryVo>> getRecursiveChildrenCategory(@RequestParam(value = "categoryId",
            defaultValue = "0") Integer categoryId) {
        return categoryService.getRecursiveChildrenCategory(categoryId);
    }
    @GetMapping("/getallcategory")
    public ServerResponse<List<CategoryVo>> getAllCategory() {
        return categoryService.getAllCategory();
    }


}
