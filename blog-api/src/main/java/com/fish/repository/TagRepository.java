package com.fish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fish.entity.Tag;
import com.fish.repository.wrapper.TagWrapper;

/**
 * @author fish
 * <p>
 */
public interface TagRepository extends JpaRepository<Tag, Integer>, TagWrapper {

    @Query(value = "select t.*,count(at.tag_id) as count from me_article_tag at "
            + "right join me_tag t on t.id = at.tag_id "
            + "group by t.id order by count(at.tag_id) desc limit :limit", nativeQuery = true)
    List<Tag> listHotTagsByArticleUse(@Param("limit") int limit);

}
