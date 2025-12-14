# 公共模板使用说明

## 概述
此目录包含了项目中所有模块可以共享的公共模板资源。

## 模板文件

### 1. layout.html
通用的页面布局模板，适用于管理后台页面。

#### 使用方法
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" 
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
      th:replace="~{common/layout :: layout(
      title = '页面标题',
      css = ~{::css},
      content = ~{::content},
      js = ~{::js}
    )}">

<th:block th:fragment="css">
    <!-- 页面特定CSS -->
</th:block>

<th:block th:fragment="content">
    <!-- 页面内容 -->
</th:block>

<th:block th:fragment="js">
    <!-- 页面特定JavaScript -->
</th:block>
</html>
```

#### 参数说明
- `title`: 页面标题，会显示在浏览器标签页和页面头部
- `css`: 页面特定的CSS样式
- `content`: 页面的主要内容
- `js`: 页面特定的JavaScript代码

#### 可选变量
- `appName`: 应用名称，默认为"iJupiter Trading System"
- `sidebarItems`: 侧边栏菜单项，如果未提供，将使用默认菜单
  - 格式: `[{name: '菜单名称', url: '/menu/url', icon: 'bi bi-icon', active: true/false}, ...]`
- `activeMenu`: 当前激活的菜单项标识

### 2. base.html
基础页面布局模板，适用于一般页面。

#### 使用方法
```html
<head th:replace="~{common/base :: head}">
    <title>页面标题 - iJupiter Trading System</title>
    <th:block th:fragment="css">
        <!-- 页面特定CSS -->
    </th:block>
</head>
<body>
    <!-- 页面内容 -->
</body>
```

## 依赖说明
使用公共模板需要确保项目中已引入以下依赖：
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Security (如需安全功能)
- WebJars (Bootstrap, jQuery等)

## 注意事项
1. 使用公共模板时，请确保模板路径正确
2. 公共模板中的所有资源引用都使用了WebJars，请确保相关依赖已添加
3. 如有特殊需求，可以通过变量参数自定义模板行为