package net.ijupiter.trading.web.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 公共终端模块配置类
 * 确保公共控制器和服务被正确扫描
 */
@Configuration
@ComponentScan(basePackages = "net.ijupiter.trading.web.common")
public class CommonConfig {
}