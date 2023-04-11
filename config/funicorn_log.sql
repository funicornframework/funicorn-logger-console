/*
Navicat MySQL Data Transfer

Source Server         : 百度(106.13.217.154)
Source Server Version : 80031
Source Host           : 106.13.217.154:3306
Source Database       : funicorn_log

Target Server Type    : MYSQL
Target Server Version : 80031
File Encoding         : 65001

Date: 2023-03-30 16:40:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `app_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端标识',
  `deleted` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `created_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_name_index` (`app_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端信息表';

-- ----------------------------
-- Table structure for app_node
-- ----------------------------
DROP TABLE IF EXISTS `app_node`;
CREATE TABLE `app_node` (
  `id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `app_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端标识',
  `ip` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP',
  `online` int NOT NULL DEFAULT '1' COMMENT '是否在线 0 离线 1在线',
  `heartbeat_time` datetime DEFAULT NULL COMMENT '心跳时间',
  `deleted` varchar(2) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `created_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_name_ip_index` (`app_name`,`ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for job_lock
-- ----------------------------
DROP TABLE IF EXISTS `job_lock`;
CREATE TABLE `job_lock` (
  `id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键id',
  `lock_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `lock_holder` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '持锁人',
  `lock_status` int NOT NULL DEFAULT '0' COMMENT '锁状态 0未锁 1已锁',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_lock_key` (`lock_key`) USING BTREE,
  KEY `index_expire_time` (`expire_time`) USING BTREE,
  KEY `index_lock_key_and_expire_time` (`lock_key`,`expire_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '中文描述',
  `operation_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型  update/delete/insert/query',
  `operation_time` datetime DEFAULT NULL COMMENT '用户操作时间',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作用户账号',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端标识',
  `remote_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求源ip地址',
  `function_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后台函数路径',
  `request_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求路径',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求参数',
  `request_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求类型 post/get/patch/delete',
  `cost_time` bigint DEFAULT NULL COMMENT '耗时 单位ms',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户id',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `log_username_index` (`username`) USING BTREE COMMENT '用户名索引',
  KEY `index_operation_time` (`operation_time`) USING BTREE,
  KEY `plural_log_index` (`operation_time`,`username`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户操作日志管理';

-- ----------------------------
-- Table structure for user_app
-- ----------------------------
DROP TABLE IF EXISTS `user_app`;
CREATE TABLE `user_app` (
  `id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `app_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端标识',
  PRIMARY KEY (`id`),
  KEY `user_app_index` (`user_name`,`app_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与客户端关系表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `nickname` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `username` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `user_type` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'normal' COMMENT '用户类型 Admin/Normal',
  `deleted` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  `created_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
