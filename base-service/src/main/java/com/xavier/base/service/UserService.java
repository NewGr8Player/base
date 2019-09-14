package com.xavier.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.User;
import com.xavier.dao.UserDao;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class UserService extends ServiceImpl<UserDao, User> {

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
}
