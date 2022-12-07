/*
 Navicat Premium Data Transfer

 Source Server         : ZnPi
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : pi-db:3306
 Source Schema         : pi

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 07/12/2022 14:29:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标识',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `sort` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `parent_id` bigint(0) UNSIGNED NULL DEFAULT 0 COMMENT '父节点(根节点为 0)',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除（0:=未删除, 1:=已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dept_name`(`name`, `deleted`) USING BTREE COMMENT '部门名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '2022-08-16 14:27:55', NULL, 'admin', NULL, '资讯部', 1, 0, 0);
INSERT INTO `sys_dept` VALUES (2, '2022-09-04 11:39:56', NULL, 'admin', NULL, '硬件组', 2, 1, 0);
INSERT INTO `sys_dept` VALUES (3, '2022-09-04 11:40:41', NULL, 'admin', NULL, '软件组', 1, 1, 0);
INSERT INTO `sys_dept` VALUES (4, '2022-12-01 16:08:12', NULL, 'admin', NULL, '人力资源部', 2, 0, 0);
INSERT INTO `sys_dept` VALUES (5, '2022-12-01 16:17:37', '2022-12-01 16:19:28', 'admin', 'admin', '公共事业部', 3, 0, 0);
INSERT INTO `sys_dept` VALUES (6, '2022-12-01 16:17:51', NULL, 'admin', NULL, '企宣部', 1, 5, 0);
INSERT INTO `sys_dept` VALUES (7, '2022-12-01 16:18:16', NULL, 'admin', NULL, '市场部', 2, 5, 0);
INSERT INTO `sys_dept` VALUES (8, '2022-12-01 16:26:24', '2022-12-01 16:28:56', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (9, '2022-12-01 16:30:13', '2022-12-01 16:38:25', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (10, '2022-12-01 16:30:19', '2022-12-01 16:38:25', 'admin', 'admin', 'test1', 999, 9, NULL);
INSERT INTO `sys_dept` VALUES (11, '2022-12-01 16:30:27', '2022-12-01 16:37:47', 'admin', 'admin', 'test2', 999, 9, NULL);
INSERT INTO `sys_dept` VALUES (12, '2022-12-01 16:30:34', '2022-12-01 16:37:47', 'admin', 'admin', 'test3', 999, 9, NULL);
INSERT INTO `sys_dept` VALUES (13, '2022-12-01 16:30:42', '2022-12-01 16:37:47', 'admin', 'admin', 'test4', 999, 12, NULL);
INSERT INTO `sys_dept` VALUES (14, '2022-12-01 16:30:59', '2022-12-01 16:37:47', 'admin', 'admin', 'test5', 999, 13, NULL);
INSERT INTO `sys_dept` VALUES (15, '2022-12-01 16:31:08', '2022-12-01 16:37:47', 'admin', 'admin', 'test6', 999, 12, NULL);
INSERT INTO `sys_dept` VALUES (16, '2022-12-06 22:52:00', '2022-12-06 22:53:12', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (19, '2022-12-06 22:53:17', '2022-12-06 22:53:58', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (20, '2022-12-06 22:54:02', '2022-12-06 23:09:40', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (21, '2022-12-06 22:54:23', '2022-12-06 23:09:59', 'admin', 'admin', 'test1', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (23, '2022-12-06 22:55:17', '2022-12-06 23:10:40', 'admin', 'admin', 'test2', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (24, '2022-12-06 22:55:52', '2022-12-06 23:14:55', 'admin', 'admin', 'test3', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (25, '2022-12-06 23:08:55', '2022-12-07 13:51:36', 'admin', 'admin', 'te', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (26, '2022-12-06 23:15:36', '2022-12-07 13:51:36', 'admin', 'admin', 'test', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (27, '2022-12-06 23:20:22', '2022-12-07 13:51:44', 'admin', 'admin', 'ttt', 333, 0, NULL);
INSERT INTO `sys_dept` VALUES (29, '2022-12-06 23:21:41', '2022-12-07 13:51:44', 'admin', 'admin', 'tt1t', 333, 0, NULL);
INSERT INTO `sys_dept` VALUES (34, '2022-12-06 23:27:27', '2022-12-07 13:51:44', 'admin', 'admin', 'tt12t', 333, 0, NULL);
INSERT INTO `sys_dept` VALUES (35, '2022-12-07 11:26:44', '2022-12-07 13:47:55', 'admin', 'admin', 'aaa', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (38, '2022-12-07 11:27:34', '2022-12-07 13:47:27', 'admin', 'admin', 'sss', 999, 0, NULL);
INSERT INTO `sys_dept` VALUES (39, '2022-12-07 11:49:17', '2022-12-07 13:47:05', 'admin', 'admin', 'ssss', 999, 0, NULL);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `type` tinyint(0) NULL DEFAULT 1 COMMENT '类型(0:=异常;1:=正常)',
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `exception_desc` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常描述',
  `request_method` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_param` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `request_time` bigint(0) NULL DEFAULT NULL COMMENT '请求耗时',
  `method_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (4, '2022-11-20 21:30:05', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (5, '2022-11-20 22:22:40', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (6, '2022-11-21 08:12:21', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (7, '2022-11-21 18:51:02', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (8, '2022-11-21 22:53:39', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (9, '2022-11-22 08:34:42', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (10, '2022-11-22 14:36:37', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (11, '2022-11-22 14:59:26', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (12, '2022-11-23 17:20:15', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (13, '2022-11-23 17:25:11', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (14, '2022-11-23 20:28:21', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (15, '2022-11-24 17:18:45', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (16, '2022-11-25 09:14:50', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (17, '2022-11-25 14:32:28', NULL, 'admin', NULL, 1, '0:0:0:0:0:0:0:1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (18, '2022-11-25 15:20:03', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (19, '2022-11-25 20:39:17', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (20, '2022-11-30 20:45:36', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (21, '2022-12-01 13:40:52', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (22, '2022-12-01 19:04:48', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (23, '2022-12-01 21:46:05', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (24, '2022-12-02 09:28:11', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (25, '2022-12-02 11:31:34', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (26, '2022-12-02 11:32:05', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (27, '2022-12-02 11:32:16', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (28, '2022-12-02 11:33:36', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (29, '2022-12-02 11:35:12', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (30, '2022-12-02 11:52:50', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (31, '2022-12-02 16:15:23', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (32, '2022-12-03 11:18:25', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (33, '2022-12-05 16:05:03', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (34, '2022-12-05 16:50:14', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (35, '2022-12-05 16:51:26', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (36, '2022-12-05 16:52:44', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (37, '2022-12-05 17:26:11', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (38, '2022-12-05 18:44:02', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (39, '2022-12-05 18:46:21', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (40, '2022-12-05 22:47:28', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (41, '2022-12-05 22:53:41', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (42, '2022-12-06 08:40:04', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (43, '2022-12-06 11:09:44', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (44, '2022-12-06 11:20:31', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (45, '2022-12-06 11:20:50', NULL, 'zhaoliu', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (46, '2022-12-06 11:21:03', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (47, '2022-12-06 13:42:01', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (48, '2022-12-06 14:07:18', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (49, '2022-12-06 14:08:03', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (50, '2022-12-06 14:08:40', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (51, '2022-12-06 14:40:34', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (52, '2022-12-06 14:46:55', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (53, '2022-12-06 14:49:07', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (54, '2022-12-06 14:49:15', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (55, '2022-12-06 14:52:45', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (56, '2022-12-06 16:34:36', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (57, '2022-12-06 16:56:53', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (58, '2022-12-06 16:57:13', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (59, '2022-12-06 20:34:00', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (60, '2022-12-06 21:09:53', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (61, '2022-12-06 21:10:25', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (62, '2022-12-06 23:08:38', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (63, '2022-12-06 23:09:19', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (64, '2022-12-06 23:14:48', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (67, '2022-12-07 11:27:43', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (68, '2022-12-07 14:11:13', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (69, '2022-12-07 14:19:06', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (70, '2022-12-07 14:23:34', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (71, '2022-12-07 14:23:41', NULL, 'zhangsan', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);
INSERT INTO `sys_log` VALUES (72, '2022-12-07 14:23:53', NULL, 'admin', NULL, 1, '127.0.0.1', '用户登录', NULL, 'POST', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由路径（浏览器地址栏路径）',
  `component_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径（vue页面完整路径，省略.vue后缀）',
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单权限标识',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` smallint(0) UNSIGNED NOT NULL COMMENT '排序',
  `keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存（0:=关闭, 1:=开启）',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '菜单类型（1:=目录, 2:=菜单，3:=按钮）',
  `external_links` tinyint(0) NULL DEFAULT 0 COMMENT '是否外链（0:=否, 1:=是）',
  `visible` tinyint(1) NULL DEFAULT NULL COMMENT '是否可见（0:=不可见，1：可见）',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
  `parent_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '父菜单 ID（0表示跟菜单）',
  `has_children` tinyint(0) NULL DEFAULT 0 COMMENT '是否拥有子节点（0：否；1：是）',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除(0:=未删除; null:=已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '2022-08-18 08:40:23', '2022-12-02 11:49:08', 'admin', 'admin', '系统管理', '/system', NULL, 'Navigation', NULL, 'system', 1, 1, 1, 0, 1, '/system/user', 0, 1, 0);
INSERT INTO `sys_menu` VALUES (2, '2022-08-18 08:42:45', '2022-12-06 16:36:34', 'admin', 'admin', '用户管理', 'user', 'SystemUser', 'system/user/Index', NULL, 'user', 1, 1, 2, 0, 1, NULL, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (3, '2022-08-18 08:45:24', '2022-09-26 20:28:58', 'admin', 'admin', '用户新增', '', NULL, '', 'sys_user_add', NULL, 2, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (4, '2022-08-28 17:48:21', '2022-09-27 07:48:38', 'admin', 'admin', '角色管理', 'role', 'SystemRole', 'system/role/Index', NULL, 'role', 3, 1, 2, 0, 1, NULL, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (5, '2022-09-19 14:17:23', '2022-09-27 07:48:44', 'admin', 'admin', '菜单管理', 'menu', 'SystemMenu', 'system/menu/Index', NULL, 'menu', 2, 1, 2, 0, 1, NULL, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (13, '2022-09-24 11:19:18', NULL, 'admin', NULL, 'test', '/test', '', 'Navigation', NULL, NULL, 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (14, '2022-09-26 20:21:08', '2022-12-06 16:37:02', 'admin', 'admin', '用户查询', NULL, NULL, NULL, 'sys_user_query', NULL, 1, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (15, '2022-09-26 20:29:37', NULL, 'admin', NULL, '用户删除', NULL, NULL, NULL, 'sys_user_delete', NULL, 3, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (16, '2022-09-26 20:30:06', '2022-09-26 20:30:14', 'admin', 'admin', '用户修改', NULL, NULL, NULL, 'sys_user_edit', NULL, 4, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (17, '2022-11-14 16:25:12', '2022-12-02 11:35:27', NULL, 'admin', '客户端管理', 'client', 'RegisteredClient', 'system/client/Index', NULL, 'client', 4, 1, 2, 0, 1, NULL, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (18, '2022-11-17 11:50:49', '2022-11-17 13:43:00', 'admin', 'admin', '系统监控', 'http://localhost:9731/webjars/swagger-ui/index.html', 'Navigation', NULL, NULL, 'monitor', 2, 1, 1, 1, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (19, '2022-11-17 13:43:26', '2022-11-20 16:47:38', 'admin', 'admin', '系统监控', '/monitoring', NULL, 'Navigation', NULL, 'monitor', 2, 1, 1, 0, 1, NULL, 0, 1, 0);
INSERT INTO `sys_menu` VALUES (20, '2022-11-17 13:46:55', NULL, 'admin', NULL, '接口文档', 'http://localhost:9731/webjars/swagger-ui/index.html', NULL, NULL, NULL, 'swagger', 1, 1, 2, 1, 1, NULL, 19, 0, 0);
INSERT INTO `sys_menu` VALUES (21, '2022-11-20 16:46:46', '2022-12-06 16:36:48', 'admin', 'admin', '操作日志', 'log', 'Log', 'system/log/Index', NULL, 'log', 2, 1, 2, 0, 1, NULL, 19, 1, 0);
INSERT INTO `sys_menu` VALUES (22, '2022-11-22 10:58:01', NULL, 'admin', NULL, '服务监控', 'http://pi:8027/applications', NULL, NULL, NULL, 'service-monitor', 3, 1, 2, 1, 1, NULL, 19, 0, 0);
INSERT INTO `sys_menu` VALUES (23, '2022-11-23 17:23:53', '2022-11-30 21:09:56', 'admin', 'admin', '任务调度', 'http://pi:8037/xxl-job-admin', NULL, NULL, NULL, 'task', 6, 1, 2, 1, 1, NULL, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (24, '2022-11-30 21:07:48', '2022-11-30 21:09:44', 'admin', 'admin', '部门管理', 'dept', 'SystemDept', 'system/dept/Index', NULL, 'dept', 5, 1, 2, 0, 1, NULL, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (25, '2022-12-02 13:53:02', '2022-12-02 13:54:05', 'admin', 'admin', 'test', 'test', NULL, 'Navigation', NULL, 'client', 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (26, '2022-12-02 13:53:16', '2022-12-02 13:54:05', 'admin', 'admin', 'ss', 'ss', NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 25, 0, NULL);
INSERT INTO `sys_menu` VALUES (27, '2022-12-02 13:53:31', '2022-12-02 13:54:05', 'admin', 'admin', 'ttt', 'ttt', NULL, 'Navigation', NULL, 'expand', 999, 1, 1, 0, 1, NULL, 26, 0, NULL);
INSERT INTO `sys_menu` VALUES (28, '2022-12-02 13:54:55', '2022-12-02 13:55:12', 'admin', 'admin', 'test1', 'te', NULL, 'Navigation', NULL, 'client', 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (29, '2022-12-02 14:07:50', '2022-12-06 16:37:35', 'admin', 'admin', '部门查询', NULL, NULL, NULL, 'sys_dept_query', NULL, 1, 1, 3, 0, 1, NULL, 24, 0, 0);
INSERT INTO `sys_menu` VALUES (30, '2022-12-02 14:08:41', '2022-12-06 22:49:33', 'admin', 'admin', '部门新增', NULL, NULL, NULL, 'sys_dept_add', NULL, 2, 1, 3, 0, 1, NULL, 24, 0, 0);
INSERT INTO `sys_menu` VALUES (31, '2022-12-02 14:11:13', NULL, 'admin', NULL, '部门删除', NULL, NULL, NULL, 'sys_dept_delete', NULL, 3, 1, 3, 0, 1, NULL, 24, 0, 0);
INSERT INTO `sys_menu` VALUES (32, '2022-12-02 14:11:34', '2022-12-06 22:49:24', 'admin', 'admin', '部门编辑', NULL, NULL, NULL, 'sys_dept_edit', NULL, 4, 1, 3, 0, 1, NULL, 24, 0, 0);
INSERT INTO `sys_menu` VALUES (33, '2022-12-06 10:54:04', NULL, 'admin', NULL, '用户导出', NULL, NULL, NULL, 'sys_user_export', NULL, 5, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (34, '2022-12-06 10:54:24', NULL, 'admin', NULL, '用户导入', NULL, NULL, NULL, 'sys_user_import', NULL, 6, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (35, '2022-12-06 13:39:30', NULL, 'admin', NULL, '密码重置', NULL, NULL, NULL, 'sys_user_pass_reset', NULL, 6, 1, 3, 0, 1, NULL, 2, 0, 0);
INSERT INTO `sys_menu` VALUES (36, '2022-12-06 14:54:51', '2022-12-06 16:37:22', 'admin', 'admin', '菜单查看', NULL, NULL, NULL, 'sys_menu_query', NULL, 1, 1, 3, 0, 1, NULL, 5, 0, 0);
INSERT INTO `sys_menu` VALUES (37, '2022-12-06 14:57:53', NULL, 'admin', NULL, '菜单新增', NULL, NULL, NULL, 'sys_menu_add', NULL, 2, 1, 3, 0, 1, NULL, 5, 0, 0);
INSERT INTO `sys_menu` VALUES (38, '2022-12-06 14:58:12', NULL, 'admin', NULL, '菜单修改', NULL, NULL, NULL, 'sys_menu_edit', NULL, 3, 1, 3, 0, 1, NULL, 5, 0, 0);
INSERT INTO `sys_menu` VALUES (39, '2022-12-06 14:58:39', NULL, 'admin', NULL, '菜单删除', NULL, NULL, NULL, 'sys_menu_delete', NULL, 4, 1, 3, 0, 1, NULL, 5, 0, 0);
INSERT INTO `sys_menu` VALUES (40, '2022-12-06 16:42:18', NULL, 'admin', NULL, '角色查询', NULL, NULL, NULL, 'sys_role_query', NULL, 1, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (41, '2022-12-06 16:42:37', NULL, 'admin', NULL, '角色新增', NULL, NULL, NULL, 'sys_role_add', NULL, 2, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (42, '2022-12-06 17:06:00', '2022-12-06 17:27:28', 'admin', 'admin', 'test', NULL, NULL, 'Navigation', NULL, 'client', 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (43, '2022-12-06 17:06:12', '2022-12-06 17:20:46', 'admin', 'admin', 'test1', NULL, NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 42, 1, NULL);
INSERT INTO `sys_menu` VALUES (44, '2022-12-06 17:06:51', '2022-12-06 17:08:59', 'admin', 'admin', 'test2', NULL, NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 43, 0, NULL);
INSERT INTO `sys_menu` VALUES (45, '2022-12-06 17:17:53', '2022-12-06 17:18:07', 'admin', 'admin', 'test2', 'test2', NULL, 'Navigation', NULL, 'client', 999, 1, 1, 0, 1, NULL, 43, 0, NULL);
INSERT INTO `sys_menu` VALUES (46, '2022-12-06 17:18:36', '2022-12-06 17:18:41', 'admin', 'admin', 'test2', 'test2', NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 43, 0, NULL);
INSERT INTO `sys_menu` VALUES (47, '2022-12-06 17:19:18', '2022-12-06 17:19:28', 'admin', 'admin', 'test2', NULL, NULL, 'Navigation', NULL, NULL, 999, 1, 1, 0, 1, NULL, 43, 0, NULL);
INSERT INTO `sys_menu` VALUES (48, '2022-12-06 17:22:26', '2022-12-06 17:22:38', 'admin', 'admin', 'test1', 't', NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 42, 0, NULL);
INSERT INTO `sys_menu` VALUES (49, '2022-12-06 17:22:56', '2022-12-06 17:23:21', 'admin', 'admin', 'test1', NULL, NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 42, 1, NULL);
INSERT INTO `sys_menu` VALUES (50, '2022-12-06 17:23:10', '2022-12-06 17:23:21', 'admin', 'admin', 'test2', NULL, NULL, 'Navigation', NULL, NULL, 999, 1, 1, 0, 1, NULL, 49, 0, NULL);
INSERT INTO `sys_menu` VALUES (51, '2022-12-06 18:46:10', '2022-12-06 18:50:23', 'admin', 'admin', 'ttt1', NULL, NULL, 'Navigation', NULL, 'dashboard', 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (52, '2022-12-06 20:30:07', NULL, 'admin', NULL, '角色修改', NULL, NULL, NULL, 'sys_role_edit', NULL, 3, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (53, '2022-12-06 20:30:36', NULL, 'admin', NULL, '角色删除', NULL, NULL, NULL, 'sys_role_delete', NULL, 4, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (54, '2022-12-06 20:31:18', '2022-12-06 20:31:25', 'admin', 'admin', '角色用户分配', NULL, NULL, NULL, 'sys_role_user_allocation', NULL, 5, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (55, '2022-12-06 20:31:53', NULL, 'admin', NULL, '角色菜单分配', NULL, NULL, NULL, 'sys_role_menu_allocation', NULL, 999, 1, 3, 0, 1, NULL, 4, 0, 0);
INSERT INTO `sys_menu` VALUES (56, '2022-12-06 22:29:13', NULL, 'admin', NULL, '客户端查询', NULL, NULL, NULL, 'sys_client_query', NULL, 1, 1, 3, 0, 1, NULL, 17, 0, 0);
INSERT INTO `sys_menu` VALUES (57, '2022-12-06 22:29:39', NULL, 'admin', NULL, '客户端新增', NULL, NULL, NULL, 'sys_client_add', NULL, 2, 1, 3, 0, 1, NULL, 17, 0, 0);
INSERT INTO `sys_menu` VALUES (58, '2022-12-06 22:33:21', NULL, 'admin', NULL, '客户端修改', NULL, NULL, NULL, 'sys_client_edit', NULL, 3, 1, 3, 0, 1, NULL, 17, 0, 0);
INSERT INTO `sys_menu` VALUES (59, '2022-12-06 22:33:48', NULL, 'admin', NULL, '客户端删除', NULL, NULL, NULL, 'sys_client_delete', NULL, 4, 1, 3, 0, 1, NULL, 17, 0, 0);
INSERT INTO `sys_menu` VALUES (60, '2022-12-07 13:48:08', '2022-12-07 14:09:17', 'admin', 'admin', 'teste', NULL, NULL, 'Navigation', NULL, 'client', 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (61, '2022-12-07 13:48:16', '2022-12-07 13:48:20', 'admin', 'admin', 'test2', NULL, NULL, 'Navigation', NULL, NULL, 999, 1, 1, 0, 1, NULL, 0, 0, NULL);
INSERT INTO `sys_menu` VALUES (62, '2022-12-07 14:10:00', '2022-12-07 14:10:29', 'admin', 'admin', '日志查询', NULL, NULL, NULL, 'sys_log_query', NULL, 1, 1, 3, 0, 1, NULL, 21, 0, 0);
INSERT INTO `sys_menu` VALUES (63, '2022-12-07 14:10:21', NULL, 'admin', NULL, '日志删除', NULL, NULL, NULL, 'sys_log_delete', NULL, 2, 1, 3, 0, 1, NULL, 21, 0, 0);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `seq` int(0) NOT NULL COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_post_code`(`post_code`) USING BTREE COMMENT '岗位编码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, '2022-08-18 08:47:21', NULL, 'admin', NULL, 'engineer', '工程师', 1, NULL);

-- ----------------------------
-- Table structure for sys_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_registered_client`;
CREATE TABLE `sys_registered_client`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端 ID',
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端密码',
  `client_secret_expires_at` datetime(0) NULL DEFAULT NULL COMMENT '客户端密码过期时间',
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端认证方式（client_secret_basic，client_secret_post，private_key_jwt，client_secret_jwt，none）',
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权类型（authorization_code,client_credentials,refresh_token,password）',
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向 URI',
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SCOPE',
  `require_authorization_consent` tinyint(0) NOT NULL DEFAULT 1 COMMENT '是否要求授权许可（1:=是；0:=否）',
  `access_token_time_to_live` int(0) NOT NULL COMMENT '访问令牌有效期（单位：s）',
  `access_token_format` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问令牌格式（self-contained, reference）',
  `refresh_token_time_to_live` int(0) NULL DEFAULT NULL COMMENT '刷新令牌有效期（单位：s）',
  `deleted` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除（0:=未删除;null:=已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_registered_client
-- ----------------------------
INSERT INTO `sys_registered_client` VALUES (1, '2022-11-14 14:14:17', '2022-12-06 22:34:20', 'admin', 'admin', 'admin', '{bcrypt}$2a$10$GLkoQfOQqaggcXog1LYS5.FgqezeOknNsYusUmaf17RdcDXz.Yec.', NULL, 'pi-cloud', 'client_secret_basic', 'refresh_token,client_credentials,password,authorization_code', 'http://127.0.0.1:8080/authorized', 'server', 1, 14400, 'self-contained', 2592000, 0);
INSERT INTO `sys_registered_client` VALUES (11, '2022-11-15 22:15:50', '2022-11-16 13:50:23', NULL, 'admin', 'pi', '{bcrypt}$2a$10$4VkSp8UyrAYTeyn9gIbqLOpySOQrfamKVmsXuxwwFrLTXyIe6lS5K', NULL, 'pi', 'client_secret_basic', 'client_credentials,password,refresh_token', NULL, 'test', 1, 60, 'self-contained', 120, 0);
INSERT INTO `sys_registered_client` VALUES (15, '2022-11-15 22:36:44', '2022-11-15 22:37:54', 'admin', 'admin', 'test', '{bcrypt}$2a$10$qoDfG6forEmRkhtaCWAR5e993Wb9v9ILdLZ9.S4/XpE5jy9kpIgOq', NULL, 'test', 'client_secret_basic', 'authorization_code', NULL, 'test', 1, 14400, 'self-contained', 2592000, NULL);
INSERT INTO `sys_registered_client` VALUES (16, '2022-11-16 14:08:04', '2022-11-16 14:08:27', 'admin', 'admin', 'test', '{bcrypt}$2a$10$3L5qaC6NWw2fMXeJWOcQSOW8veWf1sN0DlrqPK7ON..GJ8OeOu17G', NULL, 'test', 'client_secret_basic', '', NULL, 'test', 1, 14400, 'self-contained', 2592000, NULL);
INSERT INTO `sys_registered_client` VALUES (17, '2022-11-16 14:08:17', '2022-11-16 14:08:27', 'admin', 'admin', 'test2', '{bcrypt}$2a$10$FCZJhIhiQ7e7ZR6FLtO0De779DCnHeze4YVHllE8wGzTTvQm878li', NULL, 'test2', 'client_secret_basic', '', NULL, 'test', 1, 14400, 'self-contained', 2592000, NULL);
INSERT INTO `sys_registered_client` VALUES (18, '2022-11-16 14:13:21', '2022-11-16 14:13:25', 'admin', 'admin', 'test', '{bcrypt}$2a$10$5oGZiSOsCyjuTu3f5U7yf.KwAK4MAYzJkkl5s32/11eQ9rmm9p3xi', NULL, 'test', 'client_secret_basic', 'client_credentials', NULL, 'test', 1, 14400, 'self-contained', 2592000, NULL);
INSERT INTO `sys_registered_client` VALUES (19, '2022-12-06 22:25:19', '2022-12-06 22:38:58', 'admin', 'admin', 'test', '{bcrypt}$2a$10$6ZMK9bB/zHjihXktoVSTRuxHLrSaFRGf9f8xU3vK7ZamyaEmzNNaS', NULL, 'test', 'client_secret_basic', 'client_credentials', NULL, 'test', 1, 14400, 'self-contained', 2592000, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除（0:=未删除;null:=已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_code`(`role_code`, `deleted`) USING BTREE,
  UNIQUE INDEX `uk_role_name`(`name`, `deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2022-08-18 08:48:32', NULL, 'admin', NULL, '管理员', 'ADMIN', NULL, 0);
INSERT INTO `sys_role` VALUES (2, '2022-08-18 08:49:06', NULL, 'admin', NULL, '普通用户', 'USER', NULL, 0);
INSERT INTO `sys_role` VALUES (3, '2022-09-25 14:35:47', '2022-09-25 14:45:12', 'admin', 'admin', '包装1', 'Pack', '包装', NULL);
INSERT INTO `sys_role` VALUES (4, '2022-09-25 14:36:55', NULL, 'admin', NULL, '测试', 'TEST', '测试', NULL);
INSERT INTO `sys_role` VALUES (5, '2022-09-25 14:45:31', NULL, 'admin', NULL, '包装2', 'PACK2', '包装2', NULL);
INSERT INTO `sys_role` VALUES (8, '2022-09-25 22:09:46', NULL, 'admin', NULL, 'test1', 'test1', 'test', NULL);
INSERT INTO `sys_role` VALUES (9, '2022-09-25 22:09:55', NULL, 'admin', NULL, 'test2', 'test2', 'test2', NULL);
INSERT INTO `sys_role` VALUES (10, '2022-09-25 22:10:01', NULL, 'admin', NULL, 'test3', 'test3', 't3', NULL);
INSERT INTO `sys_role` VALUES (11, '2022-09-25 22:10:09', NULL, 'admin', NULL, 'test4', 'test4', 'te', NULL);
INSERT INTO `sys_role` VALUES (12, '2022-09-25 22:10:15', NULL, 'admin', NULL, 'test5', 'test5', NULL, NULL);
INSERT INTO `sys_role` VALUES (13, '2022-09-25 22:10:23', NULL, 'admin', NULL, 'test6', 'test6', 'te6', NULL);
INSERT INTO `sys_role` VALUES (14, '2022-09-25 22:10:32', NULL, 'admin', NULL, 'test7', 'tert7', NULL, NULL);
INSERT INTO `sys_role` VALUES (15, '2022-09-25 22:10:42', NULL, 'admin', NULL, 'test22', 'test22', NULL, NULL);
INSERT INTO `sys_role` VALUES (16, '2022-09-25 22:11:00', NULL, 'admin', NULL, 'test33', 'test33', NULL, NULL);
INSERT INTO `sys_role` VALUES (17, '2022-09-25 22:11:07', NULL, 'admin', NULL, 'test44', 'test44', NULL, NULL);
INSERT INTO `sys_role` VALUES (18, '2022-09-25 22:11:24', NULL, 'admin', NULL, 'test55', 'test55', NULL, NULL);
INSERT INTO `sys_role` VALUES (19, '2022-09-25 22:11:33', NULL, 'admin', NULL, 'test66', 'test66', NULL, NULL);
INSERT INTO `sys_role` VALUES (21, '2022-12-06 20:35:42', '2022-12-06 21:08:27', 'admin', 'admin', 'test', 'test', 'test1', NULL);
INSERT INTO `sys_role` VALUES (22, '2022-12-07 13:52:01', '2022-12-07 13:52:13', 'admin', 'admin', 'test', 'test', 'test', NULL);
INSERT INTO `sys_role` VALUES (23, '2022-12-07 13:52:08', '2022-12-07 14:00:31', 'admin', 'admin', 'test1', 'test1', 'test1', NULL);
INSERT INTO `sys_role` VALUES (24, '2022-12-07 14:00:27', '2022-12-07 14:00:31', 'admin', 'admin', 'test2', 'test2', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(0) UNSIGNED NOT NULL COMMENT '角色 ID',
  `menu_id` bigint(0) UNSIGNED NOT NULL COMMENT '菜单 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_id_menu_id`(`role_id`, `menu_id`) USING BTREE COMMENT '角色 ID, 菜单 ID 唯一索引',
  INDEX `fk_sys_role_menu_menu_id`(`menu_id`) USING BTREE,
  CONSTRAINT `fk_sys_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sys_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (12, 1, 2);
INSERT INTO `sys_role_menu` VALUES (24, 1, 3);
INSERT INTO `sys_role_menu` VALUES (49, 1, 4);
INSERT INTO `sys_role_menu` VALUES (5, 1, 5);
INSERT INTO `sys_role_menu` VALUES (22, 1, 14);
INSERT INTO `sys_role_menu` VALUES (27, 1, 15);
INSERT INTO `sys_role_menu` VALUES (26, 1, 16);
INSERT INTO `sys_role_menu` VALUES (25, 1, 17);
INSERT INTO `sys_role_menu` VALUES (31, 1, 19);
INSERT INTO `sys_role_menu` VALUES (30, 1, 20);
INSERT INTO `sys_role_menu` VALUES (32, 1, 21);
INSERT INTO `sys_role_menu` VALUES (33, 1, 22);
INSERT INTO `sys_role_menu` VALUES (40, 1, 23);
INSERT INTO `sys_role_menu` VALUES (35, 1, 24);
INSERT INTO `sys_role_menu` VALUES (44, 1, 29);
INSERT INTO `sys_role_menu` VALUES (62, 1, 30);
INSERT INTO `sys_role_menu` VALUES (37, 1, 31);
INSERT INTO `sys_role_menu` VALUES (36, 1, 32);
INSERT INTO `sys_role_menu` VALUES (42, 1, 33);
INSERT INTO `sys_role_menu` VALUES (41, 1, 34);
INSERT INTO `sys_role_menu` VALUES (43, 1, 35);
INSERT INTO `sys_role_menu` VALUES (45, 1, 36);
INSERT INTO `sys_role_menu` VALUES (47, 1, 37);
INSERT INTO `sys_role_menu` VALUES (46, 1, 38);
INSERT INTO `sys_role_menu` VALUES (48, 1, 39);
INSERT INTO `sys_role_menu` VALUES (50, 1, 40);
INSERT INTO `sys_role_menu` VALUES (55, 1, 41);
INSERT INTO `sys_role_menu` VALUES (51, 1, 52);
INSERT INTO `sys_role_menu` VALUES (53, 1, 53);
INSERT INTO `sys_role_menu` VALUES (52, 1, 54);
INSERT INTO `sys_role_menu` VALUES (54, 1, 55);
INSERT INTO `sys_role_menu` VALUES (57, 1, 56);
INSERT INTO `sys_role_menu` VALUES (65, 1, 57);
INSERT INTO `sys_role_menu` VALUES (58, 1, 58);
INSERT INTO `sys_role_menu` VALUES (56, 1, 59);
INSERT INTO `sys_role_menu` VALUES (64, 1, 62);
INSERT INTO `sys_role_menu` VALUES (63, 1, 63);
INSERT INTO `sys_role_menu` VALUES (13, 2, 1);
INSERT INTO `sys_role_menu` VALUES (17, 2, 2);
INSERT INTO `sys_role_menu` VALUES (16, 2, 4);
INSERT INTO `sys_role_menu` VALUES (18, 2, 14);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '呢称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg' COMMENT '头像',
  `sex` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '性别 (1:=男，2:=女)',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `bday` date NULL DEFAULT NULL COMMENT '生日',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门 ID',
  `enabled` tinyint(0) NOT NULL DEFAULT 1 COMMENT '状态（0:=禁用，1:=启用）',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除（0:=未删除，NULL:=已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username_deleted`(`username`, `deleted`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2022-08-16 14:29:55', '2022-12-06 14:07:42', 'admin', 'admin', 'admin', '超级管理员', '{bcrypt}$2a$10$GLkoQfOQqaggcXog1LYS5.FgqezeOknNsYusUmaf17RdcDXz.Yec.', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', 2, '15019013139', '1960-12-29', 1, 1, 0);
INSERT INTO `sys_user` VALUES (2, '2022-08-18 19:11:33', '2022-09-06 17:05:41', 'admin', 'admin', 'test', '测试', '{bcrypt}$2a$10$dp3YBZVsQGzIRXjvN7/SteUN4v1K/5lpeEOEZtQGNAlYaFb8KhuDW', NULL, 1, '15019093933', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (3, NULL, NULL, NULL, NULL, 'test', '张三', '{bcrypt}$2a$10$76uxh8z3VCOxWgIIj0.vgescQ9Q0UwtTn2KvkVeMzzgbXVG1YnoBu', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (5, NULL, NULL, NULL, NULL, 'test1', 'test', '{bcrypt}$2a$10$tm9ilxWzNiRPp32.rQLsR.TPM7Cyk9ughe5U9rqhUIayZwiUEQA/.', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (6, NULL, NULL, NULL, NULL, 'test2', 'test', '{bcrypt}$2a$10$Ye3JNtBhgK520Y.RMcAK0eNKwOMAkJhsb8sXcayOr3/lVVbkH59KW', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (8, NULL, NULL, NULL, NULL, 'test3', 'test', '{bcrypt}$2a$10$Dn.7MY5T.0iJGtTqRtEWMu/WZmmV52KQ/uJXo5UgqSMXqqgNo9cH6', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (9, '2022-08-30 15:28:42', NULL, 'admin', NULL, 'test4', 'test', '{bcrypt}$2a$10$.GXQ0Ny/O5UOLMVart2jR.j0ra4JCvsvVax7HDo5ZBQSw0u1p/KNO', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (10, '2022-08-30 16:17:49', '2022-09-02 16:50:44', 'admin', 'admin', 'test5', 'test', '{bcrypt}$2a$10$jHQ38RpgrJ5jGs0hlqDK6.yOJ3ImNVAMg68YVC1rYyKbujFLb5iAm', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (13, '2022-09-14 17:17:59', '2022-12-06 14:08:49', 'admin', 'zhangsan', 'zhangsan', '张三', '{bcrypt}$2a$10$KHaqPnO8i/ii9J8ZMTG6YeGcw/nwtYLvwc4PxUaS.Yz9IJV5.pf5q', 'http://pi-minio:9000/pi-cloud/81a2605ab1d94a6ca75d36a2c1fc2c7d.png', 1, '15019093933', '2020-03-25', 3, 1, 0);
INSERT INTO `sys_user` VALUES (14, '2022-09-14 20:22:42', NULL, 'admin', NULL, 'test', 'test', '{bcrypt}$2a$10$8XaWzixSqJ9FN2XF/p5aD.q09sZGdenzOTFctBik/vQ3kHpFZzcK6', NULL, NULL, '15019093493', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (15, '2022-09-16 17:02:20', NULL, 'admin', NULL, 'J302440', '林珈斌', '{bcrypt}$2a$10$Jcd0/iBNO7hL5yK0ADyJeeDMyRB7ygGsXymPyUepnGvLpZ2QCqj1i', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (16, '2022-09-16 17:03:02', NULL, 'admin', NULL, 'J302440', '林珈斌', '{bcrypt}$2a$10$5zWEVZWMZiBlud/khdxJNekJlz4qWnuH5YVvAKFV2h3jG3Cn6tUFe', NULL, NULL, NULL, NULL, 2, 1, NULL);
INSERT INTO `sys_user` VALUES (19, '2022-09-17 08:11:05', NULL, 'admin', NULL, 'J302441', '张三', '{bcrypt}$2a$10$1MhPqT.ODDGHAcdocIkxquR.eUifCfqqtKxgFSK0i1OG3s0Cqdzky', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (20, '2022-09-17 08:11:05', NULL, 'admin', NULL, 'J302442', '李四', '{bcrypt}$2a$10$Z8sQcDUpGPTEKixPs3/nFuq7SKb19sn9baSD5HrOcutBcpBTkryrm', NULL, NULL, '15019093433', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (21, '2022-09-17 08:15:14', NULL, 'admin', NULL, 'J302440', '林珈斌', '{bcrypt}$2a$10$9hnw2u0KxnAPi8jb0pgxjeMiGZVy3LMKQPtXF7Ix0CYxKKBz.KxKC', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (22, '2022-09-17 08:15:14', NULL, 'admin', NULL, 'J302441', '张三', '{bcrypt}$2a$10$uq/UoLiC0qS/hpOUKrAWS.rC3BNDQhOo.dR4XPF0TZEoVe4vFQ2dC', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (23, '2022-09-17 08:15:14', NULL, 'admin', NULL, 'J302442', '李四', '{bcrypt}$2a$10$jjYa9AYu1/215hnC9M1FNeIe7AWJba5T.xGnYrTtseAjDUEqGbRya', NULL, NULL, '15019093433', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (24, '2022-09-17 08:18:53', NULL, 'admin', NULL, 'J302440', '林珈斌', '{bcrypt}$2a$10$bbxTADpohYVkiFFWvzt.DucQVkT1FojN0HRpTjANk1QMrygeYN4xC', NULL, NULL, NULL, NULL, 2, 1, NULL);
INSERT INTO `sys_user` VALUES (25, '2022-09-17 08:18:53', NULL, 'admin', NULL, 'J302441', '张三', '{bcrypt}$2a$10$BYp3PdqX1T7WM3Ump/q7F.sM3Fg8nA9OmlFl5tVybiT2ZJFCNmxs.', NULL, NULL, NULL, NULL, 2, 1, NULL);
INSERT INTO `sys_user` VALUES (26, '2022-09-17 08:18:53', NULL, 'admin', NULL, 'J302442', '李四', '{bcrypt}$2a$10$oxg9MA.RNyU4IEINvLOFGugXlv8bA4Tp41qThQhd1ZiyFRQaPkn0W', NULL, NULL, NULL, NULL, 2, 1, NULL);
INSERT INTO `sys_user` VALUES (27, '2022-09-17 08:20:57', NULL, 'admin', NULL, 'J302440', '林珈斌', '{bcrypt}$2a$10$jLcof7LPrfhkRKgWe1FXlOez.DrLsplsvVhYPMOrgb.EzF.auhkUS', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (28, '2022-09-17 08:20:57', NULL, 'admin', NULL, 'J302441', '张三', '{bcrypt}$2a$10$ro3XlQeqAqE5LhKEM7yO4unCee7n38qtIrsobaxYt9cuYIbCFvAQ2', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (29, '2022-09-17 08:20:57', '2022-09-17 09:36:11', 'admin', 'admin', 'J302442', '李四', '{bcrypt}$2a$10$udG7y2bertklUF1YbE2.muuEWfiH5fv1AQ.Ka5vaDY5LQpesJ1ITK', NULL, NULL, '15019095555', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (30, '2022-09-26 20:18:23', NULL, 'admin', NULL, 'test', 'test', '{bcrypt}$2a$10$cXezwTVhgGz51a45mFxnc.Mh7./z184sd36sFKTEn3gn9TvIl1JC6', NULL, NULL, '15019093493', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (31, '2022-09-26 20:19:14', NULL, 'zhangsan', NULL, 'test1', 'test1', '{bcrypt}$2a$10$Hg4pwb7tmrv5IV.bwxGtBuPmgBYxFOCeZuNmrLWf0YbRaNdIoCWym', NULL, NULL, '15019093493', NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (32, '2022-10-06 13:52:18', '2022-12-06 11:45:58', 'admin', 'admin', 'lisi', '李四', '{bcrypt}$2a$10$UZJRUcVoOQs4yx0ZJdwZKuWvdPfqftLzO5lFeyQ3FBSh1C7fVOSvu', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', 1, NULL, '2001-12-27', 2, 1, 0);
INSERT INTO `sys_user` VALUES (33, '2022-12-06 11:18:44', '2022-12-06 11:54:14', 'admin', 'admin', 'wangwu', '王五', '{bcrypt}$2a$10$IgkYDCte8YYgvbnupFtSiOrkDF8bE8grAMt5AevJ1hmNVBBbiQ86i', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', 2, '15019033568', '1981-12-23', 4, 1, NULL);
INSERT INTO `sys_user` VALUES (34, '2022-12-06 11:19:56', '2022-12-06 11:54:05', 'admin', 'admin', 'zhaoliu', '赵六', '{bcrypt}$2a$10$GRzHU.5MrlcaaoHzSXsTKOs4j19.JTZRQiEa0tOlxarUPmPvhbIqi', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', 1, NULL, '1996-02-15', NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (35, '2022-12-06 13:35:47', '2022-12-06 13:37:09', 'admin', 'admin', 'wangwu', '王五', '{bcrypt}$2a$10$tUzi.ib2nt.vVLzgkzi3qO/cFAhspLyuphmVRS6D1oXtQaallXWmK', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, NULL, NULL, 5, 1, NULL);
INSERT INTO `sys_user` VALUES (36, '2022-12-06 13:35:47', '2022-12-06 13:37:09', 'admin', 'admin', 'zhaoliu', '赵六', '{bcrypt}$2a$10$Azu0I8vc7UL165R4TIVxyOfGHRcd9nHmc9eHiiXBM/XNdJk9JKvLq', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, NULL, NULL, 5, 1, NULL);
INSERT INTO `sys_user` VALUES (37, '2022-12-06 13:49:46', NULL, 'admin', NULL, 'wangwu', '王五', '{bcrypt}$2a$10$q7qsPjUCFELpptZ4wqMvget5xD0vykBlRYf8p.FwAgKT1nQwEy1US', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, NULL, NULL, 5, 1, 0);
INSERT INTO `sys_user` VALUES (38, '2022-12-06 13:49:47', '2022-12-06 13:49:50', 'admin', 'admin', 'zhaoliu', '赵六', '{bcrypt}$2a$10$/na5zMgwPbb0TtvGtIyatuBPy9EwGBJ8JMU6OwG/np/uekhFOfAae', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, NULL, NULL, 5, 1, NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户 ID',
  `post_id` bigint(0) NULL DEFAULT NULL COMMENT '岗位 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id_post_id`(`user_id`, `post_id`) USING BTREE COMMENT '用户 ID, 岗位 ID 唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户 ID',
  `role_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id_role_id`(`user_id`, `role_id`) USING BTREE COMMENT '用户 ID，角色 ID 唯一索引',
  INDEX `fk_sys_user_role_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `fk_sys_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sys_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (20, 1, 1);
INSERT INTO `sys_user_role` VALUES (6, 13, 2);
INSERT INTO `sys_user_role` VALUES (7, 32, 2);
INSERT INTO `sys_user_role` VALUES (30, 32, 21);
INSERT INTO `sys_user_role` VALUES (22, 33, 1);
INSERT INTO `sys_user_role` VALUES (24, 34, 2);
INSERT INTO `sys_user_role` VALUES (25, 35, 2);
INSERT INTO `sys_user_role` VALUES (26, 36, 2);
INSERT INTO `sys_user_role` VALUES (27, 37, 2);
INSERT INTO `sys_user_role` VALUES (31, 37, 21);
INSERT INTO `sys_user_role` VALUES (28, 38, 2);

SET FOREIGN_KEY_CHECKS = 1;
