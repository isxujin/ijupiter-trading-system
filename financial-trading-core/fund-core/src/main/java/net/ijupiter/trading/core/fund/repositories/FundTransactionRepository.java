package net.ijupiter.trading.core.fund.repositories;

import net.ijupiter.trading.core.fund.entities.FundTransactionEntity;
import net.ijupiter.trading.api.fund.enums.FundTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 资金交易数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransactionEntity, String> {

    /**
     * 根据资金账户ID查询交易记录
     * 
     * @param fundAccountId 资金账户ID
     * @return 交易记录列表
     */
    List<FundTransactionEntity> findByFundAccountId(String fundAccountId);

    /**
     * 根据账户ID查询交易记录
     * 
     * @param accountId 账户ID
     * @return 交易记录列表
     */
    List<FundTransactionEntity> findByAccountId(String accountId);

    /**
     * 根据资金账户ID和交易类型查询交易记录
     * 
     * @param fundAccountId 资金账户ID
     * @param transactionType 交易类型
     * @return 交易记录列表
     */
    List<FundTransactionEntity> findByFundAccountIdAndTransactionType(String fundAccountId, 
                                                              FundTransactionType transactionType);

    /**
     * 根据资金账户ID和时间范围查询交易记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    @Query("SELECT ft FROM FundTransactionEntity ft WHERE ft.fundAccountId = :fundAccountId " +
           "AND ft.transactionTime >= :startTime AND ft.transactionTime <= :endTime " +
           "ORDER BY ft.transactionTime DESC")
    List<FundTransactionEntity> findByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
            @Param("fundAccountId") String fundAccountId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据交易ID查询交易记录
     * 
     * @param transactionId 交易ID
     * @return 交易记录
     */
    FundTransactionEntity findByTransactionId(String transactionId);

    /**
     * 检查交易是否存在
     * 
     * @param transactionId 交易ID
     * @return 是否存在
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * 查询指定时间范围内的所有交易记录
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    @Query("SELECT ft FROM FundTransactionEntity ft WHERE ft.transactionTime >= :startTime " +
           "AND ft.transactionTime <= :endTime ORDER BY ft.transactionTime DESC")
    List<FundTransactionEntity> findByTransactionTimeBetweenOrderByTransactionTimeDesc(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 查询指定时间范围内的入金记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 入金记录列表
     */
    @Query("SELECT ft FROM FundTransactionEntity ft WHERE ft.fundAccountId = :fundAccountId " +
           "AND ft.transactionType = 'DEPOSIT' " +
           "AND ft.transactionTime >= :startTime AND ft.transactionTime <= :endTime " +
           "ORDER BY ft.transactionTime DESC")
    List<FundTransactionEntity> findDepositsByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
            @Param("fundAccountId") String fundAccountId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 查询指定时间范围内的出金记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 出金记录列表
     */
    @Query("SELECT ft FROM FundTransactionEntity ft WHERE ft.fundAccountId = :fundAccountId " +
           "AND ft.transactionType = 'WITHDRAW' " +
           "AND ft.transactionTime >= :startTime AND ft.transactionTime <= :endTime " +
           "ORDER BY ft.transactionTime DESC")
    List<FundTransactionEntity> findWithdrawalsByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
            @Param("fundAccountId") String fundAccountId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}