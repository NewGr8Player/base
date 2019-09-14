package com.xavier.base.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.RolePermissionDao;
import com.xavier.base.entity.RolePermission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class RolePermissionService extends ServiceImpl<RolePermissionDao, RolePermission> {

    /**
     * 根据roleId查找
     *
     * @param roleId
     * @return
     */
    public List<RolePermission> findByRoleId(String roleId) {
        return baseMapper.findByRoleId(roleId);
    }

    /**
     * 根据roleIdList批量查找
     *
     * @param roleIdList
     * @return
     */
    @Cacheable(cacheNames = "rolePermissionList")
    public List<RolePermission> listByRoleIds(List<String> roleIdList) {
        return baseMapper.findByRoleIds(roleIdList);
    }
}
