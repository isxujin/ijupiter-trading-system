package net.ijupiter.trading.boot.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 查询服务启动类
 * @author TradingSystem
 */
@SpringBootApplication(
        scanBasePackages = {
                "net.ijupiter.trading.core.query",
                "net.ijupiter.trading.web.query"
        }
)
@EnableJpaRepositories(basePackages = "net.ijupiter.trading.core.query.repositories")
@EntityScan(basePackages = "net.ijupiter.trading.core.query.entities")
@EnableFeignClients
public class QueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("[OK] Query services started successfully!");
        System.out.println("==================================================================");
    }
}