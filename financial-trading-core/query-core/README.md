# 查询模块

## 模块概述

查询模块（query-core）是ijupiter交易系统的查询服务实现，遵循CQRS（命令查询责任分离）架构模式，将查询操作与命令处理分离，提供高效的查询服务。

## 主要功能

- 账户查询：支持按客户ID、账户类型等多维度查询账户信息
- 订单查询：支持按客户、产品、时间范围等条件查询订单信息
- 资金账户查询：支持查询资金账户余额、冻结资金等信息
- 产品查询：支持查询证券产品的基本信息和市场数据
- 成交记录查询：支持查询历史成交记录和交易统计
- 结算记录查询：支持查询结算记录和费用明细

## 架构设计

### 事件处理器

模块包含多个事件处理器，监听领域事件并更新查询视图：

- `AccountEventHandler`：处理账户相关事件
- `OrderEventHandler`：处理订单相关事件
- `FundAccountEventHandler`：处理资金账户相关事件
- `ProductEventHandler`：处理产品相关事件
- `TradeEventHandler`：处理成交相关事件
- `SettlementEventHandler`：处理结算相关事件

### 视图模型

每个领域对象都有对应的视图模型，用于查询：

- `AccountView`：账户视图
- `OrderView`：订单视图
- `FundAccountView`：资金账户视图
- `ProductView`：产品视图
- `TradeView`：成交记录视图
- `SettlementView`：结算记录视图

### 查询服务

`QueryService`接口和`QueryServiceImpl`实现类提供了完整的查询功能，支持条件查询和分页。

## 配置说明

### 数据库配置

查询模块使用MySQL数据库存储视图数据，支持通过配置文件进行数据库连接配置。

### 事件处理器配置

通过`QueryProperties`类可以配置事件处理器的线程池大小、批次大小等参数。

### 缓存配置

支持配置查询结果的缓存，提高查询性能。

## 使用示例

```java
// 查询客户的所有订单
OrderQuery query = OrderQuery.byCustomerId("customer123");
List<OrderQueryDTO> orders = queryService.queryOrders(query);

// 按产品代码查询产品信息
ProductQueryDTO product = queryService.getProductByCode("000001");

// 分页查询成交记录
TradeQuery tradeQuery = TradeQuery.all();
tradeQuery.setPage(1);
tradeQuery.setSize(20);
List<TradeQueryDTO> trades = queryService.queryTrades(tradeQuery);
```

## 部署说明

1. 确保MySQL数据库已创建并配置正确
2. 根据实际环境修改application.yml配置
3. 启动QueryApplication类

## 测试

模块包含完整的单元测试，使用H2内存数据库进行测试，可以通过运行QueryServiceTests类验证功能。