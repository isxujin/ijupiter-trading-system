package net.ijupiter.trading.boot.web.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Web测试启动器主类
 * 扫描范围包含admin-terminal等Web模块
 */
@SpringBootApplication
@ComponentScan({
        "net.ijupiter.trading.web",  // admin-terminal的控制器、配置
        "net.ijupiter.trading.boot.web"
})
public class TestWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestWebApplication.class, args);

        System.out.println("==================================================================");
        System.out.println("[OK] Financial Trading System terminal started successfully!");
        System.out.println("[OK] Administrator terminal:    http://localhost:9000/admin");
        System.out.println("[OK] Investor terminal:         http://localhost:9000/investor");
        System.out.println("==================================================================");
    }
}

