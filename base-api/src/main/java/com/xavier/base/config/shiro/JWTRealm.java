package com.xavier.base.config.shiro;

import com.alibaba.fastjson.util.TypeUtils;
import com.xavier.base.config.jwt.JWTToken;
import com.xavier.base.entity.User;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.service.ResourceService;
import com.xavier.base.service.UserService;
import com.xavier.base.util.ApiAssert;
import com.xavier.base.util.jwt.JWTGen;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shiro鉴权
 *
 * @author NewGr8Player
 */
@Service
public class JWTRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private UserService userService;
    @Lazy
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private JWTGen tokenService;

    /**
     * 权限验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /* 查询用户名称 */
        User user = (User) principalCollection.getPrimaryPrincipal();
        String uid = user.getId();
        /* 添加角色和权限 */
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleIds = userService.getRoleIds(uid).stream().map(TypeUtils::castToString).collect(Collectors.toSet());
        simpleAuthorizationInfo.addRoles(roleIds);
        simpleAuthorizationInfo.addStringPermissions(resourceService.getUserPerms(uid));
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String tokenPrincipal = (String) authenticationToken.getPrincipal();
        String username = tokenService.getClaimField(tokenPrincipal);
        User user = userService.lambdaQuery().eq(StringUtils.isNotBlank(username), User::getLoginName, username).one();
        if (Objects.isNull(user)) {
            return null;
        } else {
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
    }

    /**
     * 支持JWT
     *
     * @param token token对象
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
