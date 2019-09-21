package com.xavier.base.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.MenuMapper;
import com.xavier.base.entity.Menu;
import com.xavier.base.entity.MenuResource;
import com.xavier.base.entity.MenuTree;
import com.xavier.base.enums.ErrorCodeEnum;
import com.xavier.base.enums.MenuTypeEnum;
import com.xavier.base.enums.StatusEnum;
import com.xavier.base.util.ApiAssert;
import com.xavier.base.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MenuService
 *
 * @author NewGr8Player
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Autowired
    private MenuResourceService menuResourceService;

    @Transactional
    public void saveMenu(Menu menu, List<String> resourceIds) {
        save(menu);
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            String menuId = menu.getId();
            /* 添加resource关联 */
            menuResourceService.saveBatch(menuResourceService.getMenuResources(menuId, resourceIds)
            );
        }
    }


    @Transactional
    public void updateMenu(Menu menu, List<String> resourceIds) {
        updateById(menu);
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            String menuId = menu.getId();
            /* 删除resource关联 */
            menuResourceService.removeByMenuId(menuId);
            /* 添加resource关联 */
            menuResourceService.saveBatch(menuResourceService.getMenuResources(menuId, resourceIds)
            );
        }
    }


    @Transactional
    public void removeMenu(String menuId) {
        if (parentIdNotNull(menuId)) {
            lambdaQuery().eq(Menu::getParentId, menuId)
                    .list()
                    .stream()
                    .filter(e -> parentIdNotNull(e.getParentId()))
                    .forEach(e -> removeMenu(e.getId()));
            /* 删除resource关联 */
            menuResourceService.removeByMenuId(menuId);
            /* 删除菜单 */
            removeById(menuId);
        }

    }


    @Transactional
    public void updateStatus(Integer menuId, StatusEnum status) {
        Menu menu = getById(menuId);
        ApiAssert.notNull(ErrorCodeEnum.MENU_NOT_FOUND, menu);
        menu.setStatus(status);
        updateById(menu);
    }

    /**
     * 父ID不为0并且不为空
     *
     * @param parentId
     * @return
     */
    private boolean parentIdNotNull(String parentId) {
        return Objects.nonNull(parentId) && !Objects.equals(parentId, "0");
    }


    public Menu getMenuDetails(Integer menuId) {
        Menu menu = getById(menuId);
        ApiAssert.notNull(ErrorCodeEnum.MENU_NOT_FOUND, menu);
        List<String> resourceIds =
                menuResourceService.lambdaQuery()
                        .select(MenuResource::getResourceId)
                        .eq(MenuResource::getMenuId, menuId)
                        .list()
                        .stream().map(MenuResource::getResourceId)
                        .collect(Collectors.toList());
        menu.setResourceIds(resourceIds);
        return menu;
    }


    public List<MenuTree> getUserPermMenus(String uid) {
        List<MenuTree> menus = baseMapper.getUserPermMenus(uid, StatusEnum.NORMAL, Arrays.asList(MenuTypeEnum.CATALOG, MenuTypeEnum.MENU));
        return menus.stream()
                .filter(e -> !parentIdNotNull(e.getParentId()))
                .map(e -> TreeUtils.findChildren(e, menus)
                )
                .collect(Collectors.toList());
    }


    public Set<String> getUserPermButtonAliases(String uid) {
        return baseMapper.getUserPermMenus(uid, StatusEnum.NORMAL, Collections.singletonList(MenuTypeEnum.BUTTON))
                .stream()
                .map(MenuTree::getAlias)
                .collect(Collectors.toSet());
    }
}
