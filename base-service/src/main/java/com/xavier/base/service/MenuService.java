package com.xavier.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xavier.bean.Menu;
import com.xavier.common.structure.TreeNode;
import com.xavier.dao.MenuDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜单Servier
 *
 * @author NewGr8Player
 */
@Service
@Transactional(readOnly = true)
public class MenuService extends ServiceImpl<MenuDao, Menu> {

    /**
     * 根据idList批量查询
     *
     * @param idList 主键ID列表
     * @return
     */
    @Cacheable(cacheNames = "channelMenuList")
    public List<Menu> selectBatchIds(List<String> idList) {
        return super.selectBatchIds(idList);
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
        EntityWrapper entityWrapper = new EntityWrapper();
        if (StringUtils.isNotBlank(menu.getMenuType())) { /* menu_type */
            entityWrapper.eq("menu_type", menu.getMenuType());
        }
        if (StringUtils.isNotBlank(menu.getVisiable())) { /* visiable */
            entityWrapper.eq("visiable", menu.getVisiable());
        }
        if (StringUtils.isNotBlank(menu.getMenuName())) { /* menu_name */
            entityWrapper.like("menu_name", "%" + menu.getMenuCode() + "%");
        }
        entityWrapper.in("id", ids);
        entityWrapper.orderBy("parent_id,menu_order");
        List<Menu> menuList = baseMapper.selectList(entityWrapper);
        Map<String, List<Menu>> childMap = new HashMap<>();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (Menu it : menuList) {
            if ("0".equals(it.getParentId())) {
                treeNodeList.add(new TreeNode(it));
            } else {
                if (childMap.containsKey(it.getParentId())) {
                    List<Menu> childList = childMap.get(it.getParentId());
                    childList.add(it);
                    childMap.put(it.getParentId(), childList);
                } else {
                    childMap.put(it.getParentId(), Arrays.asList(it));
                }
            }
        }
        for (TreeNode<Menu> treeNode : treeNodeList) {
            String parentId = treeNode.getCurrent().getId();
            if (childMap.containsKey(parentId)) {
                treeNode.setChild(childMap.get(parentId));
            }
        }
        return treeNodeList;
    }
}
