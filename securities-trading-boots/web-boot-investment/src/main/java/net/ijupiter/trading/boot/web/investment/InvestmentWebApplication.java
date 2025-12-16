package net.ijupiter.trading.boot.web.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        SpringApplication.run(InvestmentWebApplication.class, args);

        System.out.println("==================================================================");
        System.out.println("[OK] Securities Trading System terminal started successfully!");
        System.out.println("[OK] Investment terminal:         http://localhost:9001/investment");
        System.out.println("==================================================================");
    }
}

