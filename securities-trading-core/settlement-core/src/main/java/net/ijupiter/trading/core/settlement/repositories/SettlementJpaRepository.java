package net.ijupiter.trading.core.settlement.repositories;

import net.ijupiter.trading.core.settlement.entities.SettlementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 清算JPA数据访问接口
 */
@Repository
public interface SettlementJpaRepository extends JpaRepository<SettlementEntity, Long> {
    
    /**
     * 根据清算编号查询清算记录
     */
    SettlementEntity findBySettlementCode(String settlementCode);
    
    /**
     * 根据清算类型查询清算记录
     */
    List<SettlementEntity> findBySettlementType(Integer settlementType);
    
    /**
     * 根据状态查询清算记录
     */
    List<SettlementEntity> findByStatus(Integer status);
    
    /**
     * 根据清算日期范围查询清算记录
     */
    @Query("SELECT s FROM SettlementEntity s WHERE s.settlementDate BETWEEN :startDate AND :endDate")
    List<SettlementEntity> findBySettlementDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                      @Param("endDate") LocalDateTime endDate);
    
    /**
     * 根据买方客户ID查询清算记录
     */
    List<SettlementEntity> findByBuyerCustomerId(Long buyerCustomerId);
    
    /**
     * 根据卖方客户ID查询清算记录
     */
    List<SettlementEntity> findBySellerCustomerId(Long sellerCustomerId);
    
    /**
     * 统计清算记录数量
     */
    @Query("SELECT COUNT(s) FROM SettlementEntity s")
    long countAll();
    
    /**
     * 根据清算类型统计记录数量
     */
    @Query("SELECT COUNT(s) FROM SettlementEntity s WHERE s.settlementType = :settlementType")
    long countBySettlementType(@Param("settlementType") Integer settlementType);
    
    /**
     * 根据状态统计记录数量
     */
    @Query("SELECT COUNT(s) FROM SettlementEntity s WHERE s.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * 统计总清算金额
     */
    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM SettlementEntity s")
    java.math.BigDecimal sumAllAmount();
    
    /**
     * 统计总手续费
     */
    @Query("SELECT COALESCE(SUM(s.fee), 0) FROM SettlementEntity s")
    java.math.BigDecimal sumAllFee();
    
    /**
     * 统计总印花税
     */
    @Query("SELECT COALESCE(SUM(s.tax), 0) FROM SettlementEntity s")
    java.math.BigDecimal sumAllTax();
}