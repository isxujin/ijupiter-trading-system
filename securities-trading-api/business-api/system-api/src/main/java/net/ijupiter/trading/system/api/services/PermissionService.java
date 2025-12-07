package net.ijupiter.trading.system.api.services;

import net.ijupiter.trading.system.api.dtos.PermissionDTO;
import net.ijupiter.trading.system.api.dtos.PermissionQueryDTO;

import java.util.List;

/**
 * 权限服务接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
public interface PermissionService {

    /**
     * 查询权限列表
     *
     * @param queryDTO 查询条件
     * @return 权限列表
     */
    List<PermissionDTO> queryPermissions(PermissionQueryDTO queryDTO);

    /**
     * 根据ID查询权限
     *
     * @param permissionId 权限ID
     * @return 权限
     */
    PermissionDTO getPermissionById(String permissionId);

    /**
     * 根据编码查询权限
     *
     * @param permissionCode 权限编码
     * @return 权限
     */
    PermissionDTO getPermissionByCode(String permissionCode);

    /**
     * 查询权限树
     *
     * @param queryDTO 查询条件
     * @return 权限树
     */
    List<PermissionDTO> queryPermissionTree(PermissionQueryDTO queryDTO);

    /**
     * 新增权限
     *
     * @param permissionDTO 权限
     * @return 新增的权限
     */
    PermissionDTO createPermission(PermissionDTO permissionDTO);

    /**
     * 更新权限
     *
     * @param permissionDTO 权限
     * @return 更新后的权限
     */
    PermissionDTO updatePermission(PermissionDTO permissionDTO);

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     * @return 是否删除成功
     */
    Boolean deletePermission(String permissionId);

    /**
     * 批量删除权限
     *
     * @param permissionIds 权限ID列表
     * @return 删除成功的数量
     */
    Integer batchDeletePermissions(List<String> permissionIds);

    /**
     * 更新权限状态
     *
     * @param permissionId 权限ID
     * @param status 状态（0-禁用，1-启用）
     * @return 是否更新成功
     */
    Boolean updatePermissionStatus(String permissionId, Integer status);
}