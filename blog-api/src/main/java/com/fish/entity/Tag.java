package com.fish.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fish.common.entity.BaseEntity;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 文章标签
 *
 * @author fish
 * <p>
 * 2018年1月23日
 */
@Entity
@Table(name = "me_tag")
@ApiIgnore
public class Tag extends BaseEntity<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 5025313969040054182L;

    @NotBlank
    private String tagname;

    @NotBlank
    private String avatar;


    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
