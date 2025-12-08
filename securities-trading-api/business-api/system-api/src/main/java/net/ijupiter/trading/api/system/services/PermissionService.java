package net.ijupiter.trading.api.system.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.system.dtos.PermissionDTO;

import java.util.List;
import java.util.Optional;

/**
 * 权限服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface PermissionService extends BaseService<PermissionDTO, Long> {

    /**
     * 根据权限编码查找权限
     *
     * @param permissionCode 权限编码
     * @return 权限DTO
     */
    Optional<PermissionDTO> findByPermissionCode(String permissionCode);

    /**
     * 根据权限名称查找权限
     *
     * @param permissionName 权限名称
     * @return 权限DTO
     */
    Optional<PermissionDTO> findByPermissionName(String permissionName);

    /**
     * 根据权限类型查找权限列表
     *
     * @param permissionType 权限类型
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByPermissionType(String permissionType);

    /**
     * 根据状态查找权限列表
     *
     * @param status 状态
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByStatus(Integer status);

    /**
     * 根据父权限ID查找子权限列表
     *
     * @param parentId 父权限ID
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByParentId(Long parentId);

    /**
     * 查找根权限列表（父权限ID为null或0）
     *
     * @return 权限DTO列表
     */
    List<PermissionDTO> findRootPermissions();

    /**
     * 根据角色ID查找权限列表
     *
     * @param roleId 角色ID
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByRoleId(Long roleId);

    /**
     * 根据角色编码查找权限列表
     *
     * @param roleCode 角色编码
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByRoleCode(String roleCode);

    /**
     * 根据用户ID查找权限列表（通过角色关联）
     *
     * @param userId 用户ID
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByUserId(Long userId);

    /**
     * 根据用户编码查找权限列表（通过角色关联）
     *
     * @param userCode 用户编码
     * @return 权限DTO列表
     */
    List<PermissionDTO> findByUserCode(String userCode);

    /**
     * 检查权限编码是否存在
     *
     * @param permissionCode 权限编码
     * @return 是否存在
     */
    boolean existsByPermissionCode(String permissionCode);

    /**
     * 检查权限名称是否存在
     *
     * @param permissionName 权限名称
     * @return 是否存在
     */
    boolean existsByPermissionName(String permissionName);

    /**
     * 模糊搜索权限（支持权限名称、权限描述、权限路径）
     *
     * @param keyword 关键字
     * @return 权限DTO列表
     */
    List<PermissionDTO> searchByKeyword(String keyword);

    /**
     * 根据排序号范围查找权限列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 权限DTO列表
     */
    List<PermissionDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder);

    /**
     * 查找菜单权限列表
     *
     * @return 菜单权限DTO列表
     */
    List<PermissionDTO> findMenuPermissions();

    /**
     * 查找按钮权限列表
     *
     * @return 按钮权限DTO列表
     */
    List<PermissionDTO> findButtonPermissions();

    /**
     * 查找API权限列表
     *
     * @return API权限DTO列表
     */
    List<PermissionDTO> findApiPermissions();

    /**
     * 创建权限
     *
     * @param permissionDTO 权限DTO
     * @return 创建后的权限DTO
     */
    PermissionDTO createPermission(PermissionDTO permissionDTO);

    /**
     * 更新权限信息
     *
     * @param permissionDTO 权限DTO
     * @return 更新后的权限DTO
     */
    PermissionDTO updatePermission(PermissionDTO permissionDTO);

    /**
     * 启用权限
     *
     * @param permissionId 权限ID
     * @return 是否启用成功
     */
    boolean enablePermission(Long permissionId);

    /**
     * 禁用权限
     *
     * @param permissionId 权限ID
     * @return 是否禁用成功
     */
    boolean disablePermission(Long permissionId);

    /**
     * 构建权限树
     *
     * @param permissions 权限DTO列表
     * @return 权限树结构
     */
    List<PermissionDTO> buildPermissionTree(List<PermissionDTO> permissions);

    /**
     * 获取完整权限树
     *
     * @return 权限树结构
     */
    List<PermissionDTO> getPermissionTree();

    /**
     * 根据用户编码获取菜单权限树
     *
     * @param userCode 用户编码
     * @return 菜单权限树结构
     */
    List<PermissionDTO> getMenuPermissionTree(String userCode);

    /**
     * 检查用户是否有指定权限
     *
     * @param userCode 用户编码
     * @param permissionCode 权限编码
     * @return 是否有权限
     */
    boolean hasPermission(String userCode, String permissionCode);

    /**
     * 检查用户是否有任意一个指定权限
     *
     * @param userCode 用户编码
     * @param permissionCodes 权限编码列表
     * @return 是否有任意一个权限
     */
    boolean hasAnyPermission(String userCode, List<String> permissionCodes);

    /**
     * 检查用户是否有所有指定权限
     *
     * @param userCode 用户编码
     * @param permissionCodes 权限编码列表
     * @return 是否有所有权限
     */
    boolean hasAllPermissions(String userCode, List<String> permissionCodes);

    /**
     * 移动权限到新的父节点下
     *
     * @param permissionId 权限ID
     * @param newParentId 新父权限ID
     * @return 是否移动成功
     */
    boolean movePermission(Long permissionId, Long newParentId);

    /**
     * 获取权限的子权限数量
     *
     * @param permissionId 权限ID
     * @return 子权限数量
     */
    Integer getChildrenCount(Long permissionId);
}