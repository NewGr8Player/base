package com.xavier.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.UserMapper;
import com.xavier.base.entity.Token;
import com.xavier.base.entity.User;
import com.xavier.base.entity.UserRole;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.enums.StatusEnum;
import com.xavier.base.util.ApiAssert;
import com.xavier.base.util.jwt.JWTGen;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author NewGr8Player
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private JWTGen tokenService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserRoleService userRoleService;


    @Transactional
    public User login(String loginName, String password, String ipAddr) {
        User user = lambdaQuery().eq(User::getLoginName, loginName).one();
        /* 用户不存在 */
        ApiAssert.notNull(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, user);
        /* 用户名密码错误 */
        ApiAssert.isTrue(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, Md5Crypt.apr1Crypt(password, loginName).equals(user.getPassword()));
        /* 用户被禁用 */
        ApiAssert.isTrue(ErrorCodeEnum.USER_IS_DISABLED, StatusEnum.NORMAL.equals(user.getStatus()));
        user.setIp(ipAddr);
        lambdaUpdate().set(User::getIp,ipAddr).eq(User::getId,user.getId());
        return user;
    }

    public Token getToken(User user) {
        Token token = new Token();
        token.setUid(user.getId());
        token.setToken(tokenService.createToken( user.getLoginName()));
        return token;
    }


    public User getUserDetails(String uid) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        user.setPerms(resourceService.getUserPerms(uid));
        return user;
    }


    @Transactional
    public void updatePassword(String uid, String oldPassword, String newPassword) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        /* 用户名密码错误 */
        ApiAssert.isTrue(ErrorCodeEnum.ORIGINAL_PASSWORD_IS_INCORRECT, Md5Crypt.apr1Crypt(oldPassword, user.getLoginName()).equals(user.getPassword()));
        user.setPassword(Md5Crypt.apr1Crypt(newPassword, user.getLoginName()));
        updateById(user);
    }


    @Transactional
    public void resetPwd(Integer uid) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        user.setPassword(Md5Crypt.apr1Crypt(user.getLoginName(), user.getLoginName()));
        updateById(user);
    }


    @Transactional
    public void updateStatus(Integer uid, StatusEnum status) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        user.setStatus(status);
        updateById(user);
    }


    @Transactional
    public void saveUserRoles(String uid, List<String> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            userRoleService.remove(userRoleService.lambdaQuery().eq(UserRole::getUid, uid));
            userRoleService.saveBatch(roleIds.stream().map(e -> new UserRole(uid, e)).collect(Collectors.toList()));
        }
    }


    public List<String> getRoleIds(String uid) {
        return userRoleService.lambdaQuery()
                .select(UserRole::getRoleId)
                .eq(UserRole::getUid, uid)
                .list()
                .stream()
                .map(UserRole::getUid)
                .collect(Collectors.toList());
    }

}
