package com.xavier.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xavier.base.entity.MenuTree;
import com.xavier.base.entity.Menu;
import com.xavier.base.enums.MenuTypeEnum;
import com.xavier.base.enums.StatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 菜单表 Mapper 接口
 *
 * @author NewGr8Player
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取用户权限菜单
     *
     * @param uid
     * @param statusType
     * @param menuTypes
     * @return
     */
    @Select("<script>"
            + "SELECT"
            + "   sm.id"
            + "   ,sm.parent_id"
            + "   ,sm.menu_name"
            + "   ,sm.menu_type"
            + "   ,sm.router"
            + "   ,sm.alias"
            + "   ,sm.icon"
            + "   ,sm.path"
            + " FROM sys_menu sm"
            + " LEFT JOIN sys_role_menu srm ON sm.id = srm.menu_id"
            + " LEFT JOIN sys_user_role sur ON sur.role_id = srm.role_id"
            + " LEFT JOIN sys_user su ON su.id = sur.uid"
            + " WHERE"
            + " su.id = #{uid}"
            + "<if test=\"statusType != null\">"
            + " and sm.status = #{statusType}"
            + "</if>"
            + "<if test=\"menuTypes != null and menuTypes.size() > 0\">"
            + " and sm.menu_type in"
            + "<foreach collection=\"menuTypes\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">"
            + "    #{item}"
            + "</foreach>"
            + "</if>"
            + "</script>")
    List<MenuTree> getUserPermMenus(
            @Param("uid") String uid
            , @Param("statusType") StatusEnum statusType
            , @Param("menuTypes") List<MenuTypeEnum> menuTypes
    );
}
