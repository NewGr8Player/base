package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 权限Bean
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "sys_permission")
public class PermissionMenu extends BaseEntity<String> {

    /**
     * 权限Id
     */
    @TableField("permission_id")
    private String permissionId;

    /**
     * 菜单
     */
    @TableField("menu_id")
    private String menuId;
}
