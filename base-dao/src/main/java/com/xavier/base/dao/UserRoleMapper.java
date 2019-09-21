package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色关系表 Mapper 接口
 *
 * @author NewGr8Player
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
