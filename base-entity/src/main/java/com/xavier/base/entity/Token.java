package com.xavier.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Token
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseEntity<String> {

    @ApiModelProperty(notes = "账号id")
    private String uid;
    @ApiModelProperty(notes = "token")
    private String token;

}
