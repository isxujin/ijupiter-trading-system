package net.ijupiter.trading.boot.web.allinone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 金融交易系统终端应用启动类（WAR包部署）
 * @author TradingSystem
 */
@SpringBootApplication
@ComponentScan({
        "net.ijupiter.trading.web",  // management-terminal的控制器、配置
        "net.ijupiter.trading.boot.web"
})
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);

        System.out.println("==================================================================");
        System.out.println("[OK] Securities Trading System terminal started successfully!");
        System.out.println("[OK] Administrator terminal:    http://localhost:9000/management");
        System.out.println("[OK] Investor terminal:         http://localhost:9000/investor");
        System.out.println("==================================================================");
    }
}
