package com.xavier.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.PermissionMenu;
import com.xavier.dao.PermissionMenuDao;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限菜单Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class PermissionMenuService extends ServiceImpl<PermissionMenuDao, PermissionMenu> {

    /**
     * 根据permissionIdList批量查找
     *
     * @param permissionIdList
     * @return
     */
    @Cacheable(cacheNames = "permissionMenuList")
    public List<PermissionMenu> findByBatchPermissionIds(List<String> permissionIdList) {
        return baseMapper.findByBatchPermissionIds(permissionIdList);
    }
}
