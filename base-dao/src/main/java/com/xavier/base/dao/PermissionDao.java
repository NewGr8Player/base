package com.xavier.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xavier.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 权限Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface PermissionDao extends BaseMapper<Permission> {
}
