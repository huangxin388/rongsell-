package com.bupt.rongsell.controller.backend;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.config.cache.RedisUtil;
import com.bupt.rongsell.entity.Category;
import com.bupt.rongsell.service.CategoryService;
import com.bupt.rongsell.service.UserService;
import com.bupt.rongsell.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
