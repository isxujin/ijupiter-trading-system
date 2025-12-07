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
│   │   ├── customer-api           # 客户管理API
│   │   ├── funding-api            # 资金API
│   │   ├── securities-api         # 证券API
│   │   ├── settlement-api         # 清/结算API
│   │   ├── trading-engine-api     # 交易撮合API
│   │   ├── query-api              # 通用查询API
│   │   └── system-api             # 系统管理API
│   └── middleware-spi              # 中间件SPI（服务提供者接口）
│       ├── message-adapter-spi     # 消息适配器SPI
│       └── cache-adapter-spi      # 缓存适配器SPI
├── securities-trading-core            # 核心业务实现层
│   ├── customer-core               # 客户管理核心服务
│   ├── funding-core                # 资金核心服务
│   ├── securities-core             # 证券核心服务
│   ├── settlement-core             # 清/结算核心服务
│   ├── trading-engine-core         # 交易撮合核心服务
│   ├── query-core                  # 通用查询核心服务
│   └── system-core                 # 系统管理核心服务
├── securities-trading-middleware      # 中间件适配器层
│   ├── rabbitmq-adapter            # RabbitMQ消息适配器
│   └── redis-adapter             # Redis缓存适配器
├── securities-trading-web             # Web表示层
│   ├── common-web                 # 公共Web模块，提供视图层框架资源和控制层公共资源
│   ├── customer-web               # 客户管理Web模块
│   ├── funding-web                # 资金Web模块
│   ├── securities-web             # 证券Web模块
│   ├── settlement-web             # 清/结算Web模块
│   ├── trading-engine-web         # 交易撮合Web模块
│   ├── query-web                  # 通用查询Web模块
│   └── system-web                 # 系统管理Web模块
├── securities-trading-boots            # 应用启动层
│   ├── service-boot-allinone     # 服务单体启动器
│   ├── web-boot-investor          # 投资者Web启动器
│   └── web-boot-management       # 管理员Web启动器
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
  - **business-api**: 业务领域API，包括客户管理、资金、证券、清/结算、交易撮合、通用查询、系统管理等API
    - **customer-api**: 客户管理API，提供客户、客户资金账户、客户证券账户等接口
    - **funding-api**: 资金API，提供资金余额、资金变动流水、交易所信息等接口
    - **securities-api**: 证券API，提供证券持仓、持仓变动流水、银行信息等接口
    - **settlement-api**: 清/结算API，提供日清/结算相关功能接口
    - **trading-engine-api**: 交易撮合API，提供日间证券标的交易/撮合相关功能接口
    - **query-api**: 通用查询API，提供所有上述模块的查询能力接口
    - **system-api**: 系统管理API，提供系统操作员、角色、权限、数据字典等接口
  - **middleware-spi**: 中间件SPI（服务提供者接口），包括：
    - **message-adapter-spi**: 消息适配器SPI，定义消息服务的标准接口
    - **cache-adapter-spi**: 缓存适配器SPI，定义缓存服务的标准接口

#### securities-trading-core
- **职责**: 实现核心业务逻辑和事件处理
- **子模块**:
  - **customer-core**: 客户管理核心，处理客户信息、客户资金账户、客户证券账户等
  - **funding-core**: 资金管理核心，处理客户资金余额、资金变动流水、交易所信息、证券产品标的、证券交易日历等
  - **securities-core**: 证券管理核心，处理客户证券持仓、持仓变动流水、银行信息等
  - **settlement-core**: 清/结算核心，处理日清/结算相关功能
  - **trading-engine-core**: 交易撮合核心，处理日间证券标的交易/撮合相关功能
  - **query-core**: 通用查询核心，处理所有模块的查询请求，采用查询与事件分离架构
  - **system-core**: 系统管理核心，采用传统MVC架构，处理系统操作员、角色、权限、数据字典、系统参数等，为管理终端提供标准的CRUD服务

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
  - **funding-web**: 资金Web模块，提供资金管理界面，继承common-web的公共资源
  - **securities-web**: 证券Web模块，提供证券管理界面，继承common-web的公共资源
  - **settlement-web**: 清/结算Web模块，提供清/结算管理界面，继承common-web的公共资源
  - **trading-engine-web**: 交易撮合Web模块，提供交易撮合管理界面，继承common-web的公共资源
  - **query-web**: 通用查询Web模块，提供所有模块的查询界面，继承common-web的公共资源
  - **system-web**: 系统管理Web模块，提供系统设置界面，继承common-web的公共资源

#### securities-trading-boots
- **职责**: 提供不同场景的应用启动入口
- **子模块**:
  - **service-boot-allinone**: 服务单体启动器，包含所有核心服务模块
  - **web-boot-investor**: 投资者Web启动器，面向证券投资者域的客户端
  - **web-boot-management**: 管理员Web启动器，面向管理员域的管理终端
- **特点**: 支持按业务域分离启动，方便不同角色使用

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
# 运行服务单体
./mvnw.sh -pl securities-trading-boots/service-boot-allinone spring-boot:run
```

#### 运行Web应用

```bash
# 运行管理员终端
./mvnw.sh -pl securities-trading-boots/web-boot-management spring-boot:run

# 运行投资者终端
./mvnw.sh -pl securities-trading-boots/web-boot-investor spring-boot:run
```

#### 注意事项

- 首次运行会自动下载Maven 3.9.5，需要网络连接
- 如果遇到网络问题，可以手动下载maven-wrapper.jar到`.mvn/wrapper/`目录
- 项目启动会自动检查Java版本，确保使用Java 17+

#### 访问应用

根据不同的启动器模块，访问地址如下：

1. 管理员终端 (web-boot-management):
   - 访问地址: http://localhost:9000
   - 登录页面: http://localhost:9000/management/login
   - 默认账号: admin / admin@123

2. 投资者终端 (web-boot-investor):
   - 访问地址: http://localhost:9001
   - 登录页面: http://localhost:9001/investor/login

3. 服务单体 (service-boot-allinone):
   - 服务端口: 8080
   - 提供RESTful API接口

## 核心功能

### 面向证券投资者域的功能模块

#### 1. 客户管理模块 (customer)
- 客户信息维护
- 客户资金账户维护
- 客户证券账户维护
- 客户状态管理（正常、冻结、注销）
- 银行卡绑定与管理
- 事件溯源与数据审计

#### 2. 资金模块 (funding)
- 客户资金余额维护
- 客户资金变动流水维护
- 证券交易所信息维护
- 证券产品标的维护
- 证券交易日历维护
- 资金划拨操作
- 资金冻结与解冻

#### 3. 证券模块 (securities)
- 客户证券持仓维护
- 客户持仓变动流水维护
- 银行信息维护
- 交易所账号绑定与管理
- 持仓信息查询与分析

#### 4. 清/结算模块 (settlement)
- 日清/结算相关功能
- T+1结算机制
- 资金交收处理
- 结算风险管理
- 结算对账功能

#### 5. 交易撮合模块 (trading-engine)
- 日间证券标的交易/撮合相关功能
- 订单委托管理
- 撮合引擎
- 成交确认机制
- 交易风控检查

#### 6. 通用查询模块 (query)
- 系统采用查询与事件分离架构
- 承接所有上述模块为客户端提供的查询能力
- 账户信息查询
- 交易记录查询
- 资金流水查询
- 持仓信息查询

### 面向管理员域的功能模块

#### 1. 系统管理模块 (system)
- 系统操作员维护
- 系统角色维护
- 系统权限维护
- 系统数据字典维护
- 系统参数维护
- 系统配置管理
- 系统监控与日志管理

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