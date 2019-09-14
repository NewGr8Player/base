package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface PermissionDao extends BaseMapper<Permission> {
}
