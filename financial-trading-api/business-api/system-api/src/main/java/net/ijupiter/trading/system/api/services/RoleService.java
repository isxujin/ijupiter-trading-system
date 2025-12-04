package net.ijupiter.trading.system.api.services;

import net.ijupiter.trading.common.base.BaseService;
import net.ijupiter.trading.system.api.dto.RoleDTO;
import net.ijupiter.trading.system.api.dto.RoleQueryDTO;

import java.util.List;

/**
 * 角色服务接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
public interface RoleService extends BaseService<RoleDTO, String> {

    /**
     * 查询角色列表
     *
     * @param queryDTO 查询条件
     * @return 角色列表
     */
    List<RoleDTO> queryRoles(RoleQueryDTO queryDTO);

    /**
     * 根据ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色
     */
    RoleDTO getRoleById(String roleId);

    /**
     * 根据编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色
     */
    RoleDTO getRoleByCode(String roleCode);

    /**
     * 新增角色
     *
     * @param roleDTO 角色
     * @return 新增的角色
     */
    RoleDTO createRole(RoleDTO roleDTO);

    /**
     * 更新角色
     *
     * @param roleDTO 角色
     * @return 更新后的角色
     */
    RoleDTO updateRole(RoleDTO roleDTO);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    Boolean deleteRole(String roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID列表
     * @return 删除成功的数量
     */
    Integer batchDeleteRoles(List<String> roleIds);

    /**
     * 更新角色状态
     *
     * @param roleId 角色ID
     * @param status 状态（0-禁用，1-启用）
     * @return 是否更新成功
     */
    Boolean updateRoleStatus(String roleId, Integer status);

    /**
     * 分配权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否分配成功
     */
    Boolean assignPermissions(String roleId, List<String> permissionIds);

    /**
     * 回收权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否回收成功
     */
    Boolean revokePermissions(String roleId, List<String> permissionIds);
}