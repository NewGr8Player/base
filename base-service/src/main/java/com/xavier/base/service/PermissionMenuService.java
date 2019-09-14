package com.xavier.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.PermissionMenuDao;
import com.xavier.base.entity.PermissionMenu;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限菜单Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class PermissionMenuService extends ServiceImpl<PermissionMenuDao, PermissionMenu> {

    /**
     * 根据permissionIdList批量查找
     *
     * @param permissionIdList
     * @return
     */
    @Cacheable(cacheNames = "permissionMenuList")
    public List<PermissionMenu> listByPermissionIds(List<String> permissionIdList) {
        return baseMapper.listByPermissionIds(permissionIdList);
    }
}
