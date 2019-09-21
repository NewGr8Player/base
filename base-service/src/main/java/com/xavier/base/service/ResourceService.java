package com.xavier.base.service;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.ResourceMapper;
import com.xavier.base.entity.Resource;
import com.xavier.base.entity.ResourcePerm;
import com.xavier.base.enums.AuthTypeEnum;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ResourceService
 *
 * @author NewGr8Player
 */
@Service
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> {


    public List<String> getUserPerms(String uid) {
        return getUserResourcePerms(uid).stream()
                .map(e -> this.getResourcePermTag(e.getMethod(), e.getMapping()))
                .collect(Collectors.toList());
    }


    public String getResourcePermTag(String method, String mapping) {
        return method + ":" + mapping;
    }


    public Set<ResourcePerm> getUserResourcePerms(String uid) {
        List<ResourcePerm> perms = getPerms(AuthTypeEnum.OPEN, AuthTypeEnum.LOGIN);
        List<ResourcePerm> resourcePerms = baseMapper.getUserResourcePerms(uid);
        List<ResourcePerm> userMenuResourcePerms = getUserMenuResourcePerms(uid);
        perms.addAll(resourcePerms);
        perms.addAll(userMenuResourcePerms);
        return new HashSet<>(perms);
    }


    public List<ResourcePerm> getUserMenuResourcePerms(String uid) {
        return baseMapper.getUserMenuResourcePerms(uid);
    }

    public List<ResourcePerm> getOpenPerms() {
        return getPerms(AuthTypeEnum.OPEN);
    }

    public List<ResourcePerm> getLoginPerms() {
        return getPerms(AuthTypeEnum.LOGIN);
    }

    public List<ResourcePerm> getPerms(AuthTypeEnum... authTypes) {
        return lambdaQuery().select(Resource::getMethod, Resource::getMapping)
                        .in(ArrayUtils.isNotEmpty(authTypes), Resource::getAuthType, authTypes)
                        .list()
                        .stream().map(e -> new ResourcePerm(e.getMethod(), e.getMapping()))
                        .collect(Collectors.toList());
    }

    public List<ResourcePerm> getPerms() {
        return getPerms((AuthTypeEnum[]) null);
    }


    public List<ResourcePerm> getResourcePerms(String method) {
        return lambdaQuery()
                .select(Resource::getMethod, Resource::getMapping)
                .eq(Resource::getMethod, method).list()
                .stream().map(e -> new ResourcePerm(e.getMethod(), e.getMapping()))
                .collect(Collectors.toList());
    }

}
