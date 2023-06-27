package com.fish.service;

import java.util.List;

import com.fish.entity.Category;
import com.fish.vo.CategoryVO;

/**
 * @author fish
 * <p>
 */
public interface CategoryService {

    List<Category> findAll();

    Category getCategoryById(Integer id);

    Integer saveCategory(Category category);

    Integer updateCategory(Category category);

    void deleteCategoryById(Integer id);

    List<CategoryVO> findAllDetail();

    CategoryVO getCategoryDetail(Integer categoryId);

}
