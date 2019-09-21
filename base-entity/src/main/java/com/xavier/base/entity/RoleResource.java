package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 * 角色资源关系表
 * </p>
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_resource")
public class RoleResource extends BaseEntity<String> {

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "resource_id")
    private String resourceId;

}
