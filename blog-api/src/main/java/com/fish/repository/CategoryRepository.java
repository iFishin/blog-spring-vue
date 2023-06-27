package com.fish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.entity.Category;
import com.fish.repository.wrapper.CategoryWrapper;

/**
 * @author fish
 * <p>
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryWrapper {

	/*@Query(value="select c.*, count(a.category_id) as articles from me_category c "
			+ "left join me_article a on a.category_id = c.id group by a.category_id",nativeQuery=true)
	List<Category> findAllDetail();*/

}
