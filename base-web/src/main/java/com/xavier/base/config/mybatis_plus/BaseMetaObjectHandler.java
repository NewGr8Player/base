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
    private static final String FIELD_DELETED = "deleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getId();
        Date nowDate = new Date();

        setFieldValByName(FIELD_CREATE_BY, userId, metaObject);
        setFieldValByName(FIELD_CREATE_DATE_TIME, nowDate, metaObject);
        setFieldValByName(FIELD_UPDATE_BY, userId, metaObject);
        setFieldValByName(FIELD_UPDATE_DATE_TIME, nowDate, metaObject);
        setFieldValByName(FIELD_DELETED, 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userId = user.getId();
        Date nowDate = new Date();

        setFieldValByName(FIELD_UPDATE_BY, userId, metaObject);
        setFieldValByName(FIELD_UPDATE_DATE_TIME, nowDate, metaObject);
        setFieldValByName(FIELD_DELETED, 0, metaObject);
    }
}
