package com.bupt.rongsell.service.impl;

import com.bupt.rongsell.common.ServerResponse;
import com.bupt.rongsell.dao.CategoryMapper;
import com.bupt.rongsell.entity.Category;
import com.bupt.rongsell.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author huang xin
 * @Date 2020/3/17 20:07
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        if(parentId == null || categoryName == null || "".equals(categoryName)) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        int resultCount = categoryMapper.insert(category);
        if(resultCount > 0) {
            return ServerResponse.getSuccessByMessage("品类添加成功");
        } else {
            return ServerResponse.getSuccessByMessage("品类添加失败");
        }
    }

    @Override
    public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName) {
        if(categoryId == null || categoryName == null || "".equals(categoryName)) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        category.setUpdateTime(new Date());
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount > 0) {
            return ServerResponse.getSuccessByMessage("品类名称修改成功");
        } else {
            return ServerResponse.getSuccessByMessage("品类名称修改失败");
        }
    }

    @Override
    public ServerResponse<List<Category>> getParallelChildrenCategory(Integer parentId) {
        List<Category> categoryList = categoryMapper.selectParallelChildrenCategory(parentId);
        if(categoryList.size() == 0) {
            logger.error("未找到该分类下的子分类");
        }
        return ServerResponse.getSuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getRecursiveChildrenCategory(Integer categoryId) {
        Set<Category> categorySet = new HashSet<>();
        recursiveCategory(categorySet, categoryId);
        List<Integer> categoryIdList = new ArrayList<>();
        if(categoryId != null) {
            for(Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.getSuccess(categoryIdList);
    }

    /**
     * 递归获取子节点
     * @param categorySet
     * @param categoryId
     * @return
     */
    Set<Category> recursiveCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectParallelChildrenCategory(categoryId);
        for(Category categoryItem : categoryList) {
            recursiveCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
