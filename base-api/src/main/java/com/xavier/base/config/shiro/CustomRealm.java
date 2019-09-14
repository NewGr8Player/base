package com.xavier.config.shiro;

import com.xavier.bean.*;
import com.xavier.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Shiro鉴权
 *
 * @author NewGr8Player
 */
@Component
public class CustomRealm extends AuthorizingRealm {

	/**
	 * 此处使用 {@code @Lazy} 注解原因
	 * 不使用会影响Redis缓存的正常使用
	 * https://docs.spring.io/spring-framework/docs/4.0.0.RELEASE/javadoc-api/org/springframework/context/annotation/Lazy.html
	 */
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
		if (null != user) {
			List<UserRole> userRoleList = this.userRoleService.findByUserId(user.getId());
			for (UserRole userRole : userRoleList) {
				/* 角色 */
				Role role = this.roleService.selectById(userRole.getRoleId());
				if (null != role) {
					simpleAuthorizationInfo.addRole(role.getRoleCode());
					List<RolePermission> rolePermissionList = this.rolePermissionService.findByRoleId(role.getId());
					for (RolePermission rolePermission : rolePermissionList) {
						/* 权限 */
						Permission permission = this.permissionService.selectById(rolePermission.getPermissionId());
						if (null != permission) {
							simpleAuthorizationInfo.addStringPermission(permission.getPermissionCode());
						}
					}
				}
			}
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
		UsernamePasswordToken usertoken = (UsernamePasswordToken) authenticationToken;
		String username = usertoken.getUsername();
		User user = userService.findByUserName(username);
		if (user != null) {
			return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		} else {
			return null;
		}
	}

}
