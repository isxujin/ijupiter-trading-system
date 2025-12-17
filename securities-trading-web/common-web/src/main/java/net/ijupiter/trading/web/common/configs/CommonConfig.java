package net.ijupiter.trading.web.common.configs;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.webjars.WebJarAssetLocator;

import java.util.Set;

/**
 * 公共终端模块配置类
 * 确保公共控制器和服务被正确扫描
 */
@Configuration
@ComponentScan(basePackages = "net.ijupiter.trading.web")
public class CommonConfig {
    // 定义WebJarAssetLocator的Bean，供Spring容器管理
    @Bean
    public WebJarAssetLocator webJarAssetLocator() {
        return new WebJarAssetLocator(); // 实例化WebJarAssetLocator
    }

    /**
     * 配置本地模板解析器，用于解析当前模块中的模板
     */
    @Bean
    @Primary
    public ITemplateResolver primaryTemplateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * 配置外部模板解析器，用于解析依赖模块中的模板
     */
    @Bean
    public ITemplateResolver secondaryTemplateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath*:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * 配置模板引擎，使用多个模板解析器
     */
    @Bean
    @Primary
    public SpringTemplateEngine templateEngine(Set<ITemplateResolver> templateResolvers) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolvers(templateResolvers);
        templateEngine.setAdditionalDialects(Set.of(layoutDialect()));
        return templateEngine;
    }

    /**
     * 配置Layout方言
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}