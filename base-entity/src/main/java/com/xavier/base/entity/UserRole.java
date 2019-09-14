package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户角色对应关系Bean
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_user_role")
public class UserRole extends BaseEntity<String> {

    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色Id
     */
    @TableField("role_id")
    private String roleId;
}
