package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.model.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 订单视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface OrderViewRepository extends JpaRepository<OrderView, String> {
    
    /**
     * 根据客户ID查询订单列表
     */
    List<OrderView> findByCustomerId(String customerId);
    
    /**
     * 根据账户ID查询订单列表
     */
    List<OrderView> findByAccountId(String accountId);
    
    /**
     * 根据订单编号查询订单
     */
    Optional<OrderView> findByOrderNo(String orderNo);
    
    /**
     * 根据产品代码查询订单列表
     */
    List<OrderView> findByProductCode(String productCode);
    
    /**
     * 根据订单状态查询订单列表
     */
    List<OrderView> findByStatus(String status);
    
    /**
     * 根据订单类型查询订单列表
     */
    List<OrderView> findByOrderType(String orderType);
    
    /**
     * 根据订单方向查询订单列表
     */
    List<OrderView> findByOrderSide(String orderSide);
    
    /**
     * 根据时间范围查询订单列表
     */
    @Query("SELECT o FROM OrderView o WHERE o.orderTime BETWEEN :startTime AND :endTime")
    List<OrderView> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据客户ID和产品代码查询订单列表
     */
    List<OrderView> findByCustomerIdAndProductCode(String customerId, String productCode);
    
    /**
     * 根据客户ID和订单状态查询订单列表
     */
    List<OrderView> findByCustomerIdAndStatus(String customerId, String status);
    
    /**
     * 统计客户的订单数量
     */
    @Query("SELECT COUNT(o) FROM OrderView o WHERE o.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
}