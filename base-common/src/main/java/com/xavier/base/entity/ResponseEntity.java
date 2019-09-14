package com.xavier.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseEntity<T> {

    /* http 状态码 */
    private int code;

    /* 返回信息 */
    private String msg;

    /* 返回的数据 */
    private T data;
}
