package com.xavier.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.UserDao;
import com.xavier.base.entity.User;
import com.xavier.base.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class UserService extends ServiceImpl<UserDao, User> {

    @Override
    public boolean save(User user) {
        return super.save(user.setPassword(PasswordUtil.encryptPassword(user.getPassword())));
    }

    /**
     * 根据用户名查找
     *
     * @param username 用户名
     * @return
     */
    @Cacheable(cacheNames = "user")
    public User findByUserName(String username) {
        return this.baseMapper.findByUsername(username);
    }

    public IPage<User> selectUserPage(IPage<User> userPage, User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        if (StringUtils.isNotBlank(user.getUsername())) {
            query.eq("username", user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getNickname())) {
            query.eq("nickname", user.getNickname());
        }
        return baseMapper.selectPage(userPage, query);
    }
}
