package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 系统用户角色关系表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class UserRole extends BaseEntity<String> {

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

}
