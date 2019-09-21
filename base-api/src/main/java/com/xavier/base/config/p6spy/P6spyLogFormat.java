package com.xavier.base.config.p6spy;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6spy SQL 日志格式化
 *
 * @author NewGr8Player
 */
public class P6spyLogFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        return StringUtils.isNotEmpty(sql)
                ? new StringBuilder(" Execute SQL")
                .append("(").append(elapsed).append("):")
                .append(sql.replaceAll("[\\s]+", StringPool.SPACE))
                .toString()
                : null;
    }
}
