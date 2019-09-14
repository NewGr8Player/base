package com.xavier.service;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.RolePermission;
import com.xavier.dao.RolePermissionDao;
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
@Transactional(readOnly = true)
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
    public List<RolePermission> findByBatchRoleIds(List<String> roleIdList) {
        return baseMapper.findByBatchRoleIds(roleIdList);
    }
}
