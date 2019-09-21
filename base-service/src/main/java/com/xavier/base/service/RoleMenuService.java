package com.xavier.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.RoleMenuMapper;
import com.xavier.base.entity.RoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RoleMenuService
 *
 * @author NewGr8Player
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    @Transactional
    public void saveRoleMenu(String roleId, List<String> menuIds) {
        remove(lambdaQuery().eq(RoleMenu::getRoleId, roleId));
        saveBatch(menuIds.stream().map(menuId -> new RoleMenu(roleId, menuId)).collect(Collectors.toList()));
    }
}
