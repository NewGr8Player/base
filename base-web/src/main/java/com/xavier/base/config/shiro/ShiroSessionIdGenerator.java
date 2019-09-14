package com.xavier.base.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.AllArgsConstructor;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

@AllArgsConstructor
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    private static final String SEPARATOR = "_";

    private String prefix;

    @Override
    public Serializable generateId(Session session) {
        return prefix + SEPARATOR + IdWorker.get32UUID();
    }

}
