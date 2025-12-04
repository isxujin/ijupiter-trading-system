package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作员Repository
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface OperatorRepository extends JpaRepository<Operator, String>, JpaSpecificationExecutor<Operator> {

    /**
     * 根据登录名查询操作员
     *
     * @param loginName 登录名
     * @return 操作员
     */
    Operator findByLoginName(String loginName);

    /**
     * 根据操作员名称模糊查询
     *
     * @param operatorName 操作员名称
     * @return 操作员列表
     */
    @Query("SELECT o FROM Operator o WHERE o.operatorName LIKE %:operatorName%")
    List<Operator> findByOperatorNameContaining(@Param("operatorName") String operatorName);

    /**
     * 根据状态查询操作员
     *
     * @param status 状态
     * @return 操作员列表
     */
    List<Operator> findByStatus(Integer status);

    /**
     * 根据角色ID查询操作员
     *
     * @param roleId 角色ID
     * @return 操作员列表
     */
    @Query("SELECT o FROM Operator o JOIN OperatorRole or ON o.operatorId = or.operatorId WHERE or.roleId = :roleId")
    List<Operator> findByRoleId(@Param("roleId") String roleId);

    /**
     * 根据登录名和密码查询操作员
     *
     * @param loginName 登录名
     * @param password  密码
     * @return 操作员
     */
    Operator findByLoginNameAndPassword(String loginName, String password);
}