package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.OperatorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作员角色关联仓储接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface OperatorRoleRepository extends JpaRepository<OperatorRole, String> {

    /**
     * 根据操作员ID查询角色ID列表
     * 
     * @param operatorId 操作员ID
     * @return 角色ID列表
     */
    @Query("SELECT orr.roleId FROM OperatorRole orr WHERE orr.operatorId = :operatorId")
    List<String> findRoleIdsByOperatorId(@Param("operatorId") String operatorId);

    /**
     * 根据角色ID查询操作员ID列表
     * 
     * @param roleId 角色ID
     * @return 操作员ID列表
     */
    @Query("SELECT orr.operatorId FROM OperatorRole orr WHERE orr.roleId = :roleId")
    List<String> findOperatorIdsByRoleId(@Param("roleId") String roleId);

    /**
     * 根据操作员ID删除操作员角色关联
     * 
     * @param operatorId 操作员ID
     */
    void deleteByOperatorId(String operatorId);

    /**
     * 根据角色ID删除操作员角色关联
     * 
     * @param roleId 角色ID
     */
    void deleteByRoleId(String roleId);

    /**
     * 检查操作员是否拥有指定角色
     * 
     * @param operatorId 操作员ID
     * @param roleId 角色ID
     * @return 是否拥有
     */
    boolean existsByOperatorIdAndRoleId(String operatorId, String roleId);
}