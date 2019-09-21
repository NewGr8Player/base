package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.Resource;
import com.xavier.base.entity.ResourcePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author NewGr8Player
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 获取用户权限
     *
     * @param uid
     * @return
     */
    @Select("<script>"
            + "SELECT"
            + "     sr.mapping"
            + "     , sr.method"
            + " FROM sys_resource sr"
            + "     LEFT JOIN sys_role_resource srr ON srr.resource_id = sr.id"
            + "     LEFT JOIN sys_user_role sur ON sur.role_id = srr.role_id"
            + "  WHERE"
            + " sur.uid = #{uid}"
            + " AND sr.auth_type = 3"
            + "</script>")
    List<ResourcePerm> getUserResourcePerms(@Param("uid") String uid);

    /**
     * 获取用户菜单资源权限
     *
     * @param uid
     * @return
     */
    @Select("<script>"
            + "SELECT" +
            "   sr.mapping" +
            "   ,sr.method" +
            " FROM sys_resource sr" +
            " LEFT JOIN sys_menu_resource smr ON smr.resource_id = sr.id" +
            " LEFT JOIN sys_role_menu srm ON smr.menu_id = srm.menu_id" +
            " LEFT JOIN sys_user_role sur ON sur.role_id = srm.role_id" +
            " WHERE" +
            " sur.uid = #{uid}"
            + "</script>")
    List<ResourcePerm> getUserMenuResourcePerms(@Param("uid") String uid);
}
