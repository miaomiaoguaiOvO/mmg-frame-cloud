/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : mmg_frame_cloud

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-04-15 17:41:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `permission_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `permission_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '拦截路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('4ec47e16eae1b590d4ca1c91c260f24a', 'Get测试', 'cloud-user-provider', 'GET/api/user/provider', '2022-04-15 16:09:22', '2022-04-15 16:09:22', '0');
INSERT INTO `permission` VALUES ('c916e101875d59f98b597540c7548e31', 'Post测试', 'cloud-user-provider', 'POST/api/user/provider', '2022-04-15 16:09:22', '2022-04-15 16:09:22', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名',
  `role_nickname` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '超级管理员', '2022-04-15 15:52:15', '2022-04-15 15:52:15', '0');
INSERT INTO `role` VALUES ('2', 'ROLE_USER', '普通用户', '2022-04-15 15:52:22', '2022-04-15 15:52:22', '0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色-权限id',
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色-权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '4ec47e16eae1b590d4ca1c91c260f24a', '2022-04-15 16:38:30', '2022-04-15 16:38:30', '0');
INSERT INTO `role_permission` VALUES ('2', '1', 'c916e101875d59f98b597540c7548e31', '2022-04-15 16:38:32', '2022-04-15 16:38:32', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `user_mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_active` tinyint NOT NULL COMMENT '是否激活',
  `user_locked` tinyint NOT NULL COMMENT '是否冻结',
  `user_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$ddihuO2Z85CZOT2TaUVqBuOoG/0di8wPXq1WjQc3IiYA4vFrBf5ri', '', '1', '0', null, '2022-03-25 13:56:15', '2022-03-25 13:56:15', '0');
INSERT INTO `sys_user` VALUES ('4b7d5eb1824444ba8ecc23beec6f0144', 'mmg', '$2a$10$b2nLnP3srfXxufsp/N5saOH2Z9kwwyIes4lRMobfAuvyV7OFvjWIy', '1733535060@qq.com1', '1', '0', null, '2021-07-19 14:00:00', '2021-07-19 14:00:00', '0');
INSERT INTO `sys_user` VALUES ('e3595ba439a4cc39a525d986e4ae83af', 'newuser', '$2a$10$ewKPXVzLV4/ydur4UW0C2unwIbOlMeXSmqrFIx28YpGLYT5t.CEZS', '1733535060@qq.com', '1', '0', null, '2021-07-19 14:02:58', '2021-07-19 14:02:58', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户-角色id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '2021-04-30 12:59:58', '2021-04-30 13:00:01', '0');
