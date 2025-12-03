package net.ijupiter.trading.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Web MVC 核心配置类
 * Thymeleaf配置类
 * 配置WebJars资源处理器，解决WebJars资源映射、静态资源访问、Thymeleaf模板解析等问题
 */
@Configuration
public class ThymeleafConfig implements WebMvcConfigurer {

/**
     * 资源处理器配置：
     * 1. 配置WebJars资源映射（解决bootstrap等依赖的访问）
     * 2. 配置本地静态资源（static/public/resources/META-INF/resources）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ========== 核心：WebJars资源映射 ==========
        // 匹配 /webjars/** 请求，映射到classpath:/META-INF/resources/webjars/
        // 解决 ${webjarsVersion.bootstrap} 依赖的资源访问问题
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(3600); // 生产环境缓存1小时，开发环境可设为0

        // ========== 本地静态资源映射 ==========
        // 匹配 /static/**、/css/**、/js/**、/img/** 等请求，映射到classpath:/static/
        registry.addResourceHandler("/**")
                .addResourceLocations(
                        "classpath:/static/",
                        "classpath:/public/",
                        "classpath:/resources/",
                        "classpath:/META-INF/resources/"
                )
                .setCachePeriod(0); // 开发环境关闭缓存，生产环境建议设3600+
    }
}