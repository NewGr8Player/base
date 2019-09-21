package com.xavier.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.MenuResourceMapper;
import com.xavier.base.entity.MenuResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MenuResourceService
 *
 * @author NewGr8Player
 */
@Service
public class MenuResourceService extends ServiceImpl<MenuResourceMapper, MenuResource> {

    public void removeByMenuId(String menuId) {
        remove(lambdaQuery().eq(MenuResource::getMenuId, menuId));
    }

    public List<MenuResource> getMenuResources(String menuId, List<String> resourceIds) {
        return resourceIds.stream()
                .map(resourceId -> new MenuResource(menuId, resourceId))
                .collect(Collectors.toList());
    }

}
