package com.xavier.base.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
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
