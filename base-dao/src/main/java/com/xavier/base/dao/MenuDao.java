package com.xavier.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xavier.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {

    @Override
    @Select("<script>"
            + "SELECT * FROM sys_menu WHERE id IN"
            + " <foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
            + " #{item}"
            + " </foreach>"
            + " ORDER BY menu_order"
            + "</script>")
    List<Menu> selectBatchIds(@Param("idList") Collection<? extends Serializable> idList);
}
