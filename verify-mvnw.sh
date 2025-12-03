#!/bin/bash

# 验证Maven Wrapper是否正常工作的脚本

echo "=========================================="
echo "验证Maven Wrapper设置"
echo "=========================================="

# 检查mvnw文件是否存在
if [ ! -f "mvnw" ]; then
    echo "❌ 错误: mvnw文件不存在"
    exit 1
else
    echo "✅ mvnw文件存在"
fi

# 检查mvnw.sh文件是否存在
if [ ! -f "mvnw.sh" ]; then
    echo "❌ 错误: mvnw.sh文件不存在"
    exit 1
else
    echo "✅ mvnw.sh文件存在"
fi

# 检查.mvn目录是否存在
if [ ! -d ".mvn" ]; then
    echo "❌ 错误: .mvn目录不存在"
    exit 1
else
    echo "✅ .mvn目录存在"
fi

# 检查Maven Wrapper配置文件是否存在
if [ ! -f ".mvn/wrapper/maven-wrapper.properties" ]; then
    echo "❌ 错误: maven-wrapper.properties文件不存在"
    exit 1
else
    echo "✅ maven-wrapper.properties文件存在"
fi

# 检查Maven Wrapper下载器是否存在
if [ ! -f ".mvn/wrapper/MavenWrapperDownloader.java" ]; then
    echo "❌ 错误: MavenWrapperDownloader.java文件不存在"
    exit 1
else
    echo "✅ MavenWrapperDownloader.java文件存在"
fi

# 检查是否有执行权限
if [ ! -x "mvnw" ]; then
    echo "🔧 添加mvnw执行权限"
    chmod +x mvnw
fi

if [ ! -x "mvnw.sh" ]; then
    echo "🔧 添加mvnw.sh执行权限"
    chmod +x mvnw.sh
fi

echo ""
echo "=========================================="
echo "验证Maven Wrapper功能"
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

# 测试Maven Wrapper是否工作
echo ""
echo "测试Maven Wrapper..."
./mvnw.sh --version

if [ $? -eq 0 ]; then
    echo "✅ Maven Wrapper工作正常"
else
    echo "❌ 错误: Maven Wrapper无法正常工作"
    exit 1
fi

echo ""
echo "=========================================="
echo "✅ 验证完成，Maven Wrapper设置正确"
echo "=========================================="
echo ""
echo "使用方式:"
echo "  Linux/Mac: ./mvnw.sh [命令]"
echo "  Windows: ./mvnw.cmd [命令]"
echo ""
echo "示例:"
echo "  ./mvnw.sh clean compile"
echo "  ./mvnw.sh spring-boot:run"
echo ""