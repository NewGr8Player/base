/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : crown

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 22/09/2019 01:05:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `menu_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `menu_type` smallint(2) NOT NULL COMMENT '类型:0:目录,1:菜单,2:按钮',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `status` smallint(2) NOT NULL COMMENT '状态 0：禁用 1：正常',
  `router` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由',
  `alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', 1, 'layui-icon-set', 0, NULL, '', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('23', '1', '用户管理', 'views/user/index.html', 2, 'layui-icon-username', 0, 'user', 'sys:user:list', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('24', '1', '角色管理', 'views/role/index.html', 2, 'layui-icon-face-surprised', 0, 'role', 'sys:role:list', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('25', '1', '菜单管理', 'views/menu/index.html', 2, 'layui-icon-template', 0, 'menu', 'sys:menu:list', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('26', '1', '资源管理', 'views/resource/index.html', 2, 'layui-icon-read', 0, 'resource', 'sys:resource:list', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('27', '26', '刷新资源', '', 3, 'layui-icon-refresh-3', 0, NULL, 'sys:resource:refresh', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('28', '25', '添加', '', 3, 'layui-icon-add-circle-fine', 0, NULL, 'sys:menu:add', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('29', '25', '修改', '', 3, 'layui-icon-senior', 0, NULL, 'sys:menu:edit', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('30', '25', '删除', '', 3, 'layui-icon-close', 0, NULL, 'sys:menu:delete', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('31', '24', '添加', '', 3, 'layui-icon-add-circle-fine', 0, NULL, 'sys:role:add', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('32', '24', '修改', '', 3, 'layui-icon-senior', 0, NULL, 'sys:role:edit', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('33', '24', '删除', '', 3, 'layui-icon-close', 0, NULL, 'sys:role:delete', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('34', '23', '添加', '', 3, 'layui-icon-add-circle-fine', 0, NULL, 'sys:user:add', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('35', '23', '修改', '', 3, 'layui-icon-senior', 0, NULL, 'sys:user:edit', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('36', '23', '重置密码', '', 3, 'layui-icon-fire', 0, NULL, 'sys:user:resetpwd', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');
INSERT INTO `sys_menu` VALUES ('42', '24', '菜单授权', NULL, 3, 'layui-icon-auz', 0, NULL, 'sys:role:perm', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:14');

-- ----------------------------
-- Table structure for sys_menu_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_resource`;
CREATE TABLE `sys_menu_resource`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键Id',
  `menu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单Id',
  `resource_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源Id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单资源关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_resource
-- ----------------------------
INSERT INTO `sys_menu_resource` VALUES ('100', '28', 'a5529264d2645996c83bba2e961d0ec3', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('101', '28', '2c654f1264fc85ac80516245672f3c47', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('70', '27', 'f45f1b577d72dcd86b84c6f033682b53', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('71', '26', '829a851334028a6e47b59f8dea0cf7cb', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('72', '30', 'f15f7b01ffe7166b05c3984c9b967837', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('73', '33', '6692d9d95184977f82d3252de2f5eac7', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('74', '29', 'a11e2191656cb199bea1defb17758411', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('75', '29', '6fd51f02b724c137a08c28587f48d7f3', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('76', '29', '2c654f1264fc85ac80516245672f3c47', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('77', '29', 'a5529264d2645996c83bba2e961d0ec3', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('80', '25', '6d1170346960aa8922b9b4d08a5bf71b', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('81', '25', '30218613e987e464b13e0c0b8721aec5', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('83', '31', 'd82de0a17f2c63106f98eb2f88d166e9', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('85', '36', '7baa5b852bc92715d7aa503c0a0d8925', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('87', '23', '579e469e8ac850de1ca0adc54d01acba', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('88', '23', 'b4770c0fe93fce7e829463328c800f20', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('89', '35', '30386fd7b8a4feb9c59861e63537acde', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('90', '35', '8a3b4dc05867f5946235ba5fbc492b76', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('91', '24', '04972e9f8e65b0364d9862687666da36', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('93', '42', 'a826bca352908155da4ca6702edfa2ed', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('94', '42', '30218613e987e464b13e0c0b8721aec5', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('95', '34', 'a71cb59835c613f39bd936deb20cdd27', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('96', '34', 'd9d6f7163b313b950710a637682354d1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('97', '32', 'eaee955f405f6b96beab5136bfa3e29b', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('98', '32', 'd9d6f7163b313b950710a637682354d1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');
INSERT INTO `sys_menu_resource` VALUES ('99', '28', '8cb1442c7814f65ce0d796e1ef93c715', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:22');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `mapping` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径映射',
  `method` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `auth_type` smallint(2) NOT NULL COMMENT '权限认证类型',
  `perm` varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('04972e9f8e65b0364d9862687666da36', '查询所有角色(分页)', '/roles', 'GET', 3, 'GET:/roles', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('29c4c75326ecf3a82f815c43b0085b2f', '修改账户信息', '/account/info', 'PUT', 1, 'PUT:/account/info', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('2c654f1264fc85ac80516245672f3c47', '查询父级菜单(下拉框)', '/menus/combos', 'GET', 3, 'GET:/menus/combos', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('30218613e987e464b13e0c0b8721aec5', '查询所有菜单', '/menus', 'GET', 3, 'GET:/menus', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('30386fd7b8a4feb9c59861e63537acde', '修改用户', '/users/{id}', 'PUT', 3, 'PUT:/users/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('3ae42391ca3abe20c5cca35f4427cf9c', '获取账户按钮', '/account/buttons/aliases', 'GET', 1, 'GET:/account/buttons/aliases', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('579e469e8ac850de1ca0adc54d01acba', '查询所有用户', '/users', 'GET', 3, 'GET:/users', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('6692d9d95184977f82d3252de2f5eac7', '删除角色', '/roles/{id}', 'DELETE', 3, 'DELETE:/roles/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('6ab0f8a49671e489f11a1bef2fcaf102', '清除Token', '/account/token', 'DELETE', 1, 'DELETE:/account/token', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('6d1170346960aa8922b9b4d08a5bf71b', '设置菜单状态', '/menus/{id}/status', 'PUT', 3, 'PUT:/menus/{id}/status', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('6fd51f02b724c137a08c28587f48d7f3', '查询单个菜单', '/menus/{id}', 'GET', 3, 'GET:/menus/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('7baa5b852bc92715d7aa503c0a0d8925', '重置用户密码', '/users/{id}/password', 'PUT', 3, 'PUT:/users/{id}/password', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('829a851334028a6e47b59f8dea0cf7cb', '查询所有资源(分页)', '/resources', 'GET', 3, 'GET:/resources', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('842e33410b5a97b6c797e4782c97a90e', '获取Token', '/account/token', 'POST', 2, 'POST:/account/token', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('8a3b4dc05867f5946235ba5fbc492b76', '查询单个用户', '/users/{id}', 'GET', 3, 'GET:/users/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('8cb1442c7814f65ce0d796e1ef93c715', '添加菜单', '/menus', 'POST', 3, 'POST:/menus', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('982803fc834e82cbb2ac1b93f2a47690', '查询单个角色', '/roles/{id}', 'GET', 3, 'GET:/roles/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('a11e2191656cb199bea1defb17758411', '修改菜单', '/menus/{id}', 'PUT', 3, 'PUT:/menus/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('a5529264d2645996c83bba2e961d0ec3', '查询所有资源', '/resources/resources', 'GET', 3, 'GET:/resources/resources', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('a71cb59835c613f39bd936deb20cdd27', '创建用户', '/users', 'POST', 3, 'POST:/users', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('a826bca352908155da4ca6702edfa2ed', '修改角色菜单', '/roles/{id}/menus', 'PUT', 3, 'PUT:/roles/{id}/menus', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('b4770c0fe93fce7e829463328c800f20', '设置用户状态', '/users/{id}/status', 'PUT', 3, 'PUT:/users/{id}/status', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('c2db9729dcd4a7d703e45411bb445dfd', '修改密码', '/account/password', 'PUT', 1, 'PUT:/account/password', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('d81bffa6ffd70cc443703820b5a95e8d', '获取账户菜单', '/account/menus', 'GET', 1, 'GET:/account/menus', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('d82de0a17f2c63106f98eb2f88d166e9', '添加角色', '/roles', 'POST', 3, 'POST:/roles', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('d9d6f7163b313b950710a637682354d1', '查询所有角色', '/roles/roles', 'GET', 3, 'GET:/roles/roles', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('e78940daf86b9ac5563d539e8802429c', '获取账户详情', '/account/info', 'GET', 1, 'GET:/account/info', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('eaee955f405f6b96beab5136bfa3e29b', '修改角色', '/roles/{id}', 'PUT', 3, 'PUT:/roles/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('f15f7b01ffe7166b05c3984c9b967837', '删除菜单', '/menus/{id}', 'DELETE', 3, 'DELETE:/menus/{id}', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');
INSERT INTO `sys_resource` VALUES ('f45f1b577d72dcd86b84c6f033682b53', '刷新资源', '/resources', 'PUT', 3, 'PUT:/resources', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:30');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '超级管理员', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:38');
INSERT INTO `sys_role` VALUES (2, '普通管理员', '普通管理员', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:38');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键Id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色Id',
  `menu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单Id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('245', '2', '1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('246', '2', '26', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('247', '2', '27', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('262', '1', '1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('263', '1', '23', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('264', '1', '34', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('265', '1', '35', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('266', '1', '36', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('267', '1', '24', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('268', '1', '31', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('269', '1', '32', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('270', '1', '33', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('271', '1', '42', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('272', '1', '25', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('273', '1', '28', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('274', '1', '29', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('275', '1', '30', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('276', '1', '26', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');
INSERT INTO `sys_role_menu` VALUES ('277', '1', '27', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:11:49');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键Id',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色Id',
  `resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单Id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键Id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `status` smallint(2) NOT NULL COMMENT '状态 0：禁用 1：正常',
  `login_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `ip` varchar(135) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'Crown', 'caratacus@qq.com', '13712345678', 0, 'crown', '$apr1$crown$WQ2TEXVPUJ8l6N6gm0CGv.', '0:0:0:0:0:0:0:1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:09');
INSERT INTO `sys_user` VALUES ('18', 'crown1', '11@qq.com', '13718867899', 0, 'crown1', '$apr1$crown1$NsepppGmlSjqtwPTlaLb1/', '0:0:0:0:0:0:0:1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:09');
INSERT INTO `sys_user` VALUES ('19', 'crown2', '13878929833@163.com', '13878929833', 1, 'crown2', '$apr1$crown2$R/8LgZ.REDrXB3jlpyglI0', NULL, NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:09');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `last_modify` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '变更时间(数据库自动)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('24', '18', '2', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:18');
INSERT INTO `sys_user_role` VALUES ('49', '1', '1', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:18');
INSERT INTO `sys_user_role` VALUES ('50', '19', '2', NULL, NULL, NULL, NULL, 0, '2019-09-21 19:12:18');

SET FOREIGN_KEY_CHECKS = 1;
