package com.fish.controller;

import com.fish.entity.Critic;
import com.fish.service.CriticService;
import com.fish.vo.ResultVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/critic")
public class CriticController {
    @Autowired
    CriticService criticService;

    //查找所有评论
    @GetMapping("/findAll")
    public ResultVO<List<Critic>> findAll() {
        return criticService.findAll();
    }

    //增加评论
    @GetMapping("/insert") 
    public ResultVO<Critic> insert(Critic critic) {
        return criticService.insert(critic);
    }

    //删除评论
    @GetMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Integer id) {
        System.out.println("DEBUG");
        return criticService.delete(id);
    }
}
