package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xavier.base.enums.MenuTypeEnum;
import com.xavier.base.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends BaseEntity<String> {

    @TableField(value = "parent_id")
    @ApiModelProperty(notes = "父菜单ID，一级菜单为0")
    @NotNull(groups = Menu.Create.class, message = "父菜单不能为空")
    private String parentId;

    @TableField(value = "menu_name")
    @ApiModelProperty(notes = "菜单名称")
    @NotBlank(groups = Menu.Create.class, message = "菜单名称不能为空")
    private String menuName;

    @TableField(value = "path")
    @ApiModelProperty(notes = "路径")
    private String path;

    @TableField(value = "router")
    @ApiModelProperty(notes = "路由")
    private String router;

    @TableField(value = "menu_type")
    @ApiModelProperty(notes = "类型:1:目录,2:菜单,3:按钮")
    @NotNull(groups = Menu.Create.class, message = "类型不能为空")
    private MenuTypeEnum menuType;

    @TableField(value = "icon")
    @ApiModelProperty(notes = "菜单图标")
    private String icon;

    @TableField(value = "alias")
    @ApiModelProperty(notes = "别名")
    private String alias;

    @TableField(value = "status")
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    @NotNull(groups = {Menu.Create.class, Menu.Status.class}, message = "状态不能为空")
    private StatusEnum status;

    @TableField(value = "resource_ids", exist = false)
    @ApiModelProperty(notes = "关联资源ID")
    private List<String> resourceIds;
}
