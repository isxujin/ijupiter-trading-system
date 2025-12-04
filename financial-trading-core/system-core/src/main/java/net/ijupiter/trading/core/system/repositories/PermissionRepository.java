package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 权限仓储接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

    /**
     * 根据权限编码查询权限
     * 
     * @param permissionCode 权限编码
     * @return 权限
     */
    Optional<Permission> findByPermissionCode(String permissionCode);

    /**
     * 检查权限编码是否存在
     * 
     * @param permissionCode 权限编码
     * @return 是否存在
     */
    boolean existsByPermissionCode(String permissionCode);

    /**
     * 根据父权限ID查询权限列表（按排序顺序）
     * 
     * @param parentId 父权限ID
     * @return 权限列表
     */
    List<Permission> findByParentIdOrderBySortOrder(String parentId);

    /**
     * 根据状态查询权限列表（按排序顺序）
     * 
     * @param status 状态
     * @return 权限列表
     */
    List<Permission> findByStatusOrderBySortOrder(Integer status);

    /**
     * 根据操作员ID查询权限列表
     * 
     * @param operatorId 操作员ID
     * @return 权限列表
     */
    @Query("SELECT DISTINCT p FROM Permission p " +
           "JOIN RolePermission rp ON p.id = rp.permissionId " +
           "JOIN OperatorRole orr ON rp.roleId = orr.roleId " +
           "WHERE orr.operatorId = :operatorId AND p.status = 1 " +
           "ORDER BY p.sortOrder")
    List<Permission> findByOperatorId(@Param("operatorId") String operatorId);
}