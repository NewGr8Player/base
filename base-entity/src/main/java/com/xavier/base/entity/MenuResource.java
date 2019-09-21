package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 菜单资源关系表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu_resource")
public class MenuResource extends BaseEntity<String> {

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    private String resourceId;

}
