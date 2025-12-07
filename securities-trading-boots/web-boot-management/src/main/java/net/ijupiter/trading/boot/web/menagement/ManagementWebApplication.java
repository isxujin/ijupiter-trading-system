package net.ijupiter.trading.boot.web.menagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 金融交易系统终端应用启动类（WAR包部署）
 * @author TradingSystem
 */
@SpringBootApplication
@ComponentScan({
        "net.ijupiter.trading.api",
        "net.ijupiter.trading.core",
        "net.ijupiter.trading.web",
        "net.ijupiter.trading.boot.web"
})
@EnableJpaRepositories(basePackages = "net.ijupiter.trading.core.*.repositories")
@EntityScan(basePackages = "net.ijupiter.trading.core.*.entities")
public class ManagementWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ManagementWebApplication.class, args);

        System.out.println("==================================================================");
        System.out.println("[OK] Securities Trading System terminal started successfully!");
        System.out.println("[OK] Administrator terminal:    http://localhost:9000/management");
        System.out.println("==================================================================");
    }
}
