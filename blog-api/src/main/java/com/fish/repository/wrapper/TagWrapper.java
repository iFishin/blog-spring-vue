package com.fish.repository.wrapper;

import java.util.List;

import com.fish.vo.TagVO;

/**
 * @author fish
 * <p>
 * 2018年1月25日
 */
public interface TagWrapper {

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);
}
