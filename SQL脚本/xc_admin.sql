/*
 Navicat MySQL Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : xc_admin

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 16/07/2023 12:07:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单主键ID',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `perms` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '权限标识',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 'system', 0, 1, '/sys', '', 'M', '', '2022-07-04 14:56:29', '2022-07-04 14:56:31', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '业务管理', 'monitor', 0, 2, '/bsns', '', 'M', '', '2022-07-04 14:59:43', '2022-07-04 14:59:45', '业务管理目录');
INSERT INTO `sys_menu` VALUES (3, '用户管理', 'user', 1, 1, '/sys/user', 'sys/user/index', 'C', 'system:user:list', '2022-07-04 15:20:51', '2022-07-04 15:20:53', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (4, '角色管理', 'peoples', 1, 2, '/sys/role', 'sys/role/index', 'C', 'system:role:list', '2022-07-04 15:23:35', '2022-07-04 15:23:39', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 'tree-table', 1, 3, '/sys/menu', 'sys/menu/index', 'C', 'system:menu:list', '2022-07-04 15:23:41', '2022-07-04 15:23:43', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 'tree', 2, 1, '/bsns/department', 'bsns/Department', 'C', '', '2022-07-04 15:24:40', '2022-07-04 15:24:44', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (7, '岗位管理', 'post', 2, 2, '/bsns/post', 'bsns/Post', 'C', '', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (8, '用户新增', '#', 3, 2, '', '', 'F', 'system:user:add', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '添加用户按钮');
INSERT INTO `sys_menu` VALUES (9, '用户修改', '#', 3, 3, '', '', 'F', 'system:user:edit', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '修改用户按钮');
INSERT INTO `sys_menu` VALUES (10, '用户删除', '#', 3, 4, '', '', 'F', 'system:user:delete', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '删除用户按钮');
INSERT INTO `sys_menu` VALUES (11, '分配角色', '#', 3, 5, '', '', 'F', 'system:user:role', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '分配角色按钮');
INSERT INTO `sys_menu` VALUES (12, '重置密码', '#', 3, 6, '', '', 'F', 'system:user:resetPwd', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '重置密码按钮');
INSERT INTO `sys_menu` VALUES (13, '角色新增', '#', 4, 2, '', '', 'F', 'system:role:add', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '添加用户按钮');
INSERT INTO `sys_menu` VALUES (14, '角色修改', '#', 4, 3, '', '', 'F', 'system:role:edit', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '修改用户按钮');
INSERT INTO `sys_menu` VALUES (15, '角色删除', '#', 4, 4, '', NULL, 'F', 'system:role:delete', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '删除用户按钮');
INSERT INTO `sys_menu` VALUES (16, '分配权限', '#', 4, 5, '', '', 'F', 'system:role:menu', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '分配权限按钮');
INSERT INTO `sys_menu` VALUES (17, '菜单新增', '#', 5, 2, '', NULL, 'F', 'system:menu:add', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '添加菜单按钮');
INSERT INTO `sys_menu` VALUES (18, '菜单修改', '#', 5, 3, '', NULL, 'F', 'system:menu:edit', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '修改菜单按钮');
INSERT INTO `sys_menu` VALUES (19, '菜单删除', '#', 5, 4, '', NULL, 'F', 'system:menu:delete', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '删除菜单按钮');
INSERT INTO `sys_menu` VALUES (20, '用户查询', '#', 3, 1, '', NULL, 'F', 'system:user:query', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '用户查询按钮');
INSERT INTO `sys_menu` VALUES (21, '角色查询', '#', 4, 1, '', NULL, 'F', 'system:role:query', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '角色查询按钮');
INSERT INTO `sys_menu` VALUES (22, '菜单查询', '#', 5, 1, '', NULL, 'F', 'system:menu:query', '2022-07-04 15:24:42', '2022-07-04 15:24:46', '菜单查询按钮');
INSERT INTO `sys_menu` VALUES (34, '测试2', '', 3, 7, '', 'sys/test', 'F', 'system:user:test', '2023-07-15 12:22:48', '2023-07-15 12:22:58', NULL);
INSERT INTO `sys_menu` VALUES (37, '测试菜单', '', 1, 1, '', '', 'C', '', '2023-07-16 00:38:02', '2023-07-16 00:38:02', NULL);
INSERT INTO `sys_menu` VALUES (38, '测试', '', 37, 1, '', 'sys/test', 'F', 'test', '2023-07-16 00:38:33', '2023-07-16 00:38:33', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色主键ID',
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '2022-07-04 14:40:44', '2022-07-04 14:40:47', '拥有系统最高权限');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', '2022-07-04 14:41:56', '2022-07-04 14:41:58', '普通角色');
INSERT INTO `sys_role` VALUES (3, '测试角色', 'test', '2022-07-04 14:42:24', '2022-07-04 14:42:27', '测试角色');
INSERT INTO `sys_role` VALUES (4, '2', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (5, '3', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (6, '4', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (7, '5', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (14, '6', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (19, '测2', 'cc2', '2022-08-13 21:06:21', '2022-08-13 13:06:27', 'eewew2');
INSERT INTO `sys_role` VALUES (20, 'ccc测试', 'test2', '2022-08-29 17:10:33', NULL, 'xxx');
INSERT INTO `sys_role` VALUES (21, '今天测试角色', 'todytest', '2022-08-29 22:01:11', NULL, 'ccc');
INSERT INTO `sys_role` VALUES (23, '傻逼2', 'sb2', '2023-07-14 10:41:29', '2023-07-14 10:41:29', '傻逼二号权限');
INSERT INTO `sys_role` VALUES (25, '测试jige', 'testjige', '2023-07-16 00:27:29', '2023-07-16 00:27:29', '');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单主键ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 326 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (8, 2, 1);
INSERT INTO `sys_role_menu` VALUES (9, 2, 2);
INSERT INTO `sys_role_menu` VALUES (10, 2, 3);
INSERT INTO `sys_role_menu` VALUES (11, 2, 4);
INSERT INTO `sys_role_menu` VALUES (12, 2, 5);
INSERT INTO `sys_role_menu` VALUES (13, 2, 6);
INSERT INTO `sys_role_menu` VALUES (14, 2, 7);
INSERT INTO `sys_role_menu` VALUES (21, 7, 1);
INSERT INTO `sys_role_menu` VALUES (22, 7, 2);
INSERT INTO `sys_role_menu` VALUES (23, 7, 6);
INSERT INTO `sys_role_menu` VALUES (24, 7, 7);
INSERT INTO `sys_role_menu` VALUES (25, 6, 1);
INSERT INTO `sys_role_menu` VALUES (26, 6, 3);
INSERT INTO `sys_role_menu` VALUES (27, 6, 9);
INSERT INTO `sys_role_menu` VALUES (28, 6, 10);
INSERT INTO `sys_role_menu` VALUES (29, 19, 1);
INSERT INTO `sys_role_menu` VALUES (30, 19, 3);
INSERT INTO `sys_role_menu` VALUES (31, 19, 2);
INSERT INTO `sys_role_menu` VALUES (32, 19, 6);
INSERT INTO `sys_role_menu` VALUES (33, 1, 1);
INSERT INTO `sys_role_menu` VALUES (34, 1, 3);
INSERT INTO `sys_role_menu` VALUES (35, 1, 20);
INSERT INTO `sys_role_menu` VALUES (36, 1, 8);
INSERT INTO `sys_role_menu` VALUES (37, 1, 9);
INSERT INTO `sys_role_menu` VALUES (38, 1, 10);
INSERT INTO `sys_role_menu` VALUES (39, 1, 11);
INSERT INTO `sys_role_menu` VALUES (40, 1, 12);
INSERT INTO `sys_role_menu` VALUES (41, 1, 4);
INSERT INTO `sys_role_menu` VALUES (42, 1, 21);
INSERT INTO `sys_role_menu` VALUES (43, 1, 13);
INSERT INTO `sys_role_menu` VALUES (44, 1, 14);
INSERT INTO `sys_role_menu` VALUES (45, 1, 15);
INSERT INTO `sys_role_menu` VALUES (46, 1, 16);
INSERT INTO `sys_role_menu` VALUES (47, 1, 23);
INSERT INTO `sys_role_menu` VALUES (48, 1, 5);
INSERT INTO `sys_role_menu` VALUES (49, 1, 22);
INSERT INTO `sys_role_menu` VALUES (50, 1, 17);
INSERT INTO `sys_role_menu` VALUES (51, 1, 18);
INSERT INTO `sys_role_menu` VALUES (52, 1, 19);
INSERT INTO `sys_role_menu` VALUES (53, 1, 2);
INSERT INTO `sys_role_menu` VALUES (54, 1, 6);
INSERT INTO `sys_role_menu` VALUES (55, 1, 7);
INSERT INTO `sys_role_menu` VALUES (208, 20, 1);
INSERT INTO `sys_role_menu` VALUES (209, 20, 3);
INSERT INTO `sys_role_menu` VALUES (210, 20, 20);
INSERT INTO `sys_role_menu` VALUES (211, 20, 8);
INSERT INTO `sys_role_menu` VALUES (212, 20, 9);
INSERT INTO `sys_role_menu` VALUES (213, 20, 33);
INSERT INTO `sys_role_menu` VALUES (214, 20, 10);
INSERT INTO `sys_role_menu` VALUES (215, 20, 11);
INSERT INTO `sys_role_menu` VALUES (216, 20, 4);
INSERT INTO `sys_role_menu` VALUES (217, 20, 21);
INSERT INTO `sys_role_menu` VALUES (218, 20, 13);
INSERT INTO `sys_role_menu` VALUES (219, 20, 5);
INSERT INTO `sys_role_menu` VALUES (220, 20, 22);
INSERT INTO `sys_role_menu` VALUES (221, 20, 17);
INSERT INTO `sys_role_menu` VALUES (222, 20, 18);
INSERT INTO `sys_role_menu` VALUES (223, 20, 2);
INSERT INTO `sys_role_menu` VALUES (224, 20, 6);
INSERT INTO `sys_role_menu` VALUES (225, 20, 7);
INSERT INTO `sys_role_menu` VALUES (232, 21, 1);
INSERT INTO `sys_role_menu` VALUES (233, 21, 9);
INSERT INTO `sys_role_menu` VALUES (234, 21, 4);
INSERT INTO `sys_role_menu` VALUES (235, 21, 21);
INSERT INTO `sys_role_menu` VALUES (236, 21, 2);
INSERT INTO `sys_role_menu` VALUES (237, 21, 6);
INSERT INTO `sys_role_menu` VALUES (238, 21, 7);
INSERT INTO `sys_role_menu` VALUES (302, 3, 1);
INSERT INTO `sys_role_menu` VALUES (303, 3, 3);
INSERT INTO `sys_role_menu` VALUES (304, 3, 20);
INSERT INTO `sys_role_menu` VALUES (305, 3, 21);
INSERT INTO `sys_role_menu` VALUES (306, 3, 2);
INSERT INTO `sys_role_menu` VALUES (307, 3, 6);
INSERT INTO `sys_role_menu` VALUES (308, 3, 7);
INSERT INTO `sys_role_menu` VALUES (321, 25, 1);
INSERT INTO `sys_role_menu` VALUES (322, 25, 3);
INSERT INTO `sys_role_menu` VALUES (323, 25, 20);
INSERT INTO `sys_role_menu` VALUES (324, 25, 4);
INSERT INTO `sys_role_menu` VALUES (325, 25, 21);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1.png' COMMENT '用户头像',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'myz', '$2a$10$NbiX2.YscVSuuMVmDRVkrO.UW9BkPxWNtMrByy8TeqAhhZKMPIHXG', '202307161213100752.png', 'caofeng40177@163.com', '18862857417', '2022-08-29 22:10:52', '0', '2022-06-09 08:47:52', '2023-07-15 17:15:44', '超级管理员');
INSERT INTO `sys_user` VALUES (2, 'common', '$2a$10$tiArwm0GxChyEP5k0JGzsOuzyY15IKA.ZTl8S2aj3haYlKAfpwfl.', '2.png', '', '', '2022-08-22 21:34:39', '0', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (3, 'test', '$2a$10$tiArwm0GxChyEP5k0JGzsOuzyY15IKA.ZTl8S2aj3haYlKAfpwfl.', '1.png', 'faesfaef@163.com', '19970449330', '2022-07-24 17:36:07', '0', NULL, '2023-07-14 18:16:39', NULL);
INSERT INTO `sys_user` VALUES (5, '2', '$2a$10$iYCS07HdVUFlFn5DxyYNGOxQpOLHjuFJbNa6nt9vlm4EDlurIxco.', '9.png', 'faehfe@163.com', '19970449330', NULL, '1', NULL, '2023-07-16 00:13:59', NULL);
INSERT INTO `sys_user` VALUES (15, 'fdsfs', '$2a$10$AQVcp4hQ7REc5o7ztVnI7eX.sJdcYy3d1x2jm5CfrcCoMZMPacfpi', '10.png', 'fdfa4@qq.com', '18862851414', '2022-08-02 02:22:45', '1', '2022-08-02 02:21:24', '2022-08-01 18:23:16', 'fdfds4');
INSERT INTO `sys_user` VALUES (28, 'sdfss2', '$2a$10$7aNJxwVmefI0XAk64vrzYuOqeeImYJUQnoBrtKP9pLTGTWO2CXQ/y', '99.png', 'dfds3@qq.com', '18862857413', NULL, '1', '2022-08-07 00:42:46', '2022-08-06 16:43:04', 'ddd33');
INSERT INTO `sys_user` VALUES (31, 'xc', '$2a$10$AXYzpe5wk7xVtQZy9opvtuf3VnA1y0YPK5HBGeI78X.i3ZnV6jUWG', '1.png', 'fadj134@163.com', '13987127469', NULL, '0', NULL, '2023-07-13 12:27:38', '假正经的小柴');
INSERT INTO `sys_user` VALUES (32, 'test2', '123456', '1.png', 'fadsf@1das.com', '19970449330', NULL, '1', '2023-07-12 15:51:04', '2023-07-12 16:01:19', 'fdsafsdgs');
INSERT INTO `sys_user` VALUES (36, 'jige', '$2a$10$TjfILXeSoPtpFButdKOGm.JFd9/Gs.5tjEe.LbwIIY8DjhtADdiuG', '1.png', 'faewf@qq.com', '19970449330', NULL, '0', '2023-07-16 00:26:59', '2023-07-16 00:26:59', '');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_user_role` VALUES (10, 5, 3);
INSERT INTO `sys_user_role` VALUES (11, 15, 3);
INSERT INTO `sys_user_role` VALUES (16, 28, 2);
INSERT INTO `sys_user_role` VALUES (17, 28, 3);
INSERT INTO `sys_user_role` VALUES (27, 31, 23);
INSERT INTO `sys_user_role` VALUES (28, 31, 2);
INSERT INTO `sys_user_role` VALUES (29, 1, 1);
INSERT INTO `sys_user_role` VALUES (30, 3, 3);
INSERT INTO `sys_user_role` VALUES (31, 36, 25);

SET FOREIGN_KEY_CHECKS = 1;
