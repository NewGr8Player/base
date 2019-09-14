package com.xavier.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xavier.bean.PermissionMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限菜单关系表
 *
 * @author NewGr8Player
 */
@Mapper
public interface PermissionMenuDao extends BaseMapper<PermissionMenu> {

    /**
     * 根据permissionIdList批量查找
     *
     * @param permissionIdList
     * @return
     */
    @Select("<script>"
            + "SELECT * FROM sys_permission_menu WHERE permission_id IN"
            + " <foreach item='item' index='index' collection='permissionIdList' open='(' separator=',' close=')'>"
            + " #{item}"
            + " </foreach>"
            + "</script>")
    List<PermissionMenu> findByBatchPermissionIds(@Param("permissionIdList") List<String> permissionIdList);
}
