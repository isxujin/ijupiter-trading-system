package net.ijupiter.trading.web.common.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.webjars.WebJarAssetLocator;

/**
 * 公共终端模块配置类
 * 确保公共控制器和服务被正确扫描
 */
@Configuration
@ComponentScan(basePackages = "net.ijupiter.trading.web.common")
public class CommonConfig {
    // 定义WebJarAssetLocator的Bean，供Spring容器管理
    @Bean
    public WebJarAssetLocator webJarAssetLocator() {
        return new WebJarAssetLocator(); // 实例化WebJarAssetLocator
    }
}