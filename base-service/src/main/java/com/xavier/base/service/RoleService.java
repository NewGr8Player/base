package com.xavier.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.RoleMapper;
import com.xavier.base.entity.Role;
import com.xavier.base.entity.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author NewGr8Player
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private RoleMenuService roleMenuService;

    public IPage<Role> pageRole(Page<Role> page, String roleName) {
        IPage<Role> rolePage = lambdaQuery()
                .like(StringUtils.isNotEmpty(roleName), Role::getRoleName, roleName)
                .page(page);
        return rolePage.convert(role -> {
            role.setMenuIds(
                    roleMenuService.lambdaQuery()
                            .select(RoleMenu::getMenuId)
                            .eq(RoleMenu::getRoleId, role.getId())
                            .list()
                            .stream().map(RoleMenu::getMenuId)
                            .collect(Collectors.toList())
            );
            return role;
        });
    }
}
