package com.xavier.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.RoleDao;
import com.xavier.base.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class RoleService extends ServiceImpl<RoleDao, Role> {

    /**
     * 根据idList查询列表
     *
     * @param idList
     * @return
     */
    @Override
    @Cacheable(cacheNames = "roleList")
    public Collection<Role> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    /**
     * 查询分页列表
     *
     * @param rolePage
     * @param role
     * @return
     */
    public IPage<Role> selectRoleListPage(Page<Role> rolePage, Role role) {
        QueryWrapper<Role> entityWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(role.getRoleCode())) {/* role_code */
            entityWrapper.like("role_code", role.getRoleCode());
        }
        if (StringUtils.isNotBlank(role.getRoleName())) {/* role_name */
            entityWrapper.like("role_name", role.getRoleName());
        }
        return (IPage<Role>) baseMapper.selectPage(rolePage, entityWrapper);
    }
}
