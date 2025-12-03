package net.ijupiter.trading.api.query.services;

import net.ijupiter.trading.api.query.dto.*;
import net.ijupiter.trading.api.query.queries.*;

import java.util.List;

/**
 * 查询服务接口
 * 
 * @author ijupiter
 */
public interface QueryService {
    
    // ==================== 账户查询 ====================
    
    /**
     * 查询账户信息
     * 
     * @param query 查询条件
     * @return 账户信息列表
     */
    List<AccountQueryDTO> queryAccounts(AccountQuery query);
    
    /**
     * 查询单个账户信息
     * 
     * @param accountId 账户ID
     * @return 账户信息
     */
    AccountQueryDTO getAccount(String accountId);
    
    // ==================== 订单查询 ====================
    
    /**
     * 查询订单信息
     * 
     * @param query 查询条件
     * @return 订单信息列表
     */
    List<OrderQueryDTO> queryOrders(OrderQuery query);
    
    /**
     * 查询单个订单信息
     * 
     * @param orderId 订单ID
     * @return 订单信息
     */
    OrderQueryDTO getOrder(String orderId);
    
    // ==================== 资金账户查询 ====================
    
    /**
     * 查询资金账户信息
     * 
     * @param query 查询条件
     * @return 资金账户信息列表
     */
    List<FundAccountQueryDTO> queryFundAccounts(FundAccountQuery query);
    
    /**
     * 查询单个资金账户信息
     * 
     * @param fundAccountId 资金账户ID
     * @return 资金账户信息
     */
    FundAccountQueryDTO getFundAccount(String fundAccountId);
    
    // ==================== 产品查询 ====================
    
    /**
     * 查询产品信息
     * 
     * @param query 查询条件
     * @return 产品信息列表
     */
    List<ProductQueryDTO> queryProducts(ProductQuery query);
    
    /**
     * 查询单个产品信息
     * 
     * @param productId 产品ID
     * @return 产品信息
     */
    ProductQueryDTO getProduct(String productId);
    
    /**
     * 根据产品代码查询产品信息
     * 
     * @param productCode 产品代码
     * @return 产品信息
     */
    ProductQueryDTO getProductByCode(String productCode);
    
    // ==================== 成交记录查询 ====================
    
    /**
     * 查询成交记录
     * 
     * @param query 查询条件
     * @return 成交记录列表
     */
    List<TradeQueryDTO> queryTrades(TradeQuery query);
    
    /**
     * 查询单个成交记录
     * 
     * @param tradeId 成交ID
     * @return 成交记录
     */
    TradeQueryDTO getTrade(String tradeId);
    
    // ==================== 结算记录查询 ====================
    
    /**
     * 查询结算记录
     * 
     * @param query 查询条件
     * @return 结算记录列表
     */
    List<SettlementQueryDTO> querySettlements(SettlementQuery query);
    
    /**
     * 查询单个结算记录
     * 
     * @param settlementId 结算ID
     * @return 结算记录
     */
    SettlementQueryDTO getSettlement(String settlementId);
}