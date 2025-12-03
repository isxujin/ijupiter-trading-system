package net.ijupiter.trading.adapter.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis配置属性
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@ConfigurationProperties(prefix = "trading.redis")
public class RedisProperties {

    /**
     * 默认过期时间（秒）
     */
    private int defaultTimeout = 3600;

    /**
     * 缓存键前缀
     */
    private String keyPrefix = "trading:";

    /**
     * 是否启用空值缓存
     */
    private boolean cacheNullValues = false;

    /**
     * 空值缓存过期时间（秒）
     */
    private int nullValueTimeout = 300;

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    public int getNullValueTimeout() {
        return nullValueTimeout;
    }

    public void setNullValueTimeout(int nullValueTimeout) {
        this.nullValueTimeout = nullValueTimeout;
    }
}