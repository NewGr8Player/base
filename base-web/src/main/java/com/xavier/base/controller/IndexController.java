package com.xavier.base.controller;

import com.xavier.base.common.BaseController;
import com.xavier.base.entity.*;
import com.xavier.base.service.*;
import com.xavier.base.structure.TreeNode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    private static final String INDEX_MAPPING = "/index/index";

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionMenuService permissionMenuService;

    @RequiresPermissions({"sys:root:index"})
    @RequestMapping(path = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView indexPage() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<UserRole> userRoleList = userRoleService.findByUserId(user.getId());
        Collection<Role> roleList = roleService.listByIds(
                userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList())
        );
        Collection<RolePermission> rolePermissionList = rolePermissionService.listByRoleIds(
                roleList.stream().map(Role::getId).collect(Collectors.toList())
        );
        Collection<Permission> permissionList = permissionService.listByIds(
                rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList())
        );
        List<PermissionMenu> permissionMenuList = permissionMenuService.listByPermissionIds(
                permissionList.stream().map(Permission::getId).collect(Collectors.toList())
        );
        Collection<TreeNode> menuList = menuService.selectMenuTree(
                new Menu().setVisitable("show")
                , permissionMenuList.stream().map(PermissionMenu::getMenuId).collect(Collectors.toList())
        );
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("currentUser", user);
        dataMap.put("menuList", menuList);
        return modelAndView(INDEX_MAPPING, dataMap);
    }
}
