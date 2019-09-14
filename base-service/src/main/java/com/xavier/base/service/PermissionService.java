package com.xavier.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.PermissionDao;
import com.xavier.base.entity.Permission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 权限Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class PermissionService extends ServiceImpl<PermissionDao, Permission> {

    public Permission selectById(String id) {
        return baseMapper.selectById(id);
    }

    @Cacheable(cacheNames = "permissionList")
    public Collection<Permission> listByIds(List<String> idList) {
        return super.listByIds(idList);
    }
}
