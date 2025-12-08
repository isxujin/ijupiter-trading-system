package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 权限数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据权限编码查找权限
     *
     * @param permissionCode 权限编码
     * @return 权限对象
     */
    Optional<Permission> findByPermissionCode(String permissionCode);

    /**
     * 根据权限名称查找权限
     *
     * @param permissionName 权限名称
     * @return 权限对象
     */
    Optional<Permission> findByPermissionName(String permissionName);

    /**
     * 根据权限类型查找权限列表
     *
     * @param permissionType 权限类型
     * @return 权限列表
     */
    List<Permission> findByPermissionType(String permissionType);

    /**
     * 根据状态查找权限列表
     *
     * @param status 状态
     * @return 权限列表
     */
    List<Permission> findByStatus(Integer status);

    /**
     * 根据父权限ID查找子权限列表
     *
     * @param parentId 父权限ID
     * @return 权限列表
     */
    List<Permission> findByParentId(Long parentId);

    /**
     * 查找根权限列表（父权限ID为null或0）
     *
     * @return 权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.parentId IS NULL OR p.parentId = 0")
    List<Permission> findRootPermissions();

    /**
     * 根据角色ID查找权限列表
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.id = :roleId")
    List<Permission> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色编码查找权限列表
     *
     * @param roleCode 角色编码
     * @return 权限列表
     */
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.roleCode = :roleCode")
    List<Permission> findByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 根据用户ID查找权限列表（通过角色关联）
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Query("SELECT DISTINCT p FROM Permission p JOIN p.roles r JOIN r.users u WHERE u.id = :userId")
    List<Permission> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户编码查找权限列表（通过角色关联）
     *
     * @param userCode 用户编码
     * @return 权限列表
     */
    @Query("SELECT DISTINCT p FROM Permission p JOIN p.roles r JOIN r.users u WHERE u.userCode = :userCode")
    List<Permission> findByUserCode(@Param("userCode") String userCode);

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
     * @return 权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.permissionName LIKE %:keyword% OR p.description LIKE %:keyword% " +
           "OR p.path LIKE %:keyword%")
    List<Permission> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据排序号范围查找权限列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.sortOrder BETWEEN :minSortOrder AND :maxSortOrder ORDER BY p.sortOrder")
    List<Permission> findBySortOrderBetween(@Param("minSortOrder") Integer minSortOrder, @Param("maxSortOrder") Integer maxSortOrder);

    /**
     * 查找菜单权限列表
     *
     * @return 菜单权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.permissionType = 'menu' AND p.status = 1 ORDER BY p.sortOrder")
    List<Permission> findMenuPermissions();

    /**
     * 查找按钮权限列表
     *
     * @return 按钮权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.permissionType = 'button' AND p.status = 1 ORDER BY p.sortOrder")
    List<Permission> findButtonPermissions();

    /**
     * 查找API权限列表
     *
     * @return API权限列表
     */
    @Query("SELECT p FROM Permission p WHERE p.permissionType = 'api' AND p.status = 1 ORDER BY p.sortOrder")
    List<Permission> findApiPermissions();
}