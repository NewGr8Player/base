package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xavier.base.enums.AuthTypeEnum;
import lombok.*;

/**
 * 资源表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_resource")
public class Resource extends BaseEntity<String> {

    /**
     * 资源名称
     */
    @TableField(value = "resource_name")
    private String resourceName;

    /**
     * 路径映射
     */
    @TableField(value = "mapping")
    private String mapping;

    /**
     * 请求方式
     */
    @TableField(value = "method")
    private String method;

    /**
     * 权限认证类型
     */
    @TableField(value = "auth_type")
    private AuthTypeEnum authType;
    /**
     * 权限标识
     */
    @TableField(value = "perm")
    private String perm;

}
