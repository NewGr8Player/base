package com.xavier.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.Role;
import com.xavier.common.page.Page;
import com.xavier.dao.RoleDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends ServiceImpl<RoleDao, Role> {

    /**
     * 根据idList查询列表
     *
     * @param idList
     * @return
     */
    @Cacheable(cacheNames = "roleList")
    public List<Role> selectBatchIds(List<String> idList) {
        return super.selectBatchIds(idList);
    }

    /**
     * 查询分页列表
     *
     * @param rolePage
     * @param role
     * @return
     */
    public Page<Role> selectRoleListPage(Page<Role> rolePage, Role role) {
        EntityWrapper entityWrapper = new EntityWrapper();
        if (StringUtils.isNotBlank(role.getRoleCode())) {/* role_code */
            entityWrapper.like("role_code", role.getRoleCode());
        }
        if (StringUtils.isNotBlank(role.getRoleName())) {/* role_name */
            entityWrapper.like("role_name", role.getRoleName());
        }
        return (Page<Role>) rolePage.setRecords(baseMapper.selectPage(rolePage, entityWrapper));
    }
}
