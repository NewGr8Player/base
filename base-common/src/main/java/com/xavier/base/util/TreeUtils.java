package com.xavier.base.util;

import com.xavier.base.entity.TreeNode;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 * TreeNode工具类
 * </p>
 *
 * @author NewGr8Player
 */
public class TreeUtils {

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        treeNodes.stream().filter(
                e -> Objects.equals(treeNode.getId(), e.getParentId())
        ).forEach(e -> treeNode.getChildren().add(findChildren(e, treeNodes))
        );
        return treeNode;
    }
}
