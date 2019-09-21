package com.xavier.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 权限-资源
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResourcePerm extends BaseEntity<String> {

    @ApiModelProperty(notes = "请求方式")
    private String method;

    @ApiModelProperty(notes = "路径映射")
    private String mapping;

}
