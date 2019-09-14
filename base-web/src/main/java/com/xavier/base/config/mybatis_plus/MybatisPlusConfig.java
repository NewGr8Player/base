package com.xavier.base.config.mybatis_plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.xavier.base.entity.User;
import groovy.util.logging.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

     /**
     * 自定义前置处理器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new BaseMetaObjectHandler();
    }

    @Slf4j
    static class BaseMetaObjectHandler implements MetaObjectHandler {

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
}
