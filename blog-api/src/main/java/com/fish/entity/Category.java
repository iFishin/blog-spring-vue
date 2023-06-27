package com.fish.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fish.common.entity.BaseEntity;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 文章分类
 *
 * @author fish
 * <p>
 */
@Entity
@Table(name = "me_category")
@ApiIgnore
public class Category extends BaseEntity<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 5025313969040054182L;

    @NotBlank
    private String categoryname;

    private String description;

    @NotBlank
    private String avatar;


    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
