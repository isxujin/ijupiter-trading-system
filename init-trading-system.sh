#!/bin/bash

# 初始化金融交易系统的脚本

echo "=========================================="
echo "初始化iJupiter金融交易系统"
echo "=========================================="

# 检查Java版本
echo "检查Java版本..."
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "当前Java版本: $JAVA_VERSION"

# 检查Java 17+
REQUIRED_VERSION="17"
if [ "$(printf '%s\n' "$REQUIRED_VERSION" "${JAVA_VERSION%%.*}" | sort -V | head -n1)" = "$REQUIRED_VERSION" ]; then
    echo "✅ Java版本满足要求 (>= Java 17)"
else
    echo "❌ 错误: 需要Java 17或更高版本，当前版本: $JAVA_VERSION"
    echo "请设置JAVA_HOME到Java 17安装路径"
    exit 1
fi

# 确保Maven Wrapper有执行权限
echo "设置Maven Wrapper执行权限..."
chmod +x mvnw mvnw.sh

# 验证Maven Wrapper
echo "验证Maven Wrapper..."
./verify-mvnw.sh

if [ $? -ne 0 ]; then
    echo "❌ 错误: Maven Wrapper验证失败"
    exit 1
fi

echo ""
echo "=========================================="
echo "编译项目"
echo "=========================================="

# 清理并编译项目
echo "执行 clean compile..."
./mvnw.sh clean compile

if [ $? -ne 0 ]; then
    echo "❌ 错误: 项目编译失败"
    exit 1
fi

echo ""
echo "=========================================="
echo "✅ 项目初始化完成"
echo "=========================================="
echo ""
echo "运行服务:"
echo "  服务端: ./mvnw.sh -pl financial-trading-boots/service-boot spring-boot:run"
echo "  Web端:  ./mvnw.sh -pl financial-trading-boots/web-boot spring-boot:run"
echo ""
echo "构建项目:"
echo "  打包:   ./mvnw.sh clean package"
echo "  安装:   ./mvnw.sh clean install"
echo ""