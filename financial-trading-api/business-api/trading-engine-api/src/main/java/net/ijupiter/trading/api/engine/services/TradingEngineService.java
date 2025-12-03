package net.ijupiter.trading.api.engine.services;

import net.ijupiter.trading.api.engine.commands.CreateOrderCommand;
import net.ijupiter.trading.api.engine.commands.CancelOrderCommand;
import net.ijupiter.trading.api.engine.dto.OrderDTO;
import net.ijupiter.trading.api.engine.dto.TradeDTO;
import net.ijupiter.trading.api.engine.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * 交易引擎服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface TradingEngineService {

    /**
     * 创建订单
     * 
     * @param command 创建订单命令
     * @return 订单ID
     */
    String createOrder(CreateOrderCommand command);

    /**
     * 取消订单
     * 
     * @param command 取消订单命令
     * @return 是否成功
     */
    boolean cancelOrder(CancelOrderCommand command);

    /**
     * 查询订单信息
     * 
     * @param orderId 订单ID
     * @return 订单信息
     */
    OrderDTO getOrder(String orderId);

    /**
     * 查询账户所有订单
     * 
     * @param accountId 账户ID
     * @return 订单列表
     */
    List<OrderDTO> getOrdersByAccount(String accountId);

    /**
     * 查询指定产品的订单
     * 
     * @param productId 产品ID
     * @param status 订单状态
     * @return 订单列表
     */
    List<OrderDTO> getOrdersByProduct(String productId, OrderStatus status);

    /**
     * 查询交易信息
     * 
     * @param tradeId 交易ID
     * @return 交易信息
     */
    TradeDTO getTrade(String tradeId);

    /**
     * 查询订单关联的交易
     * 
     * @param orderId 订单ID
     * @return 交易列表
     */
    List<TradeDTO> getTradesByOrder(String orderId);

    /**
     * 查询产品的最新价格
     * 
     * @param productId 产品ID
     * @return 最新价格
     */
    BigDecimal getLatestPrice(String productId);

    /**
     * 匹配订单
     * 
     * @param productId 产品ID
     * @return 匹配结果
     */
    boolean matchOrders(String productId);
}