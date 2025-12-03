# Maven Wrapper 使用指南

## 概述

本项目已配置Maven Wrapper，确保所有开发者使用相同版本的Maven，提高构建的一致性和可重现性。

## Maven Wrapper 优势

1. **版本一致性**：所有开发者使用相同的Maven版本
2. **无需预安装**：新环境无需预先安装Maven
3. **项目隔离**：不同项目可以使用不同的Maven版本
4. **跨平台**：支持Windows、Linux和macOS

## 文件结构

```
project-root/
├── mvnw                    # Unix/Linux/Mac可执行脚本
├── mvnw.cmd                # Windows可执行脚本
├── mvnw.sh                 # Unix/Linux/Mac shell脚本
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.properties   # Maven Wrapper配置
│       └── MavenWrapperDownloader.java # 自动下载器
└── verify-mvnw.sh         # 验证脚本
```

## 配置详情

### Maven版本
- 当前配置：Maven 3.9.5
- 配置文件：`.mvn/wrapper/maven-wrapper.properties`

### Java版本要求
- 最低要求：Java 17
- 自动检查：mvnw.sh脚本会检查Java版本

## 使用方式

### 基本命令

**Unix/Linux/Mac:**
```bash
./mvnw.sh [命令]
```

**Windows:**
```cmd
./mvnw.cmd [命令]
```

### 常用操作

```bash
# 编译项目
./mvnw.sh compile

# 清理并编译
./mvnw.sh clean compile

# 运行测试
./mvnw.sh test

# 打包项目
./mvnw.sh package

# 安装到本地仓库
./mvnw.sh install

# 运行服务端
./mvnw.sh -pl financial-trading-boots/service-boot spring-boot:run

# 运行Web端
./mvnw.sh -pl financial-trading-boots/web-boot spring-boot:run
```

## 验证和初始化

### 验证Maven Wrapper设置
```bash
./verify-mvnw.sh
```

### 初始化项目
```bash
./init-trading-system.sh
```

## 环境变量

可以使用以下环境变量自定义行为：

- `MAVEN_OPTS`：设置JVM选项
- `MAVEN_SKIP_RC`：跳过加载mavenrc文件
- `MVNW_USERNAME`：Maven仓库用户名
- `MVNW_PASSWORD`：Maven仓库密码
- `MVNW_VERBOSE`：启用详细输出
- `MVNW_REPOURL`：自定义Maven仓库URL

## 常见问题

### 1. Java版本不兼容
**问题**：错误"Java 17 or higher is required"
**解决**：设置JAVA_HOME到Java 17安装路径

### 2. 权限问题
**问题**：错误"Permission denied"
**解决**：
```bash
chmod +x mvnw.sh
chmod +x mvnw
```

### 3. 网络连接问题
**问题**：无法下载Maven Wrapper
**解决**：
1. 检查网络连接
2. 使用代理或离线模式
3. 手动下载maven-wrapper.jar到`.mvn/wrapper/`目录

### 4. 缓存问题
**问题**：构建结果不一致
**解决**：清理Maven缓存
```bash
./mvnw.sh dependency:purge-local-repository
```

## 最佳实践

1. **始终使用Maven Wrapper**而不是系统安装的Maven
2. **将mvnw和.mvn目录**添加到版本控制系统
3. **不要**将Maven Wrapper下载的Maven分布添加到版本控制系统
4. **定期更新**Maven Wrapper版本
5. **在CI/CD中使用**Maven Wrapper确保构建一致性

## 自定义配置

如需使用不同版本的Maven，修改`.mvn/wrapper/maven-wrapper.properties`：

```properties
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip
```

## IDE集成

### IntelliJ IDEA
1. 打开File → Settings → Build Tools → Maven
2. 勾选"Use Maven wrapper"
3. 指定"Maven executable"为项目根目录的mvnw

### Eclipse
1. 右键项目 → Properties → Maven
2. 勾选"Resolve Workspace Projects"
3. 设置"Maven Installation"为"External"，指向mvnw

## 故障排除

如遇到问题，请按以下步骤排查：

1. 运行`./verify-mvnw.sh`验证基本设置
2. 检查Java版本是否符合要求
3. 检查网络连接和防火墙设置
4. 查看详细日志：`./mvnw.sh -X [命令]`
5. 清理并重试：
   ```bash
   rm -rf ~/.m2/wrapper
   ./mvnw.sh clean
   ```