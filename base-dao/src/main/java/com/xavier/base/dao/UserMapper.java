package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author NewGr8Player
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
