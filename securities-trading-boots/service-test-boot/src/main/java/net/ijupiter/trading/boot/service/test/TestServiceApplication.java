package net.ijupiter.trading.boot.service.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.CountDownLatch;
/**
 * 金融交易系统核心服务启动类
 * @author TradingSystem
 */
@SpringBootApplication(scanBasePackages = "net.ijupiter.trading")
@EnableJpaRepositories(basePackages = "net.ijupiter.trading.core")
@EntityScan(basePackages = {
        "net.ijupiter.trading.core",                        // 应用自身实体包
        "org.axonframework.eventhandling.tokenstore.jpa",   // Axon TokenEntry 实体包
        "org.axonframework.eventsourcing.eventstore.jpa"    // Axon DomainEventEntry/SnapshotEventEntry 实体包
})
public class TestServiceApplication {
    // 用于阻塞主线程的计数器
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        // 核心：在 Axon 初始化前设置系统属性，屏蔽提示
        System.setProperty("disable-axoniq-console-message", "true");

        SpringApplication.run(TestServiceApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("[OK] Securities Trading System core services started successfully!");
        System.out.println("==================================================================");

        // 阻塞主线程，避免应用退出
        try {
            latch.await(); // 主线程阻塞，直到 latch.countDown() 被调用
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    // 可选：提供关闭方法（如通过接口/信号触发）
    public static void shutdown() {
        latch.countDown(); // 释放计数器，应用退出
    }
}
