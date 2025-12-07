package net.ijupiter.trading.core.engine.repositories;

import net.ijupiter.trading.core.engine.entities.OrderEntity;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    /**
     * 根据账户ID查询订单
     * 
     * @param accountId 账户ID
     * @return 订单列表
     */
    List<OrderEntity> findByAccountId(String accountId);

    /**
     * 根据产品ID和状态查询订单
     * 
     * @param productId 产品ID
     * @param status 订单状态
     * @return 订单列表
     */
    List<OrderEntity> findByProductIdAndStatus(String productId, OrderStatus status);

    /**
     * 根据产品ID、方向和状态查询订单，按创建时间升序排列
     * 
     * @param productId 产品ID
     * @param side 订单方向
     * @param status 订单状态
     * @return 订单列表
     */
    List<OrderEntity> findByProductIdAndSideAndStatusOrderByCreateTimeAsc(
            String productId, OrderSide side, OrderStatus status);

    /**
     * 根据产品ID查询订单
     * 
     * @param productId 产品ID
     * @return 订单列表
     */
    List<OrderEntity> findByProductId(String productId);

    /**
     * 查询指定价格范围内的订单
     * 
     * @param productId 产品ID
     * @param side 订单方向
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 订单列表
     */
    @Query("SELECT o FROM OrderEntity o WHERE o.productId = :productId AND o.side = :side " +
           "AND o.price >= :minPrice AND o.price <= :maxPrice")
    List<OrderEntity> findByProductIdAndSideAndPriceBetween(
            @Param("productId") String productId,
            @Param("side") OrderSide side,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);

    // 1. 价格 >= 目标价（买单/卖单）
    List<OrderEntity> findByProductIdAndSideAndPriceGreaterThanEqual(
            String productId,
            OrderSide side,
            BigDecimal price
    );

    // 2. 价格 <= 目标价（买单/卖单）
    List<OrderEntity> findByProductIdAndSideAndPriceLessThanEqual(
            String productId,
            OrderSide side,
            BigDecimal price
    );
}