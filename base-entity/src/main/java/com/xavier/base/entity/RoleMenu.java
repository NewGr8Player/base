package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

}
