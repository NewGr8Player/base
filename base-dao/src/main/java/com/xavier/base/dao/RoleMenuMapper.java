package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关系表 Mapper 接口
 *
 * @author NewGr8Player
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
