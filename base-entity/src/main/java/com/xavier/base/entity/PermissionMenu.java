package com.xavier.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xavier.bean.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 权限Bean
 *
 * @author NewGr8Player
 */
@TableName(value = "sys_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionMenu extends BaseEntity {

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
