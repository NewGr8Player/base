package com.xavier.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.Permission;
import com.xavier.dao.PermissionDao;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class PermissionService extends ServiceImpl<PermissionDao, Permission> {

    public Permission selectById(String id) {
        return baseMapper.selectById(id);
    }

    @Cacheable(cacheNames = "permissionList")
    public List<Permission> selectBatchIds(List<String> idList) {
        return super.selectBatchIds(idList);
    }
}
