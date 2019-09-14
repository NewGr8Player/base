package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 权限角色对应关系Bean
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_role_permission")
public class RolePermission extends BaseEntity<String> {

    /**
     * 角色Id
     */
    @TableField("role_id")
    private String roleId;
    /**
     * 权限Id
     */
    @TableField("permission_id")
    private String permissionId;
}
