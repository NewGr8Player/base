package com.xavier.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xavier.base.dao.MenuDao;
import com.xavier.base.entity.Menu;
import com.xavier.base.entity.User;
import com.xavier.base.structure.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单Service
 *
 * @author NewGr8Player
 */
@Service
@Transactional
public class MenuService extends ServiceImpl<MenuDao, Menu> {

    /**
     * 根据idList批量查询
     *
     * @param idList 主键ID列表
     * @return
     */
    @Cacheable(cacheNames = "channelMenuList")
    public Collection<Menu> selectBatchIds(List<String> idList) {
        return super.listByIds(idList);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param menu 实体对象
     * @param ids  id数组
     * @return List&lt;Menu&gt;
     */
    @Cacheable(cacheNames = "modelMenuList")
    public List<TreeNode> selectMenuTree(Menu menu, List<String> ids) {
        QueryWrapper<Menu> entityWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuType())) { /* menu_type */
            entityWrapper.eq("menu_type", menu.getMenuType());
        }
        if (StringUtils.isNotBlank(menu.getVisitable())) { /* visitable */
            entityWrapper.eq("visitable", menu.getVisitable());
        }
        if (StringUtils.isNotBlank(menu.getMenuName())) { /* menu_name */
            entityWrapper.like("menu_name", "%" + menu.getMenuCode() + "%");
        }
        entityWrapper.in("id", ids);
        entityWrapper.orderBy(true, true, "parent_id", "menu_order");
        List<Menu> menuList = baseMapper.selectList(entityWrapper);


        /* 根据ParentId分组 */
        Map<String, List<Menu>> groupedMap = menuList.stream().collect(Collectors.groupingBy(Menu::getParentId));

        List<TreeNode> treeNodeList = Optional.ofNullable(groupedMap.get("0"))
                .orElse(Collections.emptyList())
                .stream()
                .map(TreeNode::new)
                .collect(Collectors.toList());

        treeNodeList.forEach(
                treeNode -> {
                    String parentId = (String) treeNode.getCurrent().getId();
                    if (groupedMap.containsKey(parentId)) {
                        treeNode.setChild(groupedMap.get(parentId));
                    }
                }
        );
        return treeNodeList;
    }
}
