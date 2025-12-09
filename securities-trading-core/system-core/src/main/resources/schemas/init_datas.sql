-- 系统核心模块初始化数据
-- 创建时间：2024-01-01
-- 版本：1.0.1-SNAPSHOT

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 初始化系统权限数据
-- ----------------------------
INSERT INTO `syst_permission` (`id`, `permission_code`, `permission_name`, `description`, `permission_type`, `parent_id`, `path`, `icon`, `sort_order`, `status`) VALUES
(1, 'system', '系统管理', '系统管理模块', 'menu', NULL, '/system', 'system', 1, 1),
(2, 'system_user', '用户管理', '用户管理菜单', 'menu', 1, '/system/user', 'user', 2, 1),
(3, 'system_user_view', '查看用户', '查看用户权限', 'button', 2, '/system/user/view', NULL, 1, 1),
(4, 'system_user_add', '添加用户', '添加用户权限', 'button', 2, '/system/user/add', NULL, 2, 1),
(5, 'system_user_edit', '编辑用户', '编辑用户权限', 'button', 2, '/system/user/edit', NULL, 3, 1),
(6, 'system_user_delete', '删除用户', '删除用户权限', 'button', 2, '/system/user/delete', NULL, 4, 1),
(7, 'system_role', '角色管理', '角色管理菜单', 'menu', 1, '/system/role', 'role', 3, 1),
(8, 'system_role_view', '查看角色', '查看角色权限', 'button', 7, '/system/role/view', NULL, 1, 1),
(9, 'system_role_add', '添加角色', '添加角色权限', 'button', 7, '/system/role/add', NULL, 2, 1),
(10, 'system_role_edit', '编辑角色', '编辑角色权限', 'button', 7, '/system/role/edit', NULL, 3, 1),
(11, 'system_role_delete', '删除角色', '删除角色权限', 'button', 7, '/system/role/delete', NULL, 4, 1),
(12, 'system_permission', '权限管理', '权限管理菜单', 'menu', 1, '/system/permission', 'permission', 4, 1),
(13, 'system_permission_view', '查看权限', '查看权限权限', 'button', 12, '/system/permission/view', NULL, 1, 1),
(14, 'system_permission_add', '添加权限', '添加权限权限', 'button', 12, '/system/permission/add', NULL, 2, 1),
(15, 'system_permission_edit', '编辑权限', '编辑权限权限', 'button', 12, '/system/permission/edit', NULL, 3, 1),
(16, 'system_permission_delete', '删除权限', '删除权限权限', 'button', 12, '/system/permission/delete', NULL, 4, 1),
(17, 'system_parameter', '参数管理', '参数管理菜单', 'menu', 1, '/system/parameter', 'parameter', 5, 1),
(18, 'system_parameter_view', '查看参数', '查看参数权限', 'button', 17, '/system/parameter/view', NULL, 1, 1),
(19, 'system_parameter_add', '添加参数', '添加参数权限', 'button', 17, '/system/parameter/add', NULL, 2, 1),
(20, 'system_parameter_edit', '编辑参数', '编辑参数权限', 'button', 17, '/system/parameter/edit', NULL, 3, 1),
(21, 'system_parameter_delete', '删除参数', '删除参数权限', 'button', 17, '/system/parameter/delete', NULL, 4, 1),
(22, 'system_dictionary', '字典管理', '字典管理菜单', 'menu', 1, '/system/dictionary', 'dictionary', 6, 1),
(23, 'system_dictionary_view', '查看字典', '查看字典权限', 'button', 22, '/system/dictionary/view', NULL, 1, 1),
(24, 'system_dictionary_add', '添加字典', '添加字典权限', 'button', 22, '/system/dictionary/add', NULL, 2, 1),
(25, 'system_dictionary_edit', '编辑字典', '编辑字典权限', 'button', 22, '/system/dictionary/edit', NULL, 3, 1),
(26, 'system_dictionary_delete', '删除字典', '删除字典权限', 'button', 22, '/system/dictionary/delete', NULL, 4, 1),
(27, 'system_login_log', '登录日志', '登录日志菜单', 'menu', 1, '/system/login-log', 'login-log', 7, 1),
(28, 'system_login_log_view', '查看登录日志', '查看登录日志权限', 'button', 27, '/system/login-log/view', NULL, 1, 1);

-- ----------------------------
-- 初始化角色数据
-- ----------------------------
INSERT INTO `syst_role` (`id`, `role_code`, `role_name`, `description`, `status`, `sort_order`) VALUES
(1, 'admin', '系统管理员', '系统最高权限角色', 1, 1),
(2, 'user', '普通用户', '普通用户角色', 1, 2);

-- ----------------------------
-- 初始化角色权限关联数据
-- ----------------------------
-- 系统管理员拥有所有权限
INSERT INTO `syst_role_permission` (`role_id`, `permission_id`) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
(1, 7), (1, 8), (1, 9), (1, 10), (1, 11),
(1, 12), (1, 13), (1, 14), (1, 15), (1, 16),
(1, 17), (1, 18), (1, 19), (1, 20), (1, 21),
(1, 22), (1, 23), (1, 24), (1, 25), (1, 26),
(1, 27), (1, 28);

-- ----------------------------
-- 初始化用户数据
-- 密码：admin123（加密存储）
-- ----------------------------
INSERT INTO `syst_user` (`id`, `user_code`, `username`, `password`, `real_name`, `email`, `phone`, `status`) VALUES
(1, 'admin', 'admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '系统管理员', 'admin@ijupiter.com', '13800138000', 1),
(2, 'test', 'test', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '测试用户', 'test@ijupiter.com', '13800138001', 1);

-- ----------------------------
-- 初始化用户角色关联数据
-- ----------------------------
INSERT INTO `syst_user_role` (`user_id`, `role_id`) VALUES
(1, 1), -- 系统管理员分配系统管理员角色
(2, 2); -- 测试用户分配普通用户角色

-- ----------------------------
-- 初始化系统参数数据
-- ----------------------------
INSERT INTO `syst_parameter` (`id`, `param_code`, `param_name`, `param_value`, `param_type`, `default_value`, `param_group`, `description`, `is_system`, `is_editable`, `sort_order`, `status`) VALUES
(1, 'system_name', '系统名称', 'iJupiter Trading System', 'STRING', 'iJupiter Trading System', 'system', '系统显示名称', 1, 1, 1, 1),
(2, 'system_version', '系统版本', '1.0.1-SNAPSHOT', 'STRING', '1.0.1-SNAPSHOT', 'system', '系统版本号', 1, 0, 2, 1),
(3, 'session_timeout', '会话超时时间', '30', 'NUMBER', '30', 'security', '会话超时时间（分钟）', 1, 1, 3, 1),
(4, 'password_expire_days', '密码过期天数', '90', 'NUMBER', '90', 'security', '用户密码过期天数', 1, 1, 4, 1),
(5, 'password_min_length', '密码最小长度', '8', 'NUMBER', '8', 'security', '用户密码最小长度', 1, 1, 5, 1),
(6, 'login_fail_count', '登录失败次数限制', '5', 'NUMBER', '5', 'security', '登录失败次数限制', 1, 1, 6, 1),
(7, 'login_lock_minutes', '登录锁定时间', '15', 'NUMBER', '15', 'security', '登录失败锁定时间（分钟）', 1, 1, 7, 1),
(8, 'log_retention_days', '日志保留天数', '90', 'NUMBER', '90', 'system', '系统日志保留天数', 1, 1, 8, 1),
(9, 'enable_captcha', '启用验证码', '1', 'BOOLEAN', '1', 'security', '是否启用登录验证码（0：禁用，1：启用）', 1, 1, 9, 1),
(10, 'enable_password_strength_check', '启用密码强度检查', '1', 'BOOLEAN', '1', 'security', '是否启用密码强度检查（0：禁用，1：启用）', 1, 1, 10, 1);

-- ----------------------------
-- 初始化字典数据
-- ----------------------------
INSERT INTO `syst_dictionary` (`id`, `dict_code`, `dict_name`, `dict_type`, `status`, `remark`) VALUES
(1, 'user_status', '用户状态', 'system', 1, '系统用户状态字典'),
(2, 'role_status', '角色状态', 'system', 1, '系统角色状态字典'),
(3, 'permission_type', '权限类型', 'system', 1, '系统权限类型字典'),
(4, 'login_status', '登录状态', 'system', 1, '登录状态字典'),
(5, 'param_type', '参数类型', 'system', 1, '系统参数类型字典');

-- ----------------------------
-- 初始化字典项数据
-- ----------------------------
-- 用户状态字典项
INSERT INTO `syst_dictionary_item` (`id`, `item_code`, `item_value`, `item_label`, `sort_order`, `status`, `dictionary_id`) VALUES
(1, 'user_status_enabled', '1', '启用', 1, 1, 1),
(2, 'user_status_disabled', '0', '禁用', 2, 1, 1);

-- 角色状态字典项
INSERT INTO `syst_dictionary_item` (`id`, `item_code`, `item_value`, `item_label`, `sort_order`, `status`, `dictionary_id`) VALUES
(3, 'role_status_enabled', '1', '启用', 1, 1, 2),
(4, 'role_status_disabled', '0', '禁用', 2, 1, 2);

-- 权限类型字典项
INSERT INTO `syst_dictionary_item` (`id`, `item_code`, `item_value`, `item_label`, `sort_order`, `status`, `dictionary_id`) VALUES
(5, 'permission_type_menu', 'menu', '菜单', 1, 1, 3),
(6, 'permission_type_button', 'button', '按钮', 2, 1, 3),
(7, 'permission_type_api', 'api', '接口', 3, 1, 3);

-- 登录状态字典项
INSERT INTO `syst_dictionary_item` (`id`, `item_code`, `item_value`, `item_label`, `sort_order`, `status`, `dictionary_id`) VALUES
(8, 'login_status_success', '1', '成功', 1, 1, 4),
(9, 'login_status_fail', '0', '失败', 2, 1, 4);

-- 参数类型字典项
INSERT INTO `syst_dictionary_item` (`id`, `item_code`, `item_value`, `item_label`, `sort_order`, `status`, `dictionary_id`) VALUES
(10, 'param_type_string', 'STRING', '字符串', 1, 1, 5),
(11, 'param_type_number', 'NUMBER', '数字', 2, 1, 5),
(12, 'param_type_boolean', 'BOOLEAN', '布尔', 3, 1, 5),
(13, 'param_type_json', 'JSON', 'JSON对象', 4, 1, 5);

SET FOREIGN_KEY_CHECKS = 1;
