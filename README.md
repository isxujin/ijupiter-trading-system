# iJupiter 证券交易系统

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-green.svg)]
[![Axon Framework](https://img.shields.io/badge/Axon%20Framework-4.12.2-blue.svg)]

## 项目简介

iJupiter 证券交易系统是一个基于事件引擎架构的现代化证券交易平台，采用**DDD（领域驱动设计）**和**CQRS（命令查询责任分离）**模式，使用**Spring Boot 3.5.8**和**Axon Framework 4.12.2**构建。系统支持证券、基金、期货等多种金融产品的交易业务。

## 系统架构

### 整体架构

```
┌─────────────────────────────────────────────────────┐
│                securities-trading-boots             │  ← 应用启动层
│  ┌─────────────────┐  ┌─────────────────┐         │
│  │  web-boot-xxx   │  │ service-boot-xxx │         │
│  └─────────────────┘  └─────────────────┘         │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│                securities-trading-web               │  ← Web表示层
│  ┌─────────────────┐  ┌─────────────────┐         │
│  │   common-web    │  │  domain-web     │         │
│  └─────────────────┘  └─────────────────┘         │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│                securities-trading-core              │  ← 核心业务层
│  ┌─────────────────┐  ┌─────────────────┐         │
│  │  domain-core    │  │  domain-core    │         │
│  └─────────────────┘  └─────────────────┘         │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│                 securities-trading-api              │  ← 接口定义层
│  ┌─────────────────┐  ┌─────────────────┐         │
│  │  business-api   │  │ middleware-spi  │         │
│  └─────────────────┘  └─────────────────┘         │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│            securities-trading-middleware            │  ← 中间件层
│  ┌─────────────────┐  ┌─────────────────┐         │
│  │ rabbitmq-adapter│  │  redis-adapter  │         │
│  └─────────────────┘  └─────────────────┘         │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│              securities-trading-common               │  ← 公共基础层
│         (Entities, DTOs, Utils, Constants)          │
└─────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────┐
│              securities-trading-boms                │  ← 依赖管理层
│                (Maven BOM统一版本)                   │
└─────────────────────────────────────────────────────┘
```

### 技术架构特点

- **事件驱动架构**：使用Axon Framework实现事件溯源和CQRS，支持分布式事件处理
- **微服务架构**：按业务域垂直拆分，服务独立部署和扩展
- **领域驱动设计(DDD)**：按业务领域划分模块，明确的领域边界
- **多层架构设计**：清晰的分层结构，职责分明
- **SPI模式**：支持中间件的灵活替换

## 模块详述

### 1. securities-trading-boms（依赖版本管理）

**职责**：统一管理所有模块的依赖版本，确保版本兼容性

**核心技术栈**：
- Spring Boot: 3.5.8
- Axon Framework: 4.12.2
- MySQL Connector: 9.5.0
- Lombok: 1.18.42
- Hutool: 5.8.42
- WebJars: Bootstrap 5.3.8, jQuery 3.7.1

### 2. securities-trading-common（公共基础组件）

**职责**：提供公共工具类、常量和基础组件

**核心组件**：
- **BaseEntity**：实体基类，提供统一的审计字段
- **BaseDTO**：DTO基类，提供对象转换功能
- **BaseService**：服务基类接口
- **异常体系**：完整的异常处理体系
- **工具类**：日期、字符串、加密等工具
- **枚举类**：系统状态、删除状态等

### 3. securities-trading-api（接口定义层）

#### 3.1 business-api（业务API）

**职责**：定义各业务域的接口契约

**已实现模块**：
- **system-api**：系统管理API
  - UserDTO、RoleDTO、PermissionDTO等
  - UserService、RoleService、PermissionService等接口

**待实现模块**：
- **customer-api**：客户管理API
- **funding-api**：资金管理API
- **securities-api**：证券管理API
- **settlement-api**：清算API
- **trading-engine-api**：交易引擎API
- **query-api**：通用查询API

#### 3.2 middleware-spi（中间件SPI）

**职责**：定义中间件实现的标准接口

- **cache-adapter-spi**：缓存适配器SPI
  - CacheAdapterService：完整的缓存服务接口
- **message-adapter-spi**：消息适配器SPI
  - MessageAdapterService：消息服务接口

### 4. securities-trading-core（核心业务实现层）

#### 4.1 system-core（系统管理核心）- **已实现**

**职责**：系统管理功能，采用传统MVC架构

**实体层**：User、Role、Permission、Dictionary、Parameter等
**仓储层**：提供完整的JPA Repository接口
**服务层**：完整的业务逻辑实现

#### 4.2 其他核心模块 - **架构已设计，待实现**

- **customer-core**：客户管理核心
- **funding-core**：资金管理核心
- **securities-core**：证券管理核心
- **settlement-core**：清算核心
- **trading-engine-core**：交易引擎核心
- **query-core**：通用查询核心

### 5. securities-trading-middleware（中间件适配器层）

#### 5.1 redis-adapter - **已实现**

**职责**：Redis缓存适配器
- **RedisCacheAdapter**：完整实现CacheAdapterService SPI
- 支持基本的CRUD操作、批量操作、超时设置等

#### 5.2 rabbitmq-adapter - **已实现**

**职责**：RabbitMQ消息适配器
- **RabbitMQMessageAdapter**：消息适配器实现
- 支持消息路由和分发

### 6. securities-trading-web（Web表示层）

#### 6.1 common-web - **已实现**

**职责**：公共Web模块，提供视图层框架资源

**核心功能**：
- Spring MVC和Thymeleaf配置
- WebJars资源管理
- BaseController：统一模型属性
- 统一API响应格式
- 错误处理机制

#### 6.2 domain-web模块

**已实现**：
- **system-web**：系统管理Web模块
  - 用户、角色、权限、字典、参数管理控制器

**待实现**：
- **customer-web**、**funding-web**、**securities-web**等模块

### 7. securities-trading-boots（应用启动层）

#### 7.1 service-boot-allinone - **已实现**

**职责**：服务单体启动器
- Axon事件引擎配置
- 消息和查询模块配置

#### 7.2 web-boot-management - **已实现**

**职责**：管理员Web启动器
- Spring Security安全配置
- 自定义认证机制

#### 7.3 web-boot-investment - **已实现**

**职责**：投资者Web启动器
- 投资者专属功能和界面

## 技术栈详情

### 后端技术
- **Java 17**：主要编程语言
- **Spring Boot 3.5.8**：应用框架
- **Spring Security 6.5.7**：安全框架
- **Axon Framework 4.12.2**：事件驱动框架
- **Spring Data JPA**：数据访问层
- **Hibernate**：ORM框架
- **MySQL 9.5.0**：主数据库

### 中间件技术
- **Redis 7**：缓存和会话存储
- **RabbitMQ 5.20.0**：消息队列
- **HikariCP**：数据库连接池

### 前端技术
- **Thymeleaf**：模板引擎
- **Bootstrap 5.3.8**：UI框架
- **jQuery 3.7.1**：JavaScript库
- **Chart.js**：图表库

### 开发工具
- **Maven**：构建工具
- **Lombok**：代码简化
- **Hutool**：工具库
- **WebJars**：前端资源管理

## 环境要求

- **JDK**：17或更高版本
- **Maven**：3.8或更高版本
- **MySQL**：8.0或更高版本
- **Redis**：7.0或更高版本
- **RabbitMQ**：3.9或更高版本

## 快速开始

### 1. 克隆项目
```bash
git clone https://github.com/your-org/ijupiter-trading-system.git
cd ijupiter-trading-system
```

### 2. 构建项目
```bash
# 使用Maven Wrapper
./mvnw clean install

# 或使用系统Maven
mvn clean install
```

### 3. 启动服务单体应用
```bash
cd securities-trading-boots/service-boot-allinone
./mvnw spring-boot:run
```

### 4. 启动Web管理应用
```bash
cd securities-trading-boots/web-boot-management
./mvnw spring-boot:run
```

### 5. 启动Web投资者应用
```bash
cd securities-trading-boots/web-boot-investment
./mvnw spring-boot:run
```

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ijupiter_trading?useUnicode=true&characterEncoding=utf8
    username: your-username
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
```

### Redis配置
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: your-redis-password
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-idle: 8
```

### RabbitMQ配置
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: your-rabbitmq-username
    password: your-rabbitmq-password
    virtual-host: /
```

## 项目结构

```
ijupiter-trading-system/
├── securities-trading-boms/          # Maven BOM - 依赖版本管理
├── securities-trading-common/         # 公共基础组件
├── securities-trading-api/            # 接口定义层
│   ├── business-api/                # 业务API接口
│   │   ├── system-api/             # 系统管理API
│   │   └── [其他业务api]/
│   └── middleware-spi/             # 中间件SPI
├── securities-trading-core/            # 核心业务实现层
│   ├── system-core/                # 系统管理核心
│   └── [其他业务core]/
├── securities-trading-middleware/       # 中间件适配器层
│   ├── redis-adapter/             # Redis缓存适配器
│   └── rabbitmq-adapter/          # RabbitMQ消息适配器
├── securities-trading-web/            # Web表示层
│   ├── common-web/               # 公共Web模块
│   └── domain-web/               # 业务Web模块
│       ├── system-web/             # 系统管理Web
│       └── [其他业务web]/
├── securities-trading-boots/          # 应用启动层
│   ├── service-boot-allinone/       # 服务单体启动器
│   ├── web-boot-management/         # 管理端启动器
│   └── web-boot-investment/         # 投资端启动器
└── pom.xml                          # 主POM文件
```

## API文档

### 系统管理模块API

- **用户管理**：
  - GET `/system/user/list` - 用户列表页面
  - GET `/system/user/data` - 获取用户数据
  - POST `/system/user/save` - 保存用户
  - PUT `/system/user/update/{id}` - 更新用户
  - DELETE `/system/user/delete/{id}` - 删除用户
  - PUT `/system/user/status/{id}` - 更新用户状态

- **角色管理**：
  - GET `/system/role/list` - 角色列表页面
  - GET `/system/role/data` - 获取角色数据
  - POST `/system/role/save` - 保存角色
  - PUT `/system/role/update/{id}` - 更新角色
  - DELETE `/system/role/delete/{id}` - 删除角色

- **权限管理**：
  - GET `/system/permission/list` - 权限列表页面
  - GET `/system/permission/data` - 获取权限数据
  - POST `/system/permission/save` - 保存权限
  - PUT `/system/permission/update/{id}` - 更新权限
  - DELETE `/system/permission/delete/{id}` - 删除权限

## 实现状态

### ✅ 已完成功能

1. **完整的系统管理模块**（用户、角色、权限、字典、参数管理）
2. **中间件适配器**（Redis缓存、RabbitMQ消息）
3. **Web公共基础框架**
4. **应用启动器**（服务单体、管理端、投资者端）
5. **Maven Wrapper集成**和依赖版本管理
6. **统一异常处理**和API响应格式

### ⏳ 待实现功能

1. **客户管理核心模块**及Web界面
2. **资金管理核心模块**及Web界面
3. **证券管理核心模块**及Web界面
4. **清算核心模块**及Web界面
5. **交易引擎核心模块**及Web界面
6. **通用查询核心模块**及Web界面

## 部署说明

### Docker部署

1. **构建镜像**
```bash
mvn clean install
docker build -t ijupiter-trading:latest .
```

2. **Docker Compose部署**
```bash
docker-compose up -d
```

3. **Kubernetes部署**
```bash
kubectl apply -f k8s/
```

## 开发指南

### 代码规范
- 遵循《阿里巴巴Java开发手册》规范
- 使用Lombok简化样板代码
- 统一的异常处理机制
- 规范化的API响应格式

### 测试
- 单元测试：使用JUnit 5 + Mockito
- 集成测试：使用Spring Test + TestContainers
- 测试覆盖率要求：不低于70%

### 提交规范
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试相关

## 贡献指南

1. Fork本仓库
2. 创建特性分支：`git checkout -b feature/amazing-feature`
3. 提交更改：`git commit -m 'Add amazing feature'`
4. 推送分支：`git push origin feature/amazing-feature`
5. 提交Pull Request

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

## 联系方式

- 项目维护者：[iJupiter团队]
- 邮箱：support@ijupiter-trading.com
- 官方网站：https://www.ijupiter-trading.com

## 更新日志

### v1.0.1-SNAPSHOT (当前版本)
- 完成系统管理模块基础功能
- 实现Redis缓存和RabbitMQ消息中间件
- 建立Web公共框架
- 完成服务启动器和管理端、投顾端应用

---

**注意**：本系统目前处于开发阶段，部分业务模块尚未实现。请参考实现状态了解当前进度。