package com.xavier.common.structure;

import lombok.*;


/**
 * ajax方法的返回结果
 *
 * @author NewGr8Player
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult {

    /**
     * 返回代码
     */
    private String code;

    /**
     * 返回信息
     */
    private Object msg;

    public static AjaxResult sucess(Object message){
        return new AjaxResult("success",message);
    }

    public static AjaxResult fail(Object message){
        return new AjaxResult("fail",message);
    }


}
