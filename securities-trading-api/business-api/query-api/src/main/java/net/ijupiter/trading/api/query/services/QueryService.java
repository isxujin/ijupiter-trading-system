package net.ijupiter.trading.api.query.services;

import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.api.query.queries.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 查询服务接口
 */
public interface QueryService {
    
    /**
     * 查询客户综合信息（客户基础信息，资金账户信息、证券账户信息）
     */
    CustomerFinancialSummaryDTO queryCustomerFinancialSummary(CustomerFinancialSummaryQuery query);
    
    /**
     * 查询客户交易流水
     */
    Page<CustomerTransactionSummaryDTO> queryCustomerTransactionSummary(CustomerTransactionSummaryQuery query);
    
    /**
     * 查询客户资金流水
     */
    Page<CustomerFundingTransactionSummaryDTO> queryCustomerFundingTransactionSummary(CustomerFundingTransactionSummaryQuery query);
    
    /**
     * 查询客户资金账户余额、发生额
     */
    List<CustomerFundingBalanceDTO> queryCustomerFundingBalance(CustomerFundingBalanceQuery query);
    
    /**
     * 查询客户证券持仓信息
     */
    Page<CustomerSecuritiesPositionDTO> queryCustomerSecuritiesPosition(CustomerSecuritiesPositionQuery query);
    
    /**
     * 查询客户每日证券收益信息
     */
    Page<CustomerDailySecuritiesProfitDTO> queryCustomerDailySecuritiesProfit(CustomerDailySecuritiesProfitQuery query);
}