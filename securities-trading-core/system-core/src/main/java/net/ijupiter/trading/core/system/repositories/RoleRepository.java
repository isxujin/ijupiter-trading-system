package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 角色数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 根据角色编码查找角色
     *
     * @param roleCode 角色编码
     * @return 角色对象
     */
    Optional<Role> findByRoleCode(String roleCode);

    /**
     * 根据角色名称查找角色
     *
     * @param roleName 角色名称
     * @return 角色对象
     */
    Optional<Role> findByRoleName(String roleName);

    /**
     * 根据状态查找角色列表
     *
     * @param status 状态
     * @return 角色列表
     */
    List<Role> findByStatus(Integer status);

    /**
     * 根据用户ID查找角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户编码查找角色列表
     *
     * @param userCode 用户编码
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.userCode = :userCode")
    List<Role> findByUserCode(@Param("userCode") String userCode);

    /**
     * 根据权限ID查找角色列表
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r JOIN r.permissions p WHERE p.id = :permissionId")
    List<Role> findByPermissionId(@Param("permissionId") Long permissionId);

    /**
     * 根据权限编码查找角色列表
     *
     * @param permissionCode 权限编码
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r JOIN r.permissions p WHERE p.permissionCode = :permissionCode")
    List<Role> findByPermissionCode(@Param("permissionCode") String permissionCode);

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
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r WHERE r.roleName LIKE %:keyword% OR r.description LIKE %:keyword%")
    List<Role> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据排序号范围查找角色列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r WHERE r.sortOrder BETWEEN :minSortOrder AND :maxSortOrder ORDER BY r.sortOrder")
    List<Role> findBySortOrderBetween(@Param("minSortOrder") Integer minSortOrder, @Param("maxSortOrder") Integer maxSortOrder);
}