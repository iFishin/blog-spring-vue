package com.fish.service.impl;

import com.fish.entity.Critic;
import com.fish.repository.CriticMapper;
import com.fish.service.CriticService;
import com.fish.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriticServiceImpl implements CriticService {
    @Autowired
    CriticMapper criticMapper;
    @Override
    public ResultVO<List<Critic>> findAll() {
        List<Critic> critics= criticMapper.findAll();
        return ResultVO.ok("操作成功",critics);
    }

    @Override
    public ResultVO<Critic> insert(Critic critic) {
        criticMapper.insert(critic);
        return ResultVO.ok("操作成功");
    }

    @Override
    public ResultVO delete(Integer id) {
        criticMapper.delete(id);
        return ResultVO.ok("操作成功");
    }
}
