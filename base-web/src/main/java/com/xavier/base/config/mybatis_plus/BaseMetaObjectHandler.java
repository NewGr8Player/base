package com.xavier.base.config.mybatis_plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xavier.base.entity.User;
import groovy.util.logging.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    private static final String FIELD_CREATE_BY = "createBy";
    private static final String FIELD_CREATE_DATE_TIME = "createDateTime";
    private static final String FIELD_UPDATE_BY = "updateBy";
    private static final String FIELD_UPDATE_DATE_TIME = "updateDateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        metaObject.setValue(FIELD_CREATE_BY, user.getId());
        metaObject.setValue(FIELD_CREATE_DATE_TIME, new Date());
        metaObject.setValue(FIELD_UPDATE_BY, user.getId());
        metaObject.setValue(FIELD_UPDATE_DATE_TIME, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        metaObject.setValue(FIELD_UPDATE_BY, user.getId());
        metaObject.setValue(FIELD_UPDATE_DATE_TIME, new Date());
    }
}
