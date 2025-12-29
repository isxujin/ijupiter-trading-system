package net.ijupiter.trading.core.funding.repositories;

import net.ijupiter.trading.core.funding.entities.FundingAccountLedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 资金账户台账JPA仓库
 */
@Repository
public interface FundingAccountLedgerJpaRepository extends JpaRepository<FundingAccountLedgerEntity, Long> {
    
    /**
     * 根据账户编号和台账日期查询
     */
    FundingAccountLedgerEntity findByAccountCodeAndLedgerDate(String accountCode, LocalDate ledgerDate);
    
    /**
     * 根据账户编号查询台账记录（按日期降序）
     */
    List<FundingAccountLedgerEntity> findByAccountCodeOrderByLedgerDateDesc(String accountCode);
    
    /**
     * 根据客户编号查询台账记录（按日期降序）
     */
    List<FundingAccountLedgerEntity> findByCustomerCodeOrderByLedgerDateDesc(String customerCode);
    
    /**
     * 根据账户编号和日期范围查询台账记录
     */
    List<FundingAccountLedgerEntity> findByAccountCodeAndLedgerDateBetweenOrderByLedgerDateDesc(
        String accountCode, LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据台账日期查询所有台账记录
     */
    List<FundingAccountLedgerEntity> findByLedgerDateOrderByAccountCode(LocalDate ledgerDate);
    
    /**
     * 根据账户类型查询台账记录
     */
    List<FundingAccountLedgerEntity> findByAccountTypeOrderByLedgerDateDesc(Integer accountType);
    
    /**
     * 根据状态查询台账记录
     */
    List<FundingAccountLedgerEntity> findByStatusOrderByLedgerDateDesc(Integer status);
    
    /**
     * 查询指定账户的最新台账记录
     */
    FundingAccountLedgerEntity findTopByAccountCodeOrderByLedgerDateDesc(String accountCode);
    
    /**
     * 查询指定账户在指定日期之前最新的台账记录
     */
    FundingAccountLedgerEntity findTopByAccountCodeAndLedgerDateLessThanEqualOrderByLedgerDateDesc(
        String accountCode, LocalDate ledgerDate);
    
    /**
     * 统计指定日期范围内的总存款金额
     */
    @Query("SELECT COALESCE(SUM(l.depositAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumDepositAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总取款金额
     */
    @Query("SELECT COALESCE(SUM(l.withdrawAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumWithdrawAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总转入金额
     */
    @Query("SELECT COALESCE(SUM(l.transferInAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTransferInAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总转出金额
     */
    @Query("SELECT COALESCE(SUM(l.transferOutAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTransferOutAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总冻结金额
     */
    @Query("SELECT COALESCE(SUM(l.freezeAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumFreezeAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总解冻金额
     */
    @Query("SELECT COALESCE(SUM(l.unfreezeAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumUnfreezeAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总利息金额
     */
    @Query("SELECT COALESCE(SUM(l.interestAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumInterestAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总手续费金额
     */
    @Query("SELECT COALESCE(SUM(l.feeAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumFeeAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总退款金额
     */
    @Query("SELECT COALESCE(SUM(l.refundAmount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumRefundAmountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 统计指定日期范围内的总交易笔数
     */
    @Query("SELECT COALESCE(SUM(l.transactionCount), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    Long sumTransactionCountByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 查询指定日期范围内的总期末余额
     */
    @Query("SELECT COALESCE(SUM(l.closingBalance), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumClosingBalanceByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 查询指定日期范围内的总冻结余额
     */
    @Query("SELECT COALESCE(SUM(l.frozenBalance), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumFrozenBalanceByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 查询指定日期范围内的总可用余额
     */
    @Query("SELECT COALESCE(SUM(l.availableBalance), 0) FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAvailableBalanceByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * 查询余额在指定范围内的账户台账
     */
    @Query("SELECT l FROM FundingAccountLedgerEntity l " +
           "WHERE l.ledgerDate = :ledgerDate " +
           "AND l.closingBalance BETWEEN :minBalance AND :maxBalance")
    List<FundingAccountLedgerEntity> findByLedgerDateAndClosingBalanceBetween(
        @Param("ledgerDate") LocalDate ledgerDate,
        @Param("minBalance") BigDecimal minBalance,
        @Param("maxBalance") BigDecimal maxBalance);
}