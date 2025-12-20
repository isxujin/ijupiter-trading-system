package net.ijupiter.trading.boot.web.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Web测试启动器主类
 * 扫描范围包含admin-terminal等Web模块
 */
@SpringBootApplication
@ComponentScan({
        "net.ijupiter.trading.api",
        "net.ijupiter.trading.core",
        "net.ijupiter.trading.web",
        "net.ijupiter.trading.boot.web"
})
public class InvestmentWebApplication {
    public static void main(String[] args) {
        // 1. 启动Spring应用，获取可配置的应用上下文（核心：必须先启动上下文才能读取配置）
        ConfigurableApplicationContext context =  SpringApplication.run(InvestmentWebApplication.class, args);

        // 2. 从上下文的环境配置中读取
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String systemName = context.getEnvironment().getProperty("system.web.name", "iJupiter Trading System");
        // 3. 打印启动成功信息
        System.out.println("==================================================================");
        System.out.println("[OK] " + systemName + " started successfully!");
        System.out.println("[OK] Console Address: http://localhost:" + port + "/investment");
        System.out.println("==================================================================");
    }
}

