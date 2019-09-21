package com.xavier.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 下拉框DTO
 * </p>
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Combo extends BaseEntity<String> {

    @ApiModelProperty(notes = "名称")
    private String name;

}
