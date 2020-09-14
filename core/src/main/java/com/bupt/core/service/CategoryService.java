package com.bupt.core.service;

import com.bupt.core.common.ServerResponse;
import com.bupt.core.entity.Category;
import com.bupt.core.vo.CategoryVo;

import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/17 20:07
 * @Version 1.0
 */
public interface CategoryService {

    /**
     * 添加分类
     * @param parentId
     * @param categoryName
     * @return
     */
    ServerResponse<String> addCategory(Integer parentId, String categoryName, Boolean ifParent);

    /**
     * 更新品类名字
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取平级的子节点
     * @param parentId
     * @return
     */
    ServerResponse<List<Category>> getParallelChildrenCategory(Integer parentId);

    /**
     * 获取当前分类，递归获取当前分类下的所有子分类
     * @param categoryId
     * @return
     */
    ServerResponse<List<CategoryVo>> getRecursiveChildrenCategory(Integer categoryId);

    /**
     * 获取所有分类信息
     * @return
     */
    ServerResponse<List<CategoryVo>> getAllCategory();

}
