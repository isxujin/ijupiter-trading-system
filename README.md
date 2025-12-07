# iJupiter 证券交易系统

[![Java Version](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=flat&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Axon Framework](https://img.shields.io/badge/Axon-4.12.2-blue?style=flat&logo=axon-framework)](https://axoniq.io/)
[![Maven Wrapper](https://img.shields.io/badge/Maven%20Wrapper-3.9.5-red?style=flat&logo=apache-maven)](https://maven.apache.org/)

## 项目概述

iJupiter证券交易系统是一个基于事件引擎架构的证券交易平台，支持证券、基金、期货等多种金融产品的交易业务。系统采用现代化的微服务架构，遵循DDD（领域驱动设计）和CQRS（命令查询责任分离）模式，使用Spring Boot 3.2.5和Axon Framework 4.12.2构建，具备高可扩展性、高性能和高可用性特点。

系统以事件驱动为核心，通过领域事件解耦各业务模块，实现异步处理和数据最终一致性。项目已配置Maven Wrapper，确保所有开发环境使用一致的Maven版本。

## 技术栈

| 技术 | 版本            | 用途 |
|------|---------------|------|
| Java | 17            | 编程语言 |
| Spring Boot | 3.2.5         | 应用框架 |
| Axon Framework | 4.12.2        | 事件驱动架构框架 |
| Maven Wrapper | 3.9.5          | 构建工具管理 |
| MySQL | 8.4.0         | 主数据存储 |
| Redis | 7             | 缓存与会话存储 |
| RabbitMQ | 5.20.0        | 消息队列 |
| MongoDB | 6.0           | 文档数据库 |
| Spring Security | 6.5.7         | 安全框架 |
| Thymeleaf | 3.2.6.RELEASE | 服务端模板引擎 |
| Jakarta Persistence | 3.1.0         | JPA API |
| Lombok | 1.18.42       | 代码简化工具 |
| Hutool | 5.8.42        | Java工具库 |
| WebJars | 多种版本        | 前端资源管理 |

## 项目结构

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

### 模块说明

#### securities-trading-boms
- **职责**: 统一管理所有模块的依赖版本
- **核心组件**: Maven BOM文件
- **特点**: 版本一致性、依赖传递控制

#### securities-trading-common
- **职责**: 提供公共工具类、常量和基础组件
- **核心组件**: 
  - 通用工具类
  - 常量定义
  - 基础异常类
  - 通用枚举
- **特点**: 可复用、轻量级

#### securities-trading-api
- **职责**: 定义系统各模块间的接口契约
- **子模块**:
  - **business-api**: 业务领域API，包括账户、客户管理、资金、产品、系统管理、交易、结算、查询等API
    - **customer-api**: 客户管理API，提供客户、交易账户、资金账户等接口
  - **middleware-spi**: 中间件SPI（服务提供者接口），包括：
    - **message-adapter-spi**: 消息适配器SPI，定义消息服务的标准接口
    - **cache-adapter-spi**: 缓存适配器SPI，定义缓存服务的标准接口

#### securities-trading-core
- **职责**: 实现核心业务逻辑和事件处理
- **子模块**:
  - **securities-core**: 账户管理核心，处理用户账户、权限等（原 account-core）
  - **customer-core**: 客户管理核心，处理客户信息、交易账户、资金账户等
    - 客户账户拆分设计：交易账户拆分为基本信息和持仓，资金账户拆分为基本信息和余额
    - 银行卡信息已合并到资金账户，交易所账号信息已合并到交易账户
  - **fund-core**: 资金管理核心，处理资金划拨、冻结、解冻等
  - **product-core**: 产品管理核心，处理金融产品定义、规则等
  - **system-core**: 系统管理核心，处理操作员、角色、权限、数据字典等
  - **trading-engine-core**: 交易引擎核心，处理订单撮合、成交等
  - **settlement-core**: 结算核心，处理资金结算、交收等
  - **query-core**: 查询核心，处理各种查询请求

#### securities-trading-middleware
- **职责**: 提供中间件技术适配器
- **子模块**:
  - **rabbitmq-adapter**: RabbitMQ消息适配器，处理异步消息
  - **redis-adapter**: Redis缓存适配器，处理缓存和会话

#### securities-trading-web
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

#### securities-trading-boots
- **职责**: 提供不同场景的应用启动入口
- **子模块**:
  - **service-allinone-boot**: 服务单体启动器，包含所有核心服务模块
  - **service-test-boot**: 服务测试启动器，用于测试环境
  - **web-allinone-boot**: Web单体启动器，包含所有Web界面模块
  - **web-test-boot**: Web测试启动器，用于Web测试环境
- **特点**: 支持单体启动和测试分离，方便不同场景部署

## 编码与构建

- **编码**: UTF-8（全局统一）
- **构建工具**: Maven Wrapper 3.9.5 (无需本地安装Maven)
- **包管理**: Maven 多模块项目
- **Java版本**: 17
- **编译器插件**: Maven Compiler Plugin 3.11.0
- **编码转换**: 提供convert-to-utf8.sh脚本处理编码问题

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 7+
- RabbitMQ 3.12+
- Node.js 16+（可选，用于前端开发）
- Maven无需本地安装（项目使用Maven Wrapper）

### 快速启动

1. **初始化项目**（首次使用）
```bash
# 克隆项目
git clone https://github.com/your-org/ijupiter-trading-system.git
cd ijupiter-trading-system

# 运行初始化脚本（验证环境、下载依赖、编译项目）
./init-trading-system.sh
```

2. **构建项目**
```bash
# 使用Maven Wrapper编译整个项目
./mvnw.sh clean compile

# 打包整个项目
./mvnw.sh clean package

# 安装到本地仓库
./mvnw.sh clean install
```

3. **验证Maven Wrapper**
```bash
# 验证Maven Wrapper设置是否正确
./verify-mvnw.sh
```

### Maven Wrapper 依赖管理

项目使用Maven Wrapper确保构建一致性，无需本地安装Maven。相关文件：

- `mvnw` / `mvnw.cmd` - 跨平台Maven Wrapper脚本
- `mvnw.sh` - 增强的Unix脚本，包含Java版本检查
- `.mvn/wrapper/` - Maven Wrapper配置文件
- `MAVEN_WRAPPER.md` - 详细使用指南

Maven Wrapper会自动下载Maven 3.9.5版本到用户目录，确保所有开发环境使用相同的Maven版本。

### 运行服务

#### 运行后台服务

```bash
# 运行完整服务单体
./mvnw.sh -pl securities-trading-boots/service-allinone-boot spring-boot:run

# 运行服务测试模式
./mvnw.sh -pl securities-trading-boots/service-test-boot spring-boot:run
```

#### 运行Web应用

```bash
# 运行完整Web单体
./mvnw.sh -pl securities-trading-boots/web-allinone-boot spring-boot:run

# 运行Web测试模式
./mvnw.sh -pl securities-trading-boots/web-test-boot spring-boot:run
```

#### 注意事项

- 首次运行会自动下载Maven 3.9.5，需要网络连接
- 如果遇到网络问题，可以手动下载maven-wrapper.jar到`.mvn/wrapper/`目录
- 项目启动会自动检查Java版本，确保使用Java 17+

#### 访问应用

- 管理端: http://localhost:8080/admin
- 投资者端: http://localhost:8080/investor
- 客户管理: http://localhost:8080/customer
- 系统管理: http://localhost:8080/system

## 核心功能

### 1. 客户管理
- 客户注册与认证
- 客户信息管理
- 交易账户管理（基本信息、持仓信息）
- 资金账户管理（基本信息、余额信息）
- 银行卡绑定与管理（合并到资金账户）
- 交易所账号绑定与管理（合并到交易账户）
- 客户状态管理（正常、冻结、注销）
- 事件溯源与数据审计

### 2. 账户管理
- 用户注册与认证
- 账户信息管理
- 权限角色管理
- 密码安全策略

### 2. 资金管理
- 资金账户体系
- 资金划拨操作
- 资金冻结与解冻
- 资金流水记录

### 3. 产品管理
- 金融产品定义
- 交易规则配置
- 产品状态管理
- 产品风险控制

### 4. 交易功能
- 订单委托管理
- 撮合引擎
- 成交确认机制
- 交易风控检查

### 5. 结算功能
- T+1结算机制
- 资金交收处理
- 结算风险管理
- 结算对账功能

### 6. 查询功能
- 账户信息查询
- 交易记录查询
- 资金流水查询
- 持仓信息查询

### 7. 系统管理
- 操作员管理
- 角色权限管理
- 数据字典管理
- 系统参数配置

## 架构特点

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

## 性能指标

| 指标 | 目标值 | 说明 |
|------|--------|------|
| 交易响应时间 | < 100ms | 95%的请求响应时间 |
| 并发处理能力 | > 1000 TPS | 每秒交易处理数 |
| 系统可用性 | > 99.9% | 全年系统可用时间 |
| 数据一致性 | 最终一致性 | 事件驱动保证 |

## 安全机制

### 1. 认证授权
- Spring Security框架
- JWT令牌机制
- 角色权限控制
- OAuth2.0支持

### 2. 数据安全
- 敏感数据加密
- 操作日志记录
- 数据脱敏展示

### 3. 通信安全
- HTTPS传输加密
- API签名验证
- 防重放攻击

## 部署方案

### 开发环境
- 单机部署
- 内存数据库
- 模拟中间件

### 测试环境
- 小集群部署
- 独立数据库
- 容器化中间件

### 生产环境
- 大集群部署
- 读写分离数据库
- 高可用中间件

## 监控运维

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

## API文档

详细的API文档请参考：[API文档](./docs/api.md)

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交变更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 许可证

本项目使用 [MIT License](LICENSE) 许可证。

## 联系方式

- 项目维护者: [your-email@example.com]
- 问题反馈: [Issues](https://github.com/your-org/ijupiter-trading-system/issues)
- 技术讨论: [Discussions](https://github.com/your-org/ijupiter-trading-system/discussions)

## 项目状态

### 已实现功能

- ✅ Maven Wrapper配置与验证脚本
- ✅ 账户管理核心模块（聚合、实体、服务）
- ✅ 客户管理核心模块（客户、交易账户、资金账户管理，银行卡和交易所账号信息合并）
- ✅ 客户管理Web模块（客户信息、交易账户、资金账户等界面）
- ✅ 交易引擎核心模块（订单匹配、交易执行）
- ✅ 资金管理核心模块（资金账户、交易记录）
- ✅ 系统管理核心模块（操作员、角色、权限、数据字典等）
- ✅ 基础API接口定义（新增客户管理API）
- ✅ 消息中间件适配器（RabbitMQ）
- ✅ 缓存中间件适配器（Redis）
- ✅ Web服务启动器配置（集成客户模块）
- ✅ 编码转换脚本
- ✅ common-web公共模块（Spring MVC、Thymeleaf、WebJars集成，包结构调整）
- ✅ management-web管理端模块（重构完成，统一使用公共资源）
- ✅ investor-web投资者端模块（重构为使用common-web）
- ✅ system-web系统管理模块（新增，使用common-web）

### 待完善功能

- ⏳ 持仓管理模块
- ⏳ 结算核心模块完整实现
- ⏳ 风控规则引擎
- ⏳ 完整的单元测试覆盖
- ⏳ API文档生成
- ⏳ 监控与日志系统

## 其他资源

- [架构文档](./ARCHITECTURE.md)
- [Maven Wrapper使用指南](./MAVEN_WRAPPER.md)
- [部署指南](./docs/deployment.md)
- [开发指南](./docs/development.md)

## 项目脚本说明

| 脚本名称 | 功能 | 使用方式 |
|---------|------|---------|
| init-trading-system.sh | 初始化项目，验证环境，编译项目 | `./init-trading-system.sh` |
| verify-mvnw.sh | 验证Maven Wrapper设置 | `./verify-mvnw.sh` |
| convert-to-utf8.sh | 转换配置文件编码为UTF-8 | `./convert-to-utf8.sh` |