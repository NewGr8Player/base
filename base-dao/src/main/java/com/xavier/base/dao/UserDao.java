package com.xavier.base.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
    /**
     * 根据姓名查找用户
     *
     * @param username 用户名
     * @return
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} LIMIT 1")
    User findByUsername(@Param("username") String username);
}
