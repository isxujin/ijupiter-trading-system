package net.ijupiter.trading.web.query.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.api.query.queries.*;
import net.ijupiter.trading.api.query.services.QueryService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 查询控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/query")
public class QueryController extends BaseController {
    
    @Autowired
    private QueryGateway queryGateway;
    
    /**
     * 查询客户综合信息
     */
    @GetMapping("/customer/financial-summary/{customerId}")
    public CompletableFuture<ResponseEntity<CustomerFinancialSummaryDTO>> queryCustomerFinancialSummary(
            @PathVariable Long customerId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        
        CustomerFinancialSummaryQuery query = CustomerFinancialSummaryQuery.builder()
                .customerId(customerId)
                .build();
        
        log.info("查询客户综合信息: customerId={}", customerId);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(CustomerFinancialSummaryDTO.class))
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    log.error("查询客户综合信息失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 查询客户交易流水
     */
    @GetMapping("/customer/transactions/{customerId}")
    public CompletableFuture<ResponseEntity<Page<CustomerTransactionSummaryDTO>>> queryCustomerTransactions(
            @PathVariable Long customerId,
            @RequestParam(required = false) String securityCode,
            @RequestParam(required = false) Integer transactionType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer market,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        CustomerTransactionSummaryQuery query = CustomerTransactionSummaryQuery.builder()
                .customerId(customerId)
                .securityCode(securityCode)
                .transactionType(transactionType)
                .status(status)
                .market(market)
                .page(page)
                .size(size)
                .build();
        
        log.info("查询客户交易流水: customerId={}, page={}, size={}", customerId, page, size);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(Page.class))
                .thenApply(response -> ResponseEntity.ok((Page<CustomerTransactionSummaryDTO>) response))
                .exceptionally(throwable -> {
                    log.error("查询客户交易流水失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 查询客户资金流水
     */
    @GetMapping("/customer/funding-transactions/{customerId}")
    public CompletableFuture<ResponseEntity<Page<CustomerFundingTransactionSummaryDTO>>> queryCustomerFundingTransactions(
            @PathVariable Long customerId,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Integer transactionType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        CustomerFundingTransactionSummaryQuery query = CustomerFundingTransactionSummaryQuery.builder()
                .customerId(customerId)
                .fundingAccountId(accountId)  // 使用fundingAccountId而不是accountId
                .transactionType(transactionType)
                .status(status)
                .page(page)
                .size(size)
                .build();
        
        log.info("查询客户资金流水: customerId={}, page={}, size={}", customerId, page, size);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(Page.class))
                .thenApply(response -> ResponseEntity.ok((Page<CustomerFundingTransactionSummaryDTO>) response))
                .exceptionally(throwable -> {
                    log.error("查询客户资金流水失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 查询客户资金账户余额和发生额
     */
    @GetMapping("/customer/funding-balance/{customerId}")
    public CompletableFuture<ResponseEntity<List<CustomerFundingBalanceDTO>>> queryCustomerFundingBalance(
            @PathVariable Long customerId,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String referenceDate) {
        
        CustomerFundingBalanceQuery query = CustomerFundingBalanceQuery.builder()
                .customerId(customerId)
                .accountId(accountId)
                .accountType(accountType)
                .status(status)
                .build();
        
        log.info("查询客户资金账户余额和发生额: customerId={}", customerId);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(List.class))
                .thenApply(response -> ResponseEntity.ok((List<CustomerFundingBalanceDTO>) response))
                .exceptionally(throwable -> {
                    log.error("查询客户资金账户余额和发生额失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 查询客户证券持仓信息
     */
    @GetMapping("/customer/securities-positions/{customerId}")
    public CompletableFuture<ResponseEntity<Page<CustomerSecuritiesPositionDTO>>> queryCustomerSecuritiesPositions(
            @PathVariable Long customerId,
            @RequestParam(required = false) String securityCode,
            @RequestParam(required = false) Integer securityType,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        CustomerSecuritiesPositionQuery query = CustomerSecuritiesPositionQuery.builder()
                .customerId(customerId)
                .securityCode(securityCode)
                .securityType(securityType)
                .page(page)
                .size(size)
                .build();
        
        log.info("查询客户证券持仓信息: customerId={}, page={}, size={}", customerId, page, size);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(Page.class))
                .thenApply(response -> ResponseEntity.ok((Page<CustomerSecuritiesPositionDTO>) response))
                .exceptionally(throwable -> {
                    log.error("查询客户证券持仓信息失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 查询客户每日证券收益信息
     */
    @GetMapping("/customer/daily-securities-profit/{customerId}")
    public CompletableFuture<ResponseEntity<Page<CustomerDailySecuritiesProfitDTO>>> queryCustomerDailySecuritiesProfit(
            @PathVariable Long customerId,
            @RequestParam(required = false) String securityCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = startDate != null ? LocalDate.parse(startDate, formatter) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate, formatter) : null;
        
        CustomerDailySecuritiesProfitQuery query = CustomerDailySecuritiesProfitQuery.builder()
                .customerId(customerId)
                .securityCode(securityCode)
                .startDate(start)
                .endDate(end)
                .page(page)
                .size(size)
                .build();
        
        log.info("查询客户每日证券收益信息: customerId={}, page={}, size={}", customerId, page, size);
        
        return queryGateway.query(query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(Page.class))
                .thenApply(response -> ResponseEntity.ok((Page<CustomerDailySecuritiesProfitDTO>) response))
                .exceptionally(throwable -> {
                    log.error("查询客户每日证券收益信息失败", throwable);
                    return ResponseEntity.internalServerError().build();
                });
    }
    
    /**
     * 订阅客户综合信息变化
     */
    @GetMapping("/customer/financial-summary/{customerId}/subscribe")
    public SubscriptionQueryResult<CustomerFinancialSummaryDTO, CustomerFinancialSummaryDTO> subscribeCustomerFinancialSummary(
            @PathVariable Long customerId) {
        
        CustomerFinancialSummaryQuery query = CustomerFinancialSummaryQuery.builder()
                .customerId(customerId)
                .build();
        
        log.info("订阅客户综合信息变化: customerId={}", customerId);
        
        return queryGateway.subscriptionQuery(
                query, 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(CustomerFinancialSummaryDTO.class),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(CustomerFinancialSummaryDTO.class)
        );
    }
}