package net.ijupiter.trading.boot.service.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 查询模块配置属性
 * 
 * @author ijupiter
 */
@Data
@ConfigurationProperties(prefix = "trading.query")
public class QueryProperties {
    
    /**
     * 查询处理器配置
     */
    private EventProcessor eventProcessor = new EventProcessor();
    
    /**
     * 缓存配置
     */
    private Cache cache = new Cache();
    
    /**
     * 分页配置
     */
    private Pagination pagination = new Pagination();
    
    @Data
    public static class EventProcessor {
        /**
         * 事件处理器线程池大小
         */
        private int threadPoolSize = 4;
        
        /**
         * 事件处理器批次大小
         */
        private int batchSize = 100;
    }
    
    @Data
    public static class Cache {
        /**
         * 是否启用缓存
         */
        private boolean enabled = true;
        
        /**
         * 缓存过期时间（秒）
         */
        private long ttl = 300;
        
        /**
         * 最大缓存条目数
         */
        private long maxSize = 10000;
    }
    
    @Data
    public static class Pagination {
        /**
         * 默认页码
         */
        private int defaultPage = 1;
        
        /**
         * 默认每页大小
         */
        private int defaultSize = 20;
        
        /**
         * 最大每页大小
         */
        private int maxSize = 100;
    }
}