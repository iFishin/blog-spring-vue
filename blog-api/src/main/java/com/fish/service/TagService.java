package com.fish.service;

import java.util.List;

import com.fish.entity.Tag;
import com.fish.vo.TagVO;

/**
 * @author fish
 * <p>
 */
public interface TagService {

    List<Tag> findAll();

    Tag getTagById(Integer id);

    Integer saveTag(Tag tag);

    Integer updateTag(Tag tag);

    void deleteTagById(Integer id);

    List<Tag> listHotTags(int limit);

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);

}
