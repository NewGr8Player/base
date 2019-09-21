package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity<String> {

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(notes = "角色名称")
    @NotBlank(groups = {Create.class, Update.class}, message = "角色名称不能为空")
    private String roleName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(notes = "备注")
    private String remark;

    @TableField(value = "menu_ids", exist = false)
    @ApiModelProperty(notes = "菜单ID集合")
    private List<String> menuIds;
}
