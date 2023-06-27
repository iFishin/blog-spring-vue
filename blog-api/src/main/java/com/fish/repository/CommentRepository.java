package com.fish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.entity.Article;
import com.fish.entity.Comment;

/**
 * @author fish
 * <p>
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleAndLevelOrderByCreateDateDesc(Article a, String level);


}
