-- 系统核心模块建表语句
-- 创建时间：2024-01-01
-- 版本：1.0.1-SNAPSHOT

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 权限表
-- ----------------------------
DROP TABLE IF EXISTS `syst_permission`;
CREATE TABLE `syst_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_code` varchar(128) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(128) NOT NULL COMMENT '权限名称',
  `description` varchar(256) DEFAULT NULL COMMENT '权限描述',
  `permission_type` varchar(32) NOT NULL COMMENT '权限类型（menu：菜单，button：按钮，api：接口）',
  `parent_id` bigint DEFAULT NULL COMMENT '父权限ID',
  `path` varchar(256) DEFAULT NULL COMMENT '权限路径',
  `icon` varchar(128) DEFAULT NULL COMMENT '权限图标',
  `sort_order` int DEFAULT NULL COMMENT '排序号',
  `status` int NOT NULL DEFAULT 1 COMMENT '权限状态（0：禁用，1：启用）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限表';

-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `syst_role`;
CREATE TABLE `syst_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `status` int NOT NULL DEFAULT 1 COMMENT '角色状态（0：禁用，1：启用）',
  `sort_order` int DEFAULT NULL COMMENT '排序号',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';

-- ----------------------------
-- 角色权限关联表
-- ----------------------------
DROP TABLE IF EXISTS `syst_role_permission`;
CREATE TABLE `syst_role_permission` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `idx_permission_id` (`permission_id`),
  CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `syst_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `syst_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关联表';

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `syst_user`;
CREATE TABLE `syst_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_code` varchar(64) NOT NULL COMMENT '用户编号',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(64) NOT NULL COMMENT '真实姓名',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `status` int NOT NULL DEFAULT 1 COMMENT '用户状态（0：禁用，1：启用）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_code` (`user_code`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';

-- ----------------------------
-- 用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `syst_user_role`;
CREATE TABLE `syst_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `idx_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `syst_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `syst_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';

-- ----------------------------
-- 系统参数表
-- ----------------------------
DROP TABLE IF EXISTS `syst_parameter`;
CREATE TABLE `syst_parameter` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `param_code` varchar(64) NOT NULL COMMENT '参数编码',
  `param_name` varchar(128) NOT NULL COMMENT '参数名称',
  `param_value` varchar(1024) DEFAULT NULL COMMENT '参数值',
  `param_type` varchar(32) NOT NULL DEFAULT 'STRING' COMMENT '参数类型（STRING: 字符串, NUMBER: 数字, BOOLEAN: 布尔, JSON: JSON对象）',
  `default_value` varchar(512) DEFAULT NULL COMMENT '默认值',
  `param_group` varchar(64) DEFAULT NULL COMMENT '参数分组',
  `description` varchar(512) DEFAULT NULL COMMENT '参数描述',
  `is_system` int NOT NULL DEFAULT 0 COMMENT '是否为系统参数（0：否，1：是）',
  `is_editable` int NOT NULL DEFAULT 1 COMMENT '是否可编辑（0：否，1：是）',
  `sort_order` int DEFAULT 0 COMMENT '排序号',
  `status` int NOT NULL DEFAULT 1 COMMENT '参数状态（0：禁用，1：启用）',
  `last_modifier` varchar(64) DEFAULT NULL COMMENT '最后修改人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_param_code` (`param_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统参数表';

-- ----------------------------
-- 登录日志表
-- ----------------------------
DROP TABLE IF EXISTS `syst_login_log`;
CREATE TABLE `syst_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `login_ip` varchar(64) DEFAULT NULL COMMENT '登录IP',
  `login_location` varchar(128) DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(128) DEFAULT NULL COMMENT '浏览器类型',
  `operating_system` varchar(128) DEFAULT NULL COMMENT '操作系统',
  `login_status` int NOT NULL COMMENT '登录状态（0：失败，1：成功）',
  `login_message` varchar(256) DEFAULT NULL COMMENT '登录消息（失败原因等）',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '登出时间',
  `online_duration` bigint DEFAULT NULL COMMENT '在线时长（分钟）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户登录日志表';

-- ----------------------------
-- 字典表
-- ----------------------------
DROP TABLE IF EXISTS `syst_dictionary`;
CREATE TABLE `syst_dictionary` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_code` varchar(64) NOT NULL COMMENT '字典编码',
  `dict_name` varchar(128) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(32) NOT NULL COMMENT '字典类型（system：系统字典，business：业务字典）',
  `status` int NOT NULL DEFAULT 1 COMMENT '字典状态（0：禁用，1：启用）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_code` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统数据字典表（主表）';

-- ----------------------------
-- 字典项表
-- ----------------------------
DROP TABLE IF EXISTS `syst_dictionary_item`;
CREATE TABLE `syst_dictionary_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  `item_code` varchar(64) NOT NULL COMMENT '字典项编码',
  `item_value` varchar(256) NOT NULL COMMENT '字典项值',
  `item_label` varchar(128) NOT NULL COMMENT '字典项标签',
  `sort_order` int DEFAULT NULL COMMENT '排序号',
  `status` int NOT NULL DEFAULT 1 COMMENT '字典项状态（0：禁用，1：启用）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `dictionary_id` bigint NOT NULL COMMENT '所属字典ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `entity_version` bigint DEFAULT NULL COMMENT '版本号（用于乐观锁）',
  PRIMARY KEY (`id`),
  KEY `idx_dictionary_id` (`dictionary_id`),
  CONSTRAINT `fk_dictionary_item_dictionary` FOREIGN KEY (`dictionary_id`) REFERENCES `syst_dictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统数据字典项表（从表）';

SET FOREIGN_KEY_CHECKS = 1;
