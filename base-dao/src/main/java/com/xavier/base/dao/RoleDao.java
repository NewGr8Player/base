package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
}
