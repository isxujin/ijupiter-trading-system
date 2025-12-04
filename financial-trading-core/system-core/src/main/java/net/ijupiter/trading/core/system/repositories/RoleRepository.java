package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 角色Repository
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色
     */
    Optional<Role> findByRoleCode(String roleCode);

    /**
     * 根据角色名称模糊查询
     *
     * @param roleName 角色名称
     * @return 角色列表
     */
    @Query("SELECT r FROM Role r WHERE r.roleName LIKE %:roleName%")
    List<Role> findByRoleNameContaining(@Param("roleName") String roleName);

    /**
     * 根据状态查询角色
     *
     * @param status 状态
     * @return 角色列表
     */
    List<Role> findByStatus(Integer status);

    /**
     * 根据是否系统内置角色查询
     *
     * @param isSystem 是否系统内置角色
     * @return 角色列表
     */
    List<Role> findByIsSystem(Boolean isSystem);
}