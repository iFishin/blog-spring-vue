package com.fish.repository.wrapper;

import java.util.List;

import com.fish.vo.CategoryVO;

/**
 * @author fish
 * <p>
 * 2018年1月25日
 */
public interface CategoryWrapper {

    List<CategoryVO> findAllDetail();

    CategoryVO getCategoryDetail(Integer categoryId);
}
