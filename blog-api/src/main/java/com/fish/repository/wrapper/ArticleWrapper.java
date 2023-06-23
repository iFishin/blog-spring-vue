package com.fish.repository.wrapper;

import com.fish.entity.Article;
import com.fish.vo.ArticleVo;
import com.fish.vo.PageVo;

import java.util.List;

public interface ArticleWrapper {
    List<Article> listArticles(PageVo page);

    List<Article> listArticles(ArticleVo article, PageVo page);

    List<ArticleVo> listArchives();

}
