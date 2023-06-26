package com.fish.service;

import com.fish.entity.Critic;
import com.fish.vo.ResultVO;

import java.util.List;

public interface CriticService {
    //查找所有评论
    ResultVO<List<Critic>> findAll();

    //添加评论
    ResultVO<Critic> insert(Critic critic);

    //删除评论
    ResultVO delete(Integer id);
}
