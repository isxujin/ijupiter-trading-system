# Common Terminal 模块使用指南

## 概述

`common-terminal` 是 `financial-trading-web` 子模块的公共基础模块，提供了所有Web终端模块所需的公共资源和功能支持。

## 功能特性

### 1. Spring MVC 集成
- 提供基础的Web框架支持
- 统一的API响应格式 (`ApiResponse`)
- 公共控制器基类 (`BaseController`)

### 2. Thymeleaf 视层支持
- 统一的页面模板结构
- WebJars资源集成
- Spring Security安全标签支持

### 3. 前端资源集成
- Bootstrap 5.2.3
- jQuery 3.6.4
- Chart.js 图表库

## 模块结构

```
common-terminal/
├── src/main/java/net/ijupiter/trading/web/common/
│   ├── controller/
│   │   ├── BaseController.java       # 基础控制器类
│   │   └── HomeController.java       # 示例首页控制器
│   ├── config/
│   │   └── ThymeleafConfig.java      # Thymeleaf配置
│   └── dto/
│       └── ApiResponse.java         # 统一API响应格式
└── src/main/resources/templates/common/
    ├── base.html                     # 基础模板
    ├── home.html                     # 首页模板
    └── error.html                    # 错误页模板
```

## 使用方法

### 1. 依赖引用

在其他终端模块的 `pom.xml` 中添加 `common-terminal` 依赖：

```xml
<dependency>
    <groupId>net.ijupiter.trading</groupId>
    <artifactId>common-terminal</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 继承基础控制器

```java
@Controller
public class MyController extends BaseController {
    
    @GetMapping("/mypage")
    public String myPage(Model model) {
        // 可以直接使用父类提供的全局模型属性
        // 例如：${appVersion} 和 ${webjarsVersion}
        return "my-template";
    }
    
    @GetMapping("/api/data")
    @ResponseBody
    public ApiResponse<MyData> getData() {
        MyData data = new MyData();
        return ApiResponse.success("获取成功", data);
    }
}
```

### 3. 使用模板继承

在您的模板中引用基础模板：

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="~{common/base :: head}">
    <!-- 可以添加额外的CSS -->
    <th:block th:fragment="css">
        <link rel="stylesheet" th:href="@{/css/mystyle.css}">
    </th:block>
</head>
<body>
    <!-- 使用基础布局 -->
    <div th:replace="~{common/base :: navbar}"></div>
    <div th:replace="~{common/base :: sidebar}"></div>
    
    <!-- 主内容区 -->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">
        <h1>我的页面</h1>
        <p>这里是我的页面内容。</p>
    </main>
    
    <!-- 使用基础JS -->
    <div th:replace="~{common/base :: scripts}">
        <th:block th:fragment="js">
            <script th:src="@{/js/myscript.js}"></script>
        </th:block>
    </div>
</body>
</html>
```

### 4. 使用WebJars资源

在模板中直接使用WebJars资源：

```html
<!-- Bootstrap CSS -->
<link rel="stylesheet" th:href="@{/webjars/bootstrap/{version}/css/bootstrap.min.css(version=${webjarsVersion.bootstrap})}">

<!-- jQuery -->
<script th:src="@{/webjars/jquery/{version}/dist/jquery.min.js(version=${webjarsVersion.jquery})}"></script>

<!-- Bootstrap JS -->
<script th:src="@{/webjars/bootstrap/{version}/js/bootstrap.bundle.min.js(version=${webjarsVersion.bootstrap})}"></script>
```

## 自定义扩展

### 1. 自定义响应格式

您可以根据需要扩展 `ApiResponse` 类：

```java
public class ExtendedApiResponse<T> extends ApiResponse<T> {
    private String additionalInfo;
    
    // 构造函数、getter和setter
}
```

### 2. 自定义控制器基类

您可以创建更具体的控制器基类：

```java
@Controller
public abstract class AdminBaseController extends BaseController {
    
    @ModelAttribute("adminFeatures")
    public List<String> getAdminFeatures() {
        // 返回管理员功能列表
        return Arrays.asList("用户管理", "系统设置", "数据导出");
    }
}
```

## 最佳实践

1. **保持公共性**：只将真正通用的功能和资源放在 `common-terminal` 中
2. **版本管理**：定期检查和更新WebJars依赖版本
3. **模板约定**：遵循统一的模板命名和结构约定
4. **错误处理**：使用统一的错误处理和响应格式

## 注意事项

1. 本模块依赖于Spring Boot和Thymeleaf，确保使用兼容版本
2. 使用WebJars时，注意版本号的管理和更新
3. 模板继承时，确保保留必要的属性和占位符

## 示例项目

参考 `HomeController.java` 和 `home.html` 了解如何使用公共模块的功能。