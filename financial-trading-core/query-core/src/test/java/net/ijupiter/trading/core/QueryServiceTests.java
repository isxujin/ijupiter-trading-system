package net.ijupiter.trading.core;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.api.query.queries.*;
import net.ijupiter.trading.api.query.services.QueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * 查询服务测试
 * 
 * @author ijupiter
 */
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class QueryServiceTests {
    
    @Autowired
    private QueryService queryService;
    
    @Test
    public void testQueryAccounts() {
        log.info("测试账户查询功能");
        
        // 查询所有账户
        AccountQuery queryAll = AccountQuery.all();
        List<AccountQueryDTO> accounts = queryService.queryAccounts(queryAll);
        log.info("查询到{}个账户", accounts.size());
        
        // 按客户ID查询账户
        if (!accounts.isEmpty()) {
            String customerId = accounts.get(0).getCustomerId();
            AccountQuery queryByCustomer = AccountQuery.byCustomerId(customerId);
            List<AccountQueryDTO> customerAccounts = queryService.queryAccounts(queryByCustomer);
            log.info("客户{}有{}个账户", customerId, customerAccounts.size());
        }
        
        // 查询单个账户
        if (!accounts.isEmpty()) {
            String accountId = accounts.get(0).getAccountId();
            AccountQueryDTO account = queryService.getAccount(accountId);
            log.info("账户信息: {}", account);
        }
    }
    
    @Test
    public void testQueryOrders() {
        log.info("测试订单查询功能");
        
        // 查询所有订单
        OrderQuery queryAll = OrderQuery.all();
        List<OrderQueryDTO> orders = queryService.queryOrders(queryAll);
        log.info("查询到{}个订单", orders.size());
        
        // 按客户ID查询订单
        if (!orders.isEmpty()) {
            String customerId = orders.get(0).getCustomerId();
            OrderQuery queryByCustomer = OrderQuery.byCustomerId(customerId);
            List<OrderQueryDTO> customerOrders = queryService.queryOrders(queryByCustomer);
            log.info("客户{}有{}个订单", customerId, customerOrders.size());
        }
        
        // 查询单个订单
        if (!orders.isEmpty()) {
            String orderId = orders.get(0).getOrderId();
            OrderQueryDTO order = queryService.getOrder(orderId);
            log.info("订单信息: {}", order);
        }
    }
    
    @Test
    public void testQueryFundAccounts() {
        log.info("测试资金账户查询功能");
        
        // 查询所有资金账户
        FundAccountQuery queryAll = FundAccountQuery.all();
        List<FundAccountQueryDTO> fundAccounts = queryService.queryFundAccounts(queryAll);
        log.info("查询到{}个资金账户", fundAccounts.size());
        
        // 按客户ID查询资金账户
        if (!fundAccounts.isEmpty()) {
            String customerId = fundAccounts.get(0).getCustomerId();
            FundAccountQuery queryByCustomer = FundAccountQuery.byCustomerId(customerId);
            List<FundAccountQueryDTO> customerFundAccounts = queryService.queryFundAccounts(queryByCustomer);
            log.info("客户{}有{}个资金账户", customerId, customerFundAccounts.size());
        }
        
        // 查询单个资金账户
        if (!fundAccounts.isEmpty()) {
            String fundAccountId = fundAccounts.get(0).getFundAccountId();
            FundAccountQueryDTO fundAccount = queryService.getFundAccount(fundAccountId);
            log.info("资金账户信息: {}", fundAccount);
        }
    }
    
    @Test
    public void testQueryProducts() {
        log.info("测试产品查询功能");
        
        // 查询所有产品
        ProductQuery queryAll = ProductQuery.all();
        List<ProductQueryDTO> products = queryService.queryProducts(queryAll);
        log.info("查询到{}个产品", products.size());
        
        // 按产品类型查询产品
        if (!products.isEmpty()) {
            String productType = products.get(0).getProductType();
            ProductQuery queryByType = ProductQuery.byProductType(productType);
            List<ProductQueryDTO> typeProducts = queryService.queryProducts(queryByType);
            log.info("产品类型{}有{}个产品", productType, typeProducts.size());
        }
        
        // 查询单个产品
        if (!products.isEmpty()) {
            String productId = products.get(0).getProductId();
            ProductQueryDTO product = queryService.getProduct(productId);
            log.info("产品信息: {}", product);
            
            // 按产品代码查询
            String productCode = product.getProductCode();
            ProductQueryDTO productByCode = queryService.getProductByCode(productCode);
            log.info("按产品代码查询结果: {}", productByCode);
        }
    }
    
    @Test
    public void testQueryTrades() {
        log.info("测试成交记录查询功能");
        
        // 查询所有成交记录
        TradeQuery queryAll = TradeQuery.all();
        List<TradeQueryDTO> trades = queryService.queryTrades(queryAll);
        log.info("查询到{}个成交记录", trades.size());
        
        // 按客户ID查询成交记录
        if (!trades.isEmpty()) {
            String customerId = trades.get(0).getCustomerId();
            TradeQuery queryByCustomer = TradeQuery.byCustomerId(customerId);
            List<TradeQueryDTO> customerTrades = queryService.queryTrades(queryByCustomer);
            log.info("客户{}有{}个成交记录", customerId, customerTrades.size());
        }
        
        // 查询单个成交记录
        if (!trades.isEmpty()) {
            String tradeId = trades.get(0).getTradeId();
            TradeQueryDTO trade = queryService.getTrade(tradeId);
            log.info("成交记录信息: {}", trade);
        }
    }
    
    @Test
    public void testQuerySettlements() {
        log.info("测试结算记录查询功能");
        
        // 查询所有结算记录
        SettlementQuery queryAll = SettlementQuery.all();
        List<SettlementQueryDTO> settlements = queryService.querySettlements(queryAll);
        log.info("查询到{}个结算记录", settlements.size());
        
        // 按客户ID查询结算记录
        if (!settlements.isEmpty()) {
            String customerId = settlements.get(0).getCustomerId();
            SettlementQuery queryByCustomer = SettlementQuery.byCustomerId(customerId);
            List<SettlementQueryDTO> customerSettlements = queryService.querySettlements(queryByCustomer);
            log.info("客户{}有{}个结算记录", customerId, customerSettlements.size());
        }
        
        // 查询单个结算记录
        if (!settlements.isEmpty()) {
            String settlementId = settlements.get(0).getSettlementId();
            SettlementQueryDTO settlement = queryService.getSettlement(settlementId);
            log.info("结算记录信息: {}", settlement);
        }
    }
}