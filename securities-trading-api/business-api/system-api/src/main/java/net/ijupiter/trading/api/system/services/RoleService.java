package net.ijupiter.trading.api.system.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.system.dtos.RoleDTO;

import java.util.List;
import java.util.Optional;

/**
 * 角色服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface RoleService extends BaseService<RoleDTO, Long> {

    /**
     * 根据角色编码查找角色
     *
     * @param roleCode 角色编码
     * @return 角色DTO
     */
    Optional<RoleDTO> findByRoleCode(String roleCode);

    /**
     * 根据角色名称查找角色
     *
     * @param roleName 角色名称
     * @return 角色DTO
     */
    Optional<RoleDTO> findByRoleName(String roleName);

    /**
     * 根据状态查找角色列表
     *
     * @param status 状态
     * @return 角色DTO列表
     */
    List<RoleDTO> findByStatus(Integer status);

    /**
     * 根据用户ID查找角色列表
     *
     * @param userId 用户ID
     * @return 角色DTO列表
     */
    List<RoleDTO> findByUserId(Long userId);

    /**
     * 根据用户编码查找角色列表
     *
     * @param userCode 用户编码
     * @return 角色DTO列表
     */
    List<RoleDTO> findByUserCode(String userCode);

    /**
     * 根据权限ID查找角色列表
     *
     * @param permissionId 权限ID
     * @return 角色DTO列表
     */
    List<RoleDTO> findByPermissionId(Long permissionId);

    /**
     * 根据权限编码查找角色列表
     *
     * @param permissionCode 权限编码
     * @return 角色DTO列表
     */
    List<RoleDTO> findByPermissionCode(String permissionCode);

    /**
     * 检查角色编码是否存在
     *
     * @param roleCode 角色编码
     * @return 是否存在
     */
    boolean existsByRoleCode(String roleCode);

    /**
     * 检查角色名称是否存在
     *
     * @param roleName 角色名称
     * @return 是否存在
     */
    boolean existsByRoleName(String roleName);

    /**
     * 模糊搜索角色（支持角色名称、角色描述）
     *
     * @param keyword 关键字
     * @return 角色DTO列表
     */
    List<RoleDTO> searchByKeyword(String keyword);

    /**
     * 根据排序号范围查找角色列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 角色DTO列表
     */
    List<RoleDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder);

    /**
     * 创建角色并分配权限
     *
     * @param roleDTO 角色DTO
     * @param permissionIds 权限ID列表
     * @return 创建后的角色DTO
     */
    RoleDTO createRoleWithPermissions(RoleDTO roleDTO, List<Long> permissionIds);

    /**
     * 更新角色基本信息
     *
     * @param roleDTO 角色DTO
     * @return 更新后的角色DTO
     */
    RoleDTO updateRoleInfo(RoleDTO roleDTO);

    /**
     * 分配权限给角色
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否分配成功
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 移除角色的权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否移除成功
     */
    boolean removePermissions(Long roleId, List<Long> permissionIds);

    /**
     * 清空角色的所有权限
     *
     * @param roleId 角色ID
     * @return 是否清空成功
     */
    boolean clearPermissions(Long roleId);

    /**
     * 启用角色
     *
     * @param roleId 角色ID
     * @return 是否启用成功
     */
    boolean enableRole(Long roleId);

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 是否禁用成功
     */
    boolean disableRole(Long roleId);

    /**
     * 根据角色编码查找角色及其权限信息
     *
     * @param roleCode 角色编码
     * @return 角色DTO（包含权限信息）
     */
    Optional<RoleDTO> findRoleWithPermissions(String roleCode);

    /**
     * 根据角色编码查找角色及其用户信息
     *
     * @param roleCode 角色编码
     * @return 角色DTO（包含用户信息）
     */
    Optional<RoleDTO> findRoleWithUsers(String roleCode);

    /**
     * 检查角色是否为系统角色
     *
     * @param roleId 角色ID
     * @return 是否为系统角色
     */
    boolean isSystemRole(Long roleId);

    /**
     * 获取角色的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    Integer getUserCount(Long roleId);
}