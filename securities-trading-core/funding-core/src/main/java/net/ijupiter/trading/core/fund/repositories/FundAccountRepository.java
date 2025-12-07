package net.ijupiter.trading.core.fund.repositories;

import net.ijupiter.trading.core.fund.entities.FundAccountEntity;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金账户数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface FundAccountRepository extends JpaRepository<FundAccountEntity, String> {

    /**
     * 根据账户ID查询资金账户
     * 
     * @param accountId 账户ID
     * @return 资金账户
     */
    FundAccountEntity findByAccountId(String accountId);

    /**
     * 根据账户ID和状态查询资金账户
     * 
     * @param accountId 账户ID
     * @param status 账户状态
     * @return 资金账户
     */
    FundAccountEntity findByAccountIdAndStatus(String accountId, FundAccountStatus status);

    /**
     * 检查账户是否存在
     * 
     * @param accountId 账户ID
     * @return 是否存在
     */
    boolean existsByAccountId(String accountId);

    /**
     * 检查资金账户是否存在
     * 
     * @param fundAccountId 资金账户ID
     * @return 是否存在
     */
    boolean existsByFundAccountId(String fundAccountId);

    /**
     * 查询所有状态的账户
     * 
     * @param status 账户状态
     * @return 资金账户列表
     */
    List<FundAccountEntity> findByStatus(FundAccountStatus status);

    /**
     * 查询余额大于指定金额的账户
     * 
     * @param amount 金额
     * @return 资金账户列表
     */
    @Query("SELECT fa FROM FundAccountEntity fa WHERE fa.balance > :amount")
    List<FundAccountEntity> findByBalanceGreaterThan(@Param("amount") BigDecimal amount);

    /**
     * 查询冻结金额大于指定金额的账户
     * 
     * @param amount 金额
     * @return 资金账户列表
     */
    @Query("SELECT fa FROM FundAccountEntity fa WHERE fa.frozenAmount > :amount")
    List<FundAccountEntity> findByFrozenAmountGreaterThan(@Param("amount") BigDecimal amount);

    /**
     * 统计指定状态的账户数量
     * 
     * @param status 账户状态
     * @return 账户数量
     */
    @Query("SELECT COUNT(fa) FROM FundAccountEntity fa WHERE fa.status = :status")
    long countByStatus(@Param("status") FundAccountStatus status);

    /**
     * 计算所有账户的总余额
     * 
     * @return 总余额
     */
    @Query("SELECT SUM(fa.balance) FROM FundAccountEntity fa")
    BigDecimal sumTotalBalance();

    /**
     * 计算所有账户的总冻结金额
     * 
     * @return 总冻结金额
     */
    @Query("SELECT SUM(fa.frozenAmount) FROM FundAccountEntity fa")
    BigDecimal sumTotalFrozenAmount();
}