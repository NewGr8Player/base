package com.xavier.base.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    /**
     * 返回消息
     */
    private String msg = "";

    /**
     * 状态
     */
    private int status = 0;

}
