package net.ijupiter.trading.core.account.repositories;

import net.ijupiter.trading.core.account.entities.AccountEntity;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    /**
     * 根据用户ID查询账户
     * 
     * @param userId 用户ID
     * @return 账户列表
     */
    List<AccountEntity> findByUserId(String userId);

    /**
     * 根据用户ID和账户类型查询账户
     * 
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 账户列表
     */
    List<AccountEntity> findByUserIdAndAccountType(String userId, AccountType accountType);

    /**
     * 根据用户ID和账户状态查询账户
     * 
     * @param userId 用户ID
     * @param accountStatus 账户状态
     * @return 账户列表
     */
    List<AccountEntity> findByUserIdAndAccountStatus(String userId, AccountStatus accountStatus);

    /**
     * 根据用户ID、账户类型和账户状态查询账户
     * 
     * @param userId 用户ID
     * @param accountType 账户类型
     * @param accountStatus 账户状态
     * @return 账户列表
     */
    AccountEntity findByUserIdAndAccountTypeAndAccountStatus(String userId, AccountType accountType, AccountStatus accountStatus);

    /**
     * 检查用户是否已有指定类型的账户
     * 
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 是否存在
     */
    boolean existsByUserIdAndAccountType(String userId, AccountType accountType);

    /**
     * 查询指定时间范围内创建的账户
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 账户列表
     */
    @Query("SELECT a FROM AccountEntity a WHERE a.createTime >= :startTime AND a.createTime <= :endTime")
    List<AccountEntity> findByCreateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询指定时间范围内关闭的账户
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 账户列表
     */
    @Query("SELECT a FROM AccountEntity a WHERE a.closeTime >= :startTime AND a.closeTime <= :endTime")
    List<AccountEntity> findByCloseTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 统计用户账户数量
     * 
     * @param userId 用户ID
     * @return 账户数量
     */
    @Query("SELECT COUNT(a) FROM AccountEntity a WHERE a.userId = :userId")
    long countByUserId(@Param("userId") String userId);

    /**
     * 统计指定状态的账户数量
     * 
     * @param accountStatus 账户状态
     * @return 账户数量
     */
    @Query("SELECT COUNT(a) FROM AccountEntity a WHERE a.accountStatus = :accountStatus")
    long countByAccountStatus(@Param("accountStatus") AccountStatus accountStatus);
}
