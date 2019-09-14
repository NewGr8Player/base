package com.xavier.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xavier.bean.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色权限Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermission> {

    /**
     * 根据roleId查找
     *
     * @param roleId
     * @return
     */
    @Select("SELECT * FROM sys_role_permission WHERE role_id=#{roleId}")
    List<RolePermission> findByRoleId(@Param("roleId") String roleId);

    /**
     * 根据roleIdList批量查找
     *
     * @param roleIdList
     * @return
     */
    @Select("<script>"
            + "SELECT * FROM sys_role_permission WHERE role_id IN"
            + " <foreach item='item' index='index' collection='roleIdList' open='(' separator=',' close=')'>"
            + " #{item}"
            + " </foreach>"
            + "</script>")
    List<RolePermission> findByBatchRoleIds(@Param("roleIdList") List<String> roleIdList);


}
