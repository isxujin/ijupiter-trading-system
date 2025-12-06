package net.ijupiter.trading.boot.service.allinone.configs;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 客户模块数据库配置
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Configuration
public class CustomerDatabaseConfig {

    /**
     * 数据源初始化器，用于执行数据库初始化脚本
     * 
     * @param dataSource 数据源
     * @return 数据源初始化器
     */
    @Bean
    public DataSourceInitializer customerDataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        
        // 创建数据库表结构的SQL脚本
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new org.springframework.core.io.ClassPathResource("schema/customer_schema.sql"));
        populator.setIgnoreFailedDrops(true);  // 忽略删除失败的错误
        
        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(true);
        
        return initializer;
    }
}