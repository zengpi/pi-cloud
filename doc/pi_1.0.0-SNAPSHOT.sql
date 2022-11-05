/*
 Navicat Premium Data Transfer

 Source Server         : ZnPi
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : pi:3306
 Source Schema         : pi

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 04/11/2022 20:46:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_id_issued_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret_expires_at` timestamp(0) NULL DEFAULT NULL,
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` VALUES ('883339b7-5df2-40aa-b990-cacc7be5a715', 'messaging-client', '2022-10-22 10:07:35', '{noop}secret', NULL, '883339b7-5df2-40aa-b990-cacc7be5a715', 'client_secret_basic', 'refresh_token,client_credentials,password,authorization_code', 'http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc', 'openid,message.read,message.write', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');
INSERT INTO `oauth2_registered_client` VALUES ('f5e35387-231b-4cef-8e4f-f95b2772298f', 'admin', '2022-10-22 22:36:25', '{bcrypt}$2a$10$GLkoQfOQqaggcXog1LYS5.FgqezeOknNsYusUmaf17RdcDXz.Yec.', NULL, 'f5e35387-231b-4cef-8e4f-f95b2772298f', 'client_secret_basic', 'refresh_token,client_credentials,password,authorization_code', 'http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc', 'server', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",60.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",2592000.000000000]}');

-- ----------------------------
-- Table structure for sys_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `client_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端 ID',
  `client_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端秘钥',
  `grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权类型',
  `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '范围',
  `access_token_validity` int(0) NULL DEFAULT NULL COMMENT '刷新令牌有效期（单位：秒）',
  `refresh_token_validity` int(0) NULL DEFAULT NULL COMMENT '访问令牌有效期（单位：秒）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_client_id`(`client_id`) USING BTREE COMMENT '客户端 ID'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OAuth 客户端表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO `sys_client` VALUES (1, '2022-08-13 10:00:00', '2022-08-13 10:00:00', 'admin', NULL, 'admin', '{bcrypt}$2a$10$elqPs0LK87ulzb0vcQXfze3TNBvxwB2ntj.0LDMfhGWrLl6sjhHkS', 'authorization_code,refresh_token,client_credentials,password', 'http://127.0.0.1:8000/authorized', 'server', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `sort` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `parent_id` bigint(0) UNSIGNED NULL DEFAULT 0 COMMENT '父节点，根节点为 0',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除（0:=未删除, 1:=已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dept_name`(`name`) USING BTREE COMMENT '部门名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '2022-08-16 14:27:55', NULL, 'admin', NULL, '资讯部', 1, 0, 0);
INSERT INTO `sys_dept` VALUES (2, '2022-09-04 11:39:56', NULL, 'admin', NULL, '硬件组', 2, 1, 0);
INSERT INTO `sys_dept` VALUES (3, '2022-09-04 11:40:41', NULL, 'admin', NULL, '软件组', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
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
  `parent_id` bigint(0) NOT NULL COMMENT '父菜单 ID（-1表示跟菜单）',
  `deleted` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除(0:=未删除; null:=已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '2022-08-18 08:40:23', NULL, 'admin', NULL, '系统管理', '/system', NULL, 'Navigation', NULL, 'system', 1, 1, 1, 0, 1, '/system/user', -1, 0);
INSERT INTO `sys_menu` VALUES (2, '2022-08-18 08:42:45', NULL, 'admin', NULL, '用户管理', 'user', 'SystemUser', 'system/user/Index', NULL, 'user', 1, 1, 2, 0, 1, NULL, 1, 0);
INSERT INTO `sys_menu` VALUES (3, '2022-08-18 08:45:24', '2022-09-26 20:28:58', 'admin', 'admin', '用户新增', '', NULL, '', 'sys_user_add', NULL, 2, 1, 3, 0, 1, NULL, 2, 0);
INSERT INTO `sys_menu` VALUES (4, '2022-08-28 17:48:21', '2022-09-27 07:48:38', 'admin', 'admin', '角色管理', 'role', 'SystemRole', 'system/role/Index', NULL, 'role', 3, 1, 2, 0, 1, NULL, 1, 0);
INSERT INTO `sys_menu` VALUES (5, '2022-09-19 14:17:23', '2022-09-27 07:48:44', 'admin', 'admin', '菜单管理', 'menu', 'SystemMenu', 'system/menu/Index', NULL, 'menu', 2, 1, 2, 0, 1, NULL, 1, 0);
INSERT INTO `sys_menu` VALUES (13, '2022-09-24 11:19:18', NULL, 'admin', NULL, 'test', '/test', '', 'Navigation', NULL, NULL, 999, 1, 1, 0, 1, NULL, -1, NULL);
INSERT INTO `sys_menu` VALUES (14, '2022-09-26 20:21:08', '2022-09-26 20:28:50', 'admin', 'admin', '用户查询', NULL, NULL, NULL, 'sys_user_query', NULL, 1, 1, 3, 0, 1, NULL, 2, 0);
INSERT INTO `sys_menu` VALUES (15, '2022-09-26 20:29:37', NULL, 'admin', NULL, '用户删除', NULL, NULL, NULL, 'sys_user_delete', NULL, 3, 1, 3, 0, 1, NULL, 2, 0);
INSERT INTO `sys_menu` VALUES (16, '2022-09-26 20:30:06', '2022-09-26 20:30:14', 'admin', 'admin', '用户修改', NULL, NULL, NULL, 'sys_user_edit', NULL, 4, 1, 3, 0, 1, NULL, 2, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, '2022-08-18 08:47:21', NULL, 'admin', NULL, 'engineer', '工程师', 1, NULL);

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
  UNIQUE INDEX `uk_role_name`(`name`) USING BTREE COMMENT '角色名称唯一索引',
  UNIQUE INDEX `uk_role_code`(`role_code`) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(0) NOT NULL COMMENT '角色 ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_id_menu_id`(`role_id`, `menu_id`) USING BTREE COMMENT '角色 ID, 菜单 ID 唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (12, 1, 2);
INSERT INTO `sys_role_menu` VALUES (24, 1, 3);
INSERT INTO `sys_role_menu` VALUES (4, 1, 4);
INSERT INTO `sys_role_menu` VALUES (5, 1, 5);
INSERT INTO `sys_role_menu` VALUES (22, 1, 14);
INSERT INTO `sys_role_menu` VALUES (21, 1, 15);
INSERT INTO `sys_role_menu` VALUES (20, 1, 16);
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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2022-08-16 14:29:55', NULL, 'admin', NULL, 'admin', '超级管理员', '{bcrypt}$2a$10$GLkoQfOQqaggcXog1LYS5.FgqezeOknNsYusUmaf17RdcDXz.Yec.', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, '15019013137', NULL, 1, 1, 0);
INSERT INTO `sys_user` VALUES (2, '2022-08-18 19:11:33', '2022-09-06 17:05:41', 'admin', 'admin', 'test', '测试', '{bcrypt}$2a$10$dp3YBZVsQGzIRXjvN7/SteUN4v1K/5lpeEOEZtQGNAlYaFb8KhuDW', NULL, 1, '15019093933', NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (3, NULL, NULL, NULL, NULL, 'test', '张三', '{bcrypt}$2a$10$76uxh8z3VCOxWgIIj0.vgescQ9Q0UwtTn2KvkVeMzzgbXVG1YnoBu', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (5, NULL, NULL, NULL, NULL, 'test1', 'test', '{bcrypt}$2a$10$tm9ilxWzNiRPp32.rQLsR.TPM7Cyk9ughe5U9rqhUIayZwiUEQA/.', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (6, NULL, NULL, NULL, NULL, 'test2', 'test', '{bcrypt}$2a$10$Ye3JNtBhgK520Y.RMcAK0eNKwOMAkJhsb8sXcayOr3/lVVbkH59KW', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (8, NULL, NULL, NULL, NULL, 'test3', 'test', '{bcrypt}$2a$10$Dn.7MY5T.0iJGtTqRtEWMu/WZmmV52KQ/uJXo5UgqSMXqqgNo9cH6', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (9, '2022-08-30 15:28:42', NULL, 'admin', NULL, 'test4', 'test', '{bcrypt}$2a$10$.GXQ0Ny/O5UOLMVart2jR.j0ra4JCvsvVax7HDo5ZBQSw0u1p/KNO', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (10, '2022-08-30 16:17:49', '2022-09-02 16:50:44', 'admin', 'admin', 'test5', 'test', '{bcrypt}$2a$10$jHQ38RpgrJ5jGs0hlqDK6.yOJ3ImNVAMg68YVC1rYyKbujFLb5iAm', NULL, NULL, NULL, NULL, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (13, '2022-09-14 17:17:59', NULL, 'admin', NULL, 'zhangsan', '张三', '{bcrypt}$2a$10$MorbwUoy91hUCfhJqvFRa.B4fqj55mBaMiOPqFrkNciq6AHjStFKy', NULL, NULL, '15019093933', NULL, 3, 1, 0);
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
INSERT INTO `sys_user` VALUES (32, '2022-10-06 13:52:18', NULL, 'admin', NULL, 'lisi', '李四', '{bcrypt}$2a$10$UZJRUcVoOQs4yx0ZJdwZKuWvdPfqftLzO5lFeyQ3FBSh1C7fVOSvu', 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', NULL, NULL, NULL, 2, 1, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户 ID',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id_role_id`(`user_id`, `role_id`) USING BTREE COMMENT '用户 ID，角色 ID 唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (6, 13, 2);
INSERT INTO `sys_user_role` VALUES (7, 32, 2);

SET FOREIGN_KEY_CHECKS = 1;
