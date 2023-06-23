package com.fish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.entity.Article;
import com.fish.entity.Comment;

/**
 * @author fish
 * <p>
 * 2018年1月25日
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleAndLevelOrderByCreateDateDesc(Article a, String level);


}
