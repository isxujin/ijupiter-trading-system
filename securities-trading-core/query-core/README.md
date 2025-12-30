# Query Core Module

## 概述

Query Core模块实现了CQRS设计模式中的查询部分，提供对客户综合信息的查询功能，使用QueryBus而非CommandBus，确保读写分离。

## 功能特性

1. **客户综合信息查询**
   - 查询客户基础信息
   - 查询资金账户信息
   - 查询证券账户信息
   - 计算总资产

2. **客户交易流水查询**
   - 支持按客户、证券、交易类型、状态等条件过滤
   - 支持分页查询
   - 包含交易详情和结算信息

3. **客户资金流水查询**
   - 支持按客户、账户、交易类型等条件过滤
   - 记录资金变动前后状态
   - 支持分页查询

4. **客户资金账户余额和发生额查询**
   - 查询当前余额
   - 计算日、月、年发生额
   - 支持多账户查询

5. **客户证券持仓信息查询**
   - 查询持仓详情和成本
   - 计算当前市值
   - 计算盈亏金额和盈亏率

6. **客户每日证券收益信息查询**
   - 记录每日价格变化
   - 计算每日盈亏
   - 计算累计盈亏和涨跌幅

## 架构设计

### 查询处理器
使用`@QueryHandler`注解的处理器类来响应查询请求：

```java
@Component
public class CustomerFinancialSummaryQueryHandler {
    @QueryHandler
    public CustomerFinancialSummaryDTO handle(CustomerFinancialSummaryQuery query) {
        // 查询处理逻辑
    }
}
```

### 查询对象
所有查询对象都实现了`QueryMessage`接口，包含查询条件和分页参数：

```java
public class CustomerTransactionSummaryQuery implements QueryMessage<CustomerTransactionSummaryQuery, CustomerTransactionSummaryDTO> {
    private Long customerId;
    private String securityCode;
    private Integer page = 1;
    private Integer size = 20;
    // 其他查询条件...
}
```

### 查询结果
使用DTO对象返回查询结果，提供清晰的字段映射：

```java
public class CustomerFinancialSummaryDTO extends BaseDTO<CustomerFinancialSummaryDTO> {
    private Long customerId;
    private String customerName;
    private List<FundingAccountSummaryDTO> fundingAccounts;
    private List<SecuritiesAccountSummaryDTO> securitiesAccounts;
    // 其他字段...
}
```

## 使用示例

### 查询客户综合信息
```java
CustomerFinancialSummaryQuery query = CustomerFinancialSummaryQuery.builder()
    .customerId(123L)
    .includeFundingAccounts(true)
    .includeSecuritiesAccounts(true)
    .build();

CustomerFinancialSummaryDTO result = queryGateway.query(query).get();
```

### 查询客户交易流水
```java
CustomerTransactionSummaryQuery query = CustomerTransactionSummaryQuery.builder()
    .customerId(123L)
    .securityCode("000001")
    .transactionType(1)
    .page(1)
    .size(20)
    .build();

Page<CustomerTransactionSummaryDTO> result = queryGateway.query(query).get();
```

### 订阅客户综合信息变化
```java
CustomerFinancialSummaryQuery query = CustomerFinancialSummaryQuery.builder()
    .customerId(123L)
    .build();

SubscriptionQueryResult<CustomerFinancialSummaryDTO, CustomerFinancialSummaryDTO> subscription = 
    queryGateway.subscriptionQuery(query, CustomerFinancialSummaryDTO.class);

// 处理订阅结果
subscription.thenAccept(result -> {
    // 处理更新的客户信息
});
```

## 查询API端点

### 客户综合信息
```
GET /api/query/customer/financial-summary/{customerId}
```

### 客户交易流水
```
GET /api/query/customer/transactions/{customerId}
?securityCode={securityCode}&transactionType={transactionType}&page={page}&size={size}
```

### 客户资金流水
```
GET /api/query/customer/funding-transactions/{customerId}
?accountId={accountId}&transactionType={transactionType}&page={page}&size={size}
```

### 客户资金账户余额
```
GET /api/query/customer/funding-balance/{customerId}
?accountId={accountId}&accountType={accountType}&status={status}
```

### 客户证券持仓信息
```
GET /api/query/customer/securities-positions/{customerId}
?securityCode={securityCode}&securityType={securityType}&page={page}&size={size}
```

### 客户每日证券收益信息
```
GET /api/query/customer/daily-securities-profit/{customerId}
?securityCode={securityCode}&startDate={startDate}&endDate={endDate}&page={page}&size={size}
```

### 订阅客户综合信息变化
```
GET /api/query/customer/financial-summary/{customerId}/subscribe
```

## 性能优化

1. **查询缓存**
   - 使用Redis缓存常用查询结果
   - 支持TTL和最大缓存大小配置

2. **分页查询**
   - 所有列表查询都支持分页
   - 避免一次性加载大量数据

3. **只读数据源**
   - 配置只读数据源，提高查询性能
   - 减少数据库锁竞争

4. **异步查询**
   - 使用CompletableFuture支持异步查询
   - 提高系统并发处理能力

## 扩展指南

1. **添加新查询**
   - 创建查询DTO
   - 创建查询对象
   - 创建查询处理器
   - 添加查询API端点

2. **优化查询性能**
   - 添加索引支持
   - 使用数据库特定优化
   - 实现多级缓存策略