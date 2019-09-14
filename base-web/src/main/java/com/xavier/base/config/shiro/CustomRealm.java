package com.xavier.base.config.shiro;

import com.xavier.base.entity.Permission;
import com.xavier.base.entity.Role;
import com.xavier.base.entity.User;
import com.xavier.base.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * Shiro鉴权
 *
 * @author NewGr8Player
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private UserService userService;
    @Lazy
    @Autowired
    private UserRoleService userRoleService;
    @Lazy
    @Autowired
    private RolePermissionService rolePermissionService;
    @Lazy
    @Autowired
    private RoleService roleService;
    @Lazy
    @Autowired
    private PermissionService permissionService;

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
        /* 添加角色和权限 */
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (Objects.nonNull(user)) {
            Optional.ofNullable(this.userRoleService.findByUserId(user.getId()))
                    .orElse(Collections.emptyList())
                    .forEach(userRole -> {
                        /* 角色 */
                        Role role = this.roleService.getById(userRole.getRoleId());
                        if (Objects.nonNull(role)) {
                            simpleAuthorizationInfo.addRole(role.getRoleCode());
                            Optional.ofNullable(this.rolePermissionService.findByRoleId(role.getId()))
                                    .orElse(Collections.emptyList())
                                    .forEach(
                                            rolePermission -> {
                                                /* 权限 */
                                                Permission permission = this.permissionService.selectById(rolePermission.getPermissionId());
                                                if (Objects.nonNull(permission)) {
                                                    simpleAuthorizationInfo.addStringPermission(permission.getPermissionCode());
                                                }
                                            }
                                    );
                        }
                    });
        }
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
        String username = ((UsernamePasswordToken) authenticationToken).getUsername();
        User user = userService.findByUserName(username);
        if (Objects.isNull(user)) {
            return null;
        } else {
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
    }
}
