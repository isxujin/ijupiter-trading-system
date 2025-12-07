# 客户管理模块 (Customer Module)

## 概述

客户管理模块是证券交易系统的核心模块之一，负责管理投资者客户的全生命周期，包括客户信息管理、交易账户管理、资金账户管理、银行卡绑定和交易所账号绑定等功能。

## 模块结构

### API层 (securities-trading-api/business-api/customer-api)
- **枚举类**:
  - `CustomerStatus`: 客户状态枚举（正常、冻结、注销）
  - `TradingAccountType`: 交易账户类型枚举（股票、期货、期权、基金、债券）
  - `FundAccountType`: 资金账户类型枚举（主资金账户、保证金账户、第三方存管账户）

- **DTO类**:
  - `CustomerDTO`: 客户数据传输对象
  - `TradingAccountDTO`: 交易账户数据传输对象
  - `FundAccountDTO`: 资金账户数据传输对象
  - `BankCardDTO`: 银行卡数据传输对象
  - `ExchangeAccountDTO`: 交易所账号数据传输对象

- **命令类**:
  - 客户相关命令：`CreateCustomerCommand`, `UpdateCustomerCommand`, `FreezeCustomerCommand`, `UnfreezeCustomerCommand`, `CancelCustomerCommand`
  - 账户相关命令：`CreateTradingAccountCommand`, `CreateFundAccountCommand`

- **事件类**:
  - 客户相关事件：`CustomerCreatedEvent`, `CustomerUpdatedEvent`, `CustomerFrozenEvent`, `CustomerUnfrozenEvent`, `CustomerCancelledEvent`
  - 账户相关事件：`TradingAccountCreatedEvent`, `FundAccountCreatedEvent`

- **服务接口**:
  - `CustomerService`: 客户管理服务接口，定义了客户管理的所有操作

### 核心层 (securities-trading-core/customer-core)
- **聚合类**:
  - `CustomerAggregate`: 客户聚合，处理客户相关的命令和事件
  - `TradingAccountAggregate`: 交易账户聚合，处理交易账户相关的命令和事件
  - `FundAccountAggregate`: 资金账户聚合，处理资金账户相关的命令和事件

- **实体类**:
  - `CustomerEntity`: 客户实体，映射到customer表
  - `TradingAccountEntity`: 交易账户实体，映射到trading_account表
  - `FundAccountEntity`: 资金账户实体，映射到fund_account表
  - `BankCardEntity`: 银行卡实体，映射到bank_card表
  - `ExchangeAccountEntity`: 交易所账号实体，映射到exchange_account表

- **仓储接口**:
  - `CustomerRepository`: 客户数据访问接口
  - `TradingAccountRepository`: 交易账户数据访问接口
  - `FundAccountRepository`: 资金账户数据访问接口
  - `BankCardRepository`: 银行卡数据访问接口
  - `ExchangeAccountRepository`: 交易所账号数据访问接口

- **服务实现**:
  - `CustomerServiceImpl`: 客户服务实现类

- **配置类**:
  - `CustomerConfig`: 客户模块Axon配置，配置聚合的事件存储仓库

### Web层 (securities-trading-web/customer-web)
- **控制器**:
  - `CustomerController`: 客户管理控制器，提供REST API和页面路由

### 数据库层
- **表结构**:
  - `customer`: 客户表
  - `trading_account`: 交易账户表
  - `fund_account`: 资金账户表
  - `bank_card`: 银行卡表
  - `exchange_account`: 交易所账号表
  - Axon事件表和快照表

## 主要功能

### 1. 客户管理
- 客户新增、注销、冻结、解冻
- 客户登录、登出
- 客户信息查询（按ID、编号、状态等）
- 客户信息更新

### 2. 交易账户管理
- 交易账户新增、注销、冻结、解冻
- 交易所交易账号绑定、解绑
- 交易账户查询（按客户ID、账户ID等）

### 3. 资金账户管理
- 资金账户新增、注销、冻结、解冻
- 银行卡绑定、解绑
- 资金账户查询（按客户ID、账户ID等）

## 使用指南

### 1. 创建客户
```java
CreateCustomerCommand command = new CreateCustomerCommand(
    "C10001", 
    "C10001", 
    "张三", 
    "110101199001011234", 
    "13800138000", 
    "zhangsan@example.com", 
    "北京市朝阳区", 
    "R2"
);
String customerId = customerService.createCustomer(command);
```

### 2. 创建交易账户
```java
CreateTradingAccountCommand command = new CreateTradingAccountCommand(
    "TA10001", 
    "C10001", 
    "张三证券账户", 
    "SHSE", 
    TradingAccountType.STOCK
);
String accountId = customerService.createTradingAccount(command);
```

### 3. 创建资金账户
```java
CreateFundAccountCommand command = new CreateFundAccountCommand(
    "FA10001", 
    "C10001", 
    "张三资金账户", 
    FundAccountType.MAIN
);
String accountId = customerService.createFundAccount(command);
```

### 4. 绑定银行卡
```java
BankCardDTO bankCard = new BankCardDTO();
bankCard.setCardNumber("6225881234567890");
bankCard.setBankCode("ICBC");
bankCard.setBankName("中国工商银行");
bankCard.setHolderName("张三");

String bankCardId = customerService.bindBankCard("FA10001", bankCard);
```

### 5. 绑定交易所账号
```java
ExchangeAccountDTO exchangeAccount = new ExchangeAccountDTO();
exchangeAccount.setExchangeCode("SHSE");
exchangeAccount.setExchangeName("上海证券交易所");
exchangeAccount.setExchangeAccountNumber("1234567890");
exchangeAccount.setTradingPassword("encrypted_password");
exchangeAccount.setFundPassword("encrypted_password");

String exchangeAccountId = customerService.bindExchangeAccount("TA10001", exchangeAccount);
```

## REST API

### 客户相关API
- `GET /customer/api/list`: 查询客户列表
- `GET /customer/api/detail/{id}`: 查询客户详情
- `POST /customer/api/create`: 创建客户
- `POST /customer/api/update`: 更新客户信息
- `POST /customer/api/freeze`: 冻结客户
- `POST /customer/api/unfreeze`: 解冻客户
- `POST /customer/api/cancel`: 注销客户

### 交易账户相关API
- `GET /customer/api/trading-account/list/{customerId}`: 查询客户的交易账户列表
- `POST /customer/api/trading-account/create`: 创建交易账户
- `POST /customer/api/trading-account/freeze`: 冻结交易账户
- `POST /customer/api/trading-account/unfreeze`: 解冻交易账户
- `POST /customer/api/trading-account/close`: 注销交易账户
- `POST /customer/api/exchange-account/bind`: 绑定交易所账号
- `POST /customer/api/exchange-account/unbind`: 解绑交易所账号

### 资金账户相关API
- `GET /customer/api/fund-account/list/{customerId}`: 查询客户的资金账户列表
- `POST /customer/api/fund-account/create`: 创建资金账户
- `POST /customer/api/fund-account/freeze`: 冻结资金账户
- `POST /customer/api/fund-account/unfreeze`: 解冻资金账户
- `POST /customer/api/fund-account/close`: 注销资金账户
- `POST /customer/api/bank-card/bind`: 绑定银行卡
- `POST /customer/api/bank-card/unbind`: 解绑银行卡

## 权限控制

所有API都基于Spring Security进行权限控制，需要相应的权限才能访问：

- 客户相关权限：`customer:view`, `customer:create`, `customer:update`, `customer:freeze`, `customer:unfreeze`, `customer:cancel`
- 账户相关权限：`customer:account:view`, `customer:account:create`, `customer:account:freeze`, `customer:account:unfreeze`, `customer:account:close`
- 银行卡相关权限：`customer:card:view`, `customer:card:bind`, `customer:card:unbind`
- 交易所相关权限：`customer:exchange:view`, `customer:exchange:bind`, `customer:exchange:unbind`

## 数据库初始化

模块启动时，会自动执行`customer_schema.sql`脚本，创建所需的数据库表结构。脚本中包含了所有必要的表、索引、外键约束以及一些示例数据。

## 事件溯源

模块使用Axon Framework实现事件溯源，所有聚合的状态变更都以事件的形式持久化到事件表中，支持：

1. 事件重建：根据事件流重建聚合状态
2. 快照：定期保存聚合快照，提高重建效率
3. 事件查询：查询聚合的事件历史

## 安全考虑

1. 敏感信息如密码、API密钥等在数据库中使用加密存储
2. 所有外部访问都需要身份验证和授权
3. 所有状态变更都有审计记录

## 扩展点

1. 自定义客户状态：在`CustomerStatus`枚举中添加新的状态
2. 自定义账户类型：在`TradingAccountType`和`FundAccountType`枚举中添加新类型
3. 自定义验证规则：在服务实现中添加自定义业务逻辑
4. 自定义事件处理：添加事件处理器处理特定事件

## 后续规划

1. 客户标签和分组功能
2. 客户交易统计和分析
3. 客户风险评估模型
4. 客户行为分析
5. 客户画像功能