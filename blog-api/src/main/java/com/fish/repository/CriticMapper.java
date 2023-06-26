package com.fish.repository;

import com.fish.entity.Critic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CriticMapper {
    //搜索所有评论
    @Select("select * from critic")
    List<Critic> findAll();

    //增加评论
    @Insert("insert into critic(account,c_date,c_comment) values (#{account},#{c_date},#{c_comment})")
    int insert(Critic critic);

    //删除评论
    @Delete("delete from critic where id=#{id}")
    int delete(Integer id);
}
