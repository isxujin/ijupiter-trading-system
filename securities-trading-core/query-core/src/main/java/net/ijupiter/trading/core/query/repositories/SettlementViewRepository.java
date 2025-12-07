package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.model.SettlementView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 结算记录视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface SettlementViewRepository extends JpaRepository<SettlementView, String> {
    
    /**
     * 根据订单ID查询结算记录列表
     */
    List<SettlementView> findByOrderId(String orderId);
    
    /**
     * 根据成交ID查询结算记录
     */
    List<SettlementView> findByTradeId(String tradeId);
    
    /**
     * 根据客户ID查询结算记录列表
     */
    List<SettlementView> findByCustomerId(String customerId);
    
    /**
     * 根据账户ID查询结算记录列表
     */
    List<SettlementView> findByAccountId(String accountId);
    
    /**
     * 根据产品代码查询结算记录列表
     */
    List<SettlementView> findByProductCode(String productCode);
    
    /**
     * 根据结算日期查询结算记录列表
     */
    List<SettlementView> findBySettlementDate(String settlementDate);
    
    /**
     * 根据结算状态查询结算记录列表
     */
    List<SettlementView> findByStatus(String status);
    
    /**
     * 根据时间范围查询结算记录列表
     */
    @Query("SELECT s FROM SettlementView s WHERE s.createTime BETWEEN :startTime AND :endTime")
    List<SettlementView> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据客户ID和结算日期查询结算记录列表
     */
    List<SettlementView> findByCustomerIdAndSettlementDate(String customerId, String settlementDate);
    
    /**
     * 统计客户的结算记录数量
     */
    @Query("SELECT COUNT(s) FROM SettlementView s WHERE s.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
    
    /**
     * 计算客户总结算金额
     */
    @Query("SELECT SUM(s.settlementAmount) FROM SettlementView s WHERE s.customerId = :customerId")
    Double sumSettlementAmountByCustomerId(@Param("customerId") String customerId);
}