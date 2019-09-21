package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xavier.base.common.Regex;
import com.xavier.base.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 系统用户表
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity<String> {

    @TableField(value = "login_name")
    @ApiModelProperty(notes = "登陆名")
    @NotBlank(groups = {Create.class, Update.class}, message = "用户名不能为空")
    private String loginName;

    @TableField(value = "nickname")
    @ApiModelProperty(notes = "昵称")
    @NotBlank(groups = {Create.class, Update.class}, message = "昵称不能为空")
    private String nickname;

    @TableField(value = "email")
    @ApiModelProperty(notes = "邮箱")
    @Email(groups = {Create.class, Update.class}, message = "邮箱格式不正确")
    private String email;

    @TableField(value = "phone")
    @ApiModelProperty(notes = "手机")
    @Pattern(groups = {Create.class, Update.class}, regexp = Regex.PHONE, message = "手机号码格式不正确")
    private String phone;

    @TableField(value = "ip")
    @ApiModelProperty(notes = "IP地址")
    private String ip;

    @TableField(value = "status")
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    @NotNull(groups = Status.class, message = "用户状态不能为空")
    private StatusEnum status;

    @TableField(value = "role_ids", exist = false)
    @ApiModelProperty(notes = "用户角色ID")
    @NotEmpty(groups = {Create.class, Update.class}, message = "用户角色不能为空")
    private List<String> roleIds;

    @TableField(value = "perms", exist = false)
    @ApiModelProperty(notes = "权限路径")
    private List<String> perms;

    @TableField(value = "password")
    private String password;
}
