package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {
}
