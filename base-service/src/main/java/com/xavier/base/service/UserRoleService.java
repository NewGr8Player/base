package com.xavier.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.UserRoleDao;
import com.xavier.base.entity.UserRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.Collection;
import java.util.List;

/**
 * 用户权限Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class UserRoleService extends ServiceImpl<UserRoleDao, UserRole> {

    /**
     * 根据用户Id查找角色
     *
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "userRoleList")
    public List<UserRole> findByUserId(String userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据idList批量查找
     *
     * @param idList 主键ID列表
     * @return
     */
    @Cacheable(cacheNames = "userRoleList")
    public Collection<UserRole> selectBatchIds(List<String> idList) {
        return super.listByIds(idList);
    }
}
