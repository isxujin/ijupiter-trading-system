# iJupiter 证券交易系统架构文档

## 目录

1. [系统概述](#系统概述)
2. [技术栈](#技术栈)
3. [模块架构](#模块架构)
4. [设计原则](#设计原则)
5. [交互流程](#交互流程)
6. [部署架构](#部署架构)

## 系统概述

iJupiter证券交易系统是一个基于事件引擎架构的证券交易平台，支持证券、基金、期货等多种金融产品的交易业务。系统采用现代化的微服务架构，遵循DDD（领域驱动设计）和CQRS（命令查询责任分离）模式，使用Spring Boot和Axon Framework构建，具备高可扩展性、高性能和高可用性特点。

系统以事件驱动为核心，通过领域事件解耦各业务模块，实现异步处理和数据最终一致性。

## 技术栈

### 核心技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 17 | 编程语言 |
| Spring Boot | 3.2.5 | 应用框架 |
| Axon Framework | 4.12.2 | 事件驱动架构框架 |
| MySQL | 8.4.0 | 主数据存储 |
| Redis | 7 | 缓存与会话存储 |
| RabbitMQ | 5.20.0 | 消息队列 |
| MongoDB | 6.0 | 文档数据库 |
| Spring Security | 6.5.7 | 安全框架 |
| Thymeleaf | 3.2.6.RELEASE | 服务端模板引擎 |
| Jakarta Persistence | 3.1.0 | JPA API |
| Lombok | 1.18.42 | 代码简化工具 |
| Hutool | 5.8.42 | Java工具库 |
| WebJars | 多种版本 | 前端资源管理 |

### 编码与构建

- **编码**: UTF-8（全局统一）
- **构建工具**: Maven Wrapper 3.9.5 (无需本地安装Maven)
- **包管理**: Maven 多模块项目
- **Java版本**: 17
- **编译器插件**: Maven Compiler Plugin 3.11.0
- **编码转换**: 提供convert-to-utf8.sh脚本处理编码问题

## 模块架构

### 整体架构

```
ijupiter-trading-system (父模块)
├── .mvn                           # Maven Wrapper配置
│   └── wrapper                   # Wrapper脚本和配置
├── securities-trading-boms            # 依赖版本统一管理
├── securities-trading-common           # 公共工具和基础组件
├── securities-trading-api              # API接口定义层
│   ├── business-api                 # 业务API
│   │   ├── securities-api            # 账户API (原 account-api)
│   │   ├── customer-api           # 客户管理API
│   │   ├── funding-api            # 资金API（原 fund-api）
│   │   ├── product-api            # 产品API
│   │   ├── system-api             # 系统管理API
│   │   ├── trading-engine-api      # 交易引擎API
│   │   ├── settlement-api         # 结算API
│   │   └── query-api             # 查询API
│   └── middleware-spi              # 中间件SPI（服务提供者接口）
│       ├── message-adapter-spi     # 消息适配器SPI
│       └── cache-adapter-spi      # 缓存适配器SPI
├── securities-trading-core            # 核心业务实现层
│   ├── securities-core                # 账户核心服务 (原 account-core)
│   ├── customer-core               # 客户管理核心服务
│   ├── funding-core                # 资金核心服务（原 fund-core）
│   ├── product-core                # 产品核心服务
│   ├── system-core                 # 系统管理核心服务
│   ├── trading-engine-core         # 交易引擎核心服务
│   ├── settlement-core             # 结算核心服务
│   └── query-core                  # 查询核心服务
├── securities-trading-middleware      # 中间件适配器层
│   ├── rabbitmq-adapter            # RabbitMQ消息适配器
│   └── redis-adapter             # Redis缓存适配器
├── securities-trading-web             # Web表示层
│   ├── common-web                 # 公共Web模块，提供视图层框架资源和控制层公共资源
│   ├── customer-web               # 客户管理Web模块
│   ├── management-web             # 管理端Web模块
│   ├── investor-web               # 投资者端Web模块
│   └── system-web                 # 系统管理Web模块
├── securities-trading-boots            # 应用启动层
│   ├── service-allinone-boot       # 服务单体启动器
│   ├── service-test-boot          # 服务测试启动器
│   ├── web-allinone-boot         # Web单体启动器
│   └── web-test-boot            # Web测试启动器
├── mvnw / mvnw.cmd / mvnw.sh       # Maven Wrapper脚本
├── init-trading-system.sh          # 项目初始化脚本
├── verify-mvnw.sh                 # Maven Wrapper验证脚本
├── convert-to-utf8.sh             # 编码转换脚本
└── MAVEN_WRAPPER.md               # Maven Wrapper使用指南
```

### 模块详细说明

#### 1. securities-trading-boms
- **职责**: 统一管理所有模块的依赖版本
- **核心组件**: Maven BOM文件
- **特点**: 版本一致性、依赖传递控制

#### 2. securities-trading-common
- **职责**: 提供公共工具类、常量和基础组件
- **核心组件**: 
  - 通用工具类
  - 常量定义
  - 基础异常类
  - 通用枚举
- **特点**: 可复用、轻量级

#### 3. securities-trading-api
- **职责**: 定义系统各模块间的接口契约
- **子模块**:
  - **business-api**: 业务领域API，包括账户、客户管理、资金、产品、系统管理、交易、结算、查询等API
    - **customer-api**: 客户管理API，提供客户、交易账户、资金账户等接口
    - **system-api**: 系统管理API，提供操作员、角色、权限、数据字典等接口
  - **middleware-spi**: 中间件SPI（服务提供者接口），定义中间件实现的标准接口
    - **message-adapter-spi**: 消息适配器SPI，定义消息服务的标准接口
    - **cache-adapter-spi**: 缓存适配器SPI，定义缓存服务的标准接口

#### 4. securities-trading-core
- **职责**: 实现核心业务逻辑和事件处理
- **子模块**:
  - **securities-core**: 账户管理核心，处理用户账户、权限等（原 account-core）
  - **customer-core**: 客户管理核心，处理客户信息、交易账户、资金账户等
    - 客户账户拆分设计：交易账户拆分为基本信息和持仓，资金账户拆分为基本信息和余额
    - 银行卡信息已合并到资金账户，交易所账号信息已合并到交易账户
    - 实体层：客户、交易账户基本信息、交易账户持仓、资金账户基本信息、资金账户余额等实体
    - 仓储层：各实体的Repository接口
    - 服务层：客户管理相关Service实现
  - **funding-core**: 资金管理核心，处理资金划拨、冻结、解冻等（原 fund-core）
  - **product-core**: 产品管理核心，处理金融产品定义、规则等
  - **system-core**: 系统管理核心，处理操作员、角色、权限、数据字典等
    - **实体层**: 操作员、角色、权限、数据字典等实体定义
    - **仓储层**: 各实体的Repository接口
    - **服务层**: 各实体对应的Service实现
  - **trading-engine-core**: 交易引擎核心，处理订单撮合、成交等
  - **settlement-core**: 结算核心，处理资金结算、交收等
  - **query-core**: 查询核心，处理各种查询请求

#### 5. securities-trading-middleware
- **职责**: 提供中间件技术适配器
- **子模块**:
  - **rabbitmq-adapter**: RabbitMQ消息适配器，处理异步消息
  - **redis-adapter**: Redis缓存适配器，处理缓存和会话

#### 6. securities-trading-web
- **职责**: 提供Web界面和API入口
- **子模块**:
  - **common-web**: 公共Web模块，提供视图层框架资源和控制层公共资源，包括：
    - Spring MVC和Thymeleaf配置
    - WebJars资源管理（Bootstrap和jQuery）
    - 基础控制器类和统一API响应格式（Result和PageResult）
    - 统一的页面模板结构
    - 包结构调整：控制器包名从/controller调整为/controllers，模型包名从/dto调整为/models
  - **customer-web**: 客户管理Web模块，提供客户管理界面，继承common-web的公共资源
  - **management-web**: 管理端Web模块，提供后台管理界面，继承common-web的公共资源
  - **investor-web**: 投资者端Web模块，提供交易界面，继承common-web的公共资源
  - **system-web**: 系统管理Web模块，提供系统设置界面，继承common-web的公共资源

#### 7. securities-trading-boots
- **职责**: 提供不同场景的应用启动入口
- **子模块**:
  - **service-allinone-boot**: 服务单体启动器，包含所有核心服务模块
  - **service-test-boot**: 服务测试启动器，用于测试环境
  - **web-allinone-boot**: Web单体启动器，包含所有Web界面模块
  - **web-test-boot**: Web测试启动器，用于Web测试环境
- **特点**: 支持单体启动和测试分离，方便不同场景部署

## 设计原则

### 1. 领域驱动设计（DDD）
- 系统按照业务领域划分模块
- 每个核心模块对应一个业务领域
- 明确的领域边界和上下文映射

### 2. 命令查询责任分离（CQRS）
- 命令（写操作）和查询（读操作）分离
- 针对不同的操作模型进行优化
- 支持独立的扩展和部署

### 3. 事件驱动架构
- 使用Axon Framework实现事件驱动
- 业务操作产生领域事件
- 事件触发下游系统的异步处理

### 4. 微服务架构
- 系统按业务垂直拆分
- 每个服务独立部署和扩展
- 通过API和事件进行服务间通信

### 5. Web公共模块设计
- **common-web**: 作为Web层的公共基础模块，采用面向对象设计和继承机制
  - 提供统一的视图层框架（Spring MVC和Thymeleaf）
  - 集成WebJars管理前端资源（Bootstrap和jQuery）
  - 定义基础控制器类和统一API响应格式
  - 实现页面模板继承和组件复用
  - 支持模块化扩展，各Web模块可继承和扩展公共功能
- **继承设计**: management-web、investor-web和system-web继承common-web，避免代码重复
- **资源统一**: 所有前端资源通过WebJars统一管理，确保版本一致性

### 6. 客户管理模块设计
- **customer-core**: 客户管理核心模块，采用DDD分层架构
  - **聚合设计**: 客户聚合、交易账户聚合、资金账户聚合
  - **实体拆分**: 
    - 交易账户拆分为基本信息和持仓两部分
    - 资金账户拆分为基本信息和余额两部分
  - **信息合并**: 
    - 银行卡信息合并到资金账户基本信息
    - 交易所账号信息合并到交易账户基本信息
  - **仓储层**: 各实体对应的Repository接口
  - **服务层**: 实现客户管理相关的业务逻辑
  - **事件驱动**: 使用Axon Framework实现事件溯源和命令处理
- **customer-web**: 客户管理Web模块，继承common-web的公共资源
  - 提供完整的客户管理界面
  - 支持客户信息管理、交易账户管理、资金账户管理等功能
- **数据模型设计**: 
  - DTO与Entity分离：DTO不包含系统字段（createTime、updateTime、version），仅在Entity中保留
  - 业务时间字段：使用openDate、closeDate等业务字段替代系统时间字段

### 7. 系统管理模块设计
- **system-core**: 系统管理核心模块，采用MVC分层架构
  - **实体层**: 操作员、角色、权限、数据字典、系统配置等实体
  - **仓储层**: 提供JPA Repository接口和实现，支持复杂查询和分页
  - **服务层**: 实现操作员、角色、权限、数据字典、系统配置的业务逻辑
  - **事务管理**: 使用Spring事务注解确保数据一致性
- **system-web**: 系统管理Web模块，继承common-web的公共资源
  - 提供完整的系统设置界面
  - 支持操作员管理、角色权限管理、数据字典管理等功能

### 8. 数据模型设计规范
- **系统字段与业务字段分离**:
  - createTime、updateTime和version这三个字段只表示数据库记录的新增时间、修改时间、记录乐观锁
  - 这三个字段不包含其他业务含义，仅在Entity中保留
  - 业务上有开户时间、销户时间等属性时，使用openDate、closeDate表示

- **主键与业务标识分离**:
  - 系统所有实体表都包含id主键属性，该属性无业务含义
  - 如果业务实体需要一个具备业务含义的唯一标识，使用以code（编码/编号）为后缀的属性命名
  - 例如：customerCode、accountCode、productCode等

- **继承体系设计**:
  - 系统数据实体对象单根继承自BaseEntity
  - 系统数据传输对象单根继承自BaseDTO
  - 系统服务接口单根继承自BaseService接口
  - BaseEntity定义了公共方法convertFrom用于从其他类型对象复制属性到Entity对象
  - BaseDTO定义了公共方法convertFrom用于从其他类型对象复制属性到DTO对象

### 9. Maven Wrapper集成
- 统一构建环境，确保所有开发者使用相同的Maven版本
- 自动下载Maven 3.9.5，无需本地安装Maven
- 提供验证和初始化脚本，简化环境设置
- 支持跨平台构建（Windows、Linux、macOS）

## 交互流程

### 客户管理流程

```
1. 操作员通过customer-web界面登录系统
   ↓
2. 操作员管理客户信息、交易账户、资金账户等
   ↓
3. 调用customer-core服务执行客户管理操作
   ↓
4. 操作结果持久化到数据库
   ↓
5. 通过common-web提供的公共界面展示操作结果
```

### 系统管理流程

```
1. 系统管理员通过system-web界面登录系统
   ↓
2. 管理员管理操作员账户、角色权限等
   ↓
3. 调用system-core服务执行系统管理操作
   ↓
4. 操作结果持久化到数据库
   ↓
5. 通过common-web提供的公共界面展示操作结果
```

### 交易流程

```
1. 用户通过Web界面提交交易请求
   ↓
2. Web终端接收请求，验证参数，发送命令
   ↓
3. 交易引擎核心处理命令，执行业务逻辑
   ↓
4. 产生领域事件（订单创建、订单匹配等）
   ↓
5. 事件处理器处理事件，更新相关数据
   ↓
6. 结果通过查询API返回给Web界面
```

### 结算流程

```
1. 交易完成后产生结算事件
   ↓
2. 结算核心订阅事件，触发结算流程
   ↓
3. 执行结算计算和资金划拨
   ↓
4. 产生结算完成事件
   ↓
5. 资金核心订阅事件，更新账户资金
   ↓
6. 结果通过查询API返回
```

## 部署架构

### 开发环境
```
┌─────────────────────────────────────────────┐
│                  开发环境                     │
├─────────────────────────────────────────────┤
│  Web Boot (Spring Boot 3.2.5)            │
│  ┌─────────────┐  ┌─────────────┐      │
│  │ 管理端Web │  │ 投资者端Web │      │
│  └─────────────┘  └─────────────┘      │
│          │                    │             │
│          └──────────┬───────────┘             │
│                     ▼                         │
│         ┌─────────────────┐                 │
│         │ Service Boot    │                 │
│         │ (核心服务)      │                 │
│         └─────────────────┘                 │
│            │        │        │               │
│            ▼        ▼        ▼               │
│  ┌──────────┬ ───────────┬ ───────────┐ │
│  │ 账户服务 │ │ 资金服务 │ │ 交易服务 │ │
│  └──────────┘ └──────────┘ └──────────┘ │
│      │            │            │          │
│      ▼            ▼            ▼          │
│  ┌──────────────────┬──────────────────┐ │
│  │ MySQL          │ Redis             │ │
│  │ (主数据)        │ (缓存)            │ │
│  └──────────────────┴──────────────────┘ │
│           │                         │
│           ▼                         │
│     ┌─────────────────┐              │
│     │ RabbitMQ       │              │
│     │ (事件总线)      │              │
│     └─────────────────┘              │
└─────────────────────────────────────────────┘
```

### 生产环境
```
┌─────────────────────────────────────────────────────────────────┐
│                          生产环境                             │
├─────────────────────────────────────────────────────────────────┤
│   Web服务 (多实例)                                   │
│   ┌───────────────┐  ┌───────────────┐      │
│   │ 管理端Web     │  │ 投资者端Web     │      │
│   │ (负载均衡)     │  │ (负载均衡)     │      │
│   └───────────────┘  └───────────────┘      │
│          │                     │             │
│          └──────────┬───────────┘             │
│                     ▼                         │
│   ┌─────────────────────────┐                 │
│   │ Service集群            │                 │
│   │ (微服务实例)            │                 │
│   └─────────────────────────┘                 │
│      │         │        │         │               │
│      ▼         ▼        ▼         │               │
│  ┌──────────┬─┬──────────┬─┬──────────┐ │
│  │ 账户服务 │ │ 资金服务 │ │ 交易服务 │ │ 结算服务 │ │
│  │ (集群)   │ │ (集群)   │ │ (集群)   │ │ (集群)   │ │
│  └──────────┘─┴──────────┘─┴──────────┘ │
│      │            │           │          │         │
│      ▼            ▼           ▼          │         │
│  ┌──────────────────┬──────────────────┐ │
│  │ MySQL集群        │ Redis集群             │ │
│  │ (主数据)         │ (缓存)               │ │
│  └──────────────────┴──────────────────┘ │
│           │                            │          │
│           ▼                            │          │
│     ┌───────────────────────┐            │
│     │ RabbitMQ集群         │            │
│     │ (事件总线)            │            │
│     └───────────────────────┘            │
└─────────────────────────────────────────────────────────┘
```

## 性能考虑

### 1. 数据库优化
- 读写分离
- 分库分表策略
- 索引优化
- 连接池管理

### 2. 缓存策略
- 多级缓存（本地缓存 + Redis）
- 缓存预热
- 缓存一致性保证

### 3. 消息队列
- 异步处理提高响应速度
- 批量处理提高吞吐量
- 消息持久化保证可靠性

### 4. 服务扩展
- 水平扩展支持
- 无状态服务设计
- 负载均衡

## 安全考虑

### 1. 认证授权
- Spring Security框架
- JWT令牌机制
- 角色权限控制

### 2. 数据安全
- 敏感数据加密
- 操作日志记录
- 数据脱敏展示

### 3. 通信安全
- HTTPS传输加密
- API签名验证
- 防重放攻击

## 监控与运维

### 1. 应用监控
- Spring Boot Actuator
- Prometheus指标收集
- 日志聚合分析

### 2. 业务监控
- 交易成功率
- 系统响应时间
- 异常处理统计

### 3. 运维自动化
- 健康检查
- 自动故障恢复
- 服务降级策略

## 项目工具与脚本

### 1. Maven Wrapper
- **mvnw / mvnw.cmd / mvnw.sh**: 跨平台Maven Wrapper脚本
- **.mvn/wrapper/**: Maven Wrapper配置和下载器
- **特点**: 自动下载Maven 3.9.5，确保构建一致性

### 2. 辅助脚本
- **init-trading-system.sh**: 项目初始化脚本，验证环境并编译项目
- **verify-mvnw.sh**: 验证Maven Wrapper设置
- **convert-to-utf8.sh**: 转换配置文件编码为UTF-8

### 3. 文档
- **MAVEN_WRAPPER.md**: Maven Wrapper详细使用指南
- **README.md**: 项目总体介绍和快速入门指南
- **ARCHITECTURE.md**: 系统架构详细说明