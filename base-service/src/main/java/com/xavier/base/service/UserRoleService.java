package com.xavier.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.UserRole;
import com.xavier.dao.UserRoleDao;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户权限Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class UserRoleService extends ServiceImpl<UserRoleDao, UserRole> {

    /**
     * 根据用户Id查找角色
     *
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "userRoleList")
    public List<UserRole> findByUserId(String userId) {
        return baseMapper.findByUserId(userId);
    }

    /**
     * 根据idList批量查找
     *
     * @param idList 主键ID列表
     * @return
     */
    @Cacheable(cacheNames = "userRoleList")
    public List<UserRole> selectBatchIds(List<String> idList) {
        return super.selectBatchIds(idList);
    }
}
