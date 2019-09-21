package com.xavier.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 密码
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Password extends BaseEntity<String> {

    @ApiModelProperty(notes = "原密码")
    @NotBlank(message = "原密码不能为空", groups = Update.class)
    private String oldPassword;
    @ApiModelProperty(notes = "新密码")
    @NotBlank(message = "新密码不能为空", groups = Update.class)
    private String newPassword;
}
