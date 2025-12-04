package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限关联仓储接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    /**
     * 根据角色ID查询权限ID列表
     * 
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    @Query("SELECT rp.permissionId FROM RolePermission rp WHERE rp.roleId = :roleId")
    List<String> findPermissionIdsByRoleId(@Param("roleId") String roleId);

    /**
     * 根据权限ID查询角色ID列表
     * 
     * @param permissionId 权限ID
     * @return 角色ID列表
     */
    @Query("SELECT rp.roleId FROM RolePermission rp WHERE rp.permissionId = :permissionId")
    List<String> findRoleIdsByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 根据角色ID删除角色权限关联
     * 
     * @param roleId 角色ID
     */
    void deleteByRoleId(String roleId);

    /**
     * 根据权限ID删除角色权限关联
     * 
     * @param permissionId 权限ID
     */
    void deleteByPermissionId(String permissionId);
}