package net.ijupiter.trading.boot.query.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.webjars.WebJarAssetLocator;

/**
 * WebJars配置类
 * 提供WebJarAssetLocator bean用于静态资源版本管理
 */
@Configuration
public class WebJarsConfig {

    @Bean
    public WebJarAssetLocator webJarAssetLocator() {
        return new WebJarAssetLocator();
    }
}