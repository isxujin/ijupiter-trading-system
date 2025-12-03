package net.ijupiter.trading.spi.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存适配器服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface CacheAdapterService {

    /**
     * 设置缓存
     * 
     * @param key 缓存键
     * @param value 缓存值
     */
    void set(String key, Object value);

    /**
     * 设置带超时的缓存
     * 
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     */
    void set(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 条件设置缓存（如果键不存在）
     * 
     * @param key 缓存键
     * @param value 缓存值
     * @return 是否设置成功
     */
    boolean setIfAbsent(String key, Object value);

    /**
     * 条件设置带超时的缓存（如果键不存在）
     * 
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     * 
     * @param key 缓存键
     * @return 缓存值
     */
    Object get(String key);

    /**
     * 获取类型化缓存
     * 
     * @param key 缓存键
     * @param clazz 值类型
     * @param <T> 值类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存
     * 
     * @param key 缓存键
     */
    void delete(String key);

    /**
     * 批量删除缓存
     * 
     * @param keys 缓存键集合
     */
    void delete(Collection<String> keys);

    /**
     * 检查缓存是否存在
     * 
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 设置缓存过期时间
     * 
     * @param key 缓存键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存过期时间
     * 
     * @param key 缓存键
     * @return 过期时间（秒），-1表示永不过期，-2表示不存在
     */
    long getExpire(String key);

    /**
     * 设置哈希缓存
     * 
     * @param key 缓存键
     * @param field 哈希字段
     * @param value 哈希值
     */
    void hSet(String key, String field, Object value);

    /**
     * 获取哈希缓存
     * 
     * @param key 缓存键
     * @param field 哈希字段
     * @return 哈希值
     */
    Object hGet(String key, String field);

    /**
     * 获取全部哈希缓存
     * 
     * @param key 缓存键
     * @return 哈希映射
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 删除哈希缓存字段
     * 
     * @param key 缓存键
     * @param fields 哈希字段
     */
    void hDelete(String key, String... fields);

    /**
     * 左推列表缓存
     * 
     * @param key 缓存键
     * @param value 值
     */
    void lPush(String key, Object value);

    /**
     * 右推列表缓存
     * 
     * @param key 缓存键
     * @param value 值
     */
    void rPush(String key, Object value);

    /**
     * 左弹出列表缓存
     * 
     * @param key 缓存键
     * @return 值
     */
    Object lPop(String key);

    /**
     * 右弹出列表缓存
     * 
     * @param key 缓存键
     * @return 值
     */
    Object rPop(String key);

    /**
     * 获取列表缓存范围
     * 
     * @param key 缓存键
     * @param start 开始索引
     * @param end 结束索引
     * @return 值列表
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 添加集合缓存
     * 
     * @param key 缓存键
     * @param values 值数组
     */
    void sAdd(String key, Object... values);

    /**
     * 获取集合缓存成员
     * 
     * @param key 缓存键
     * @return 成员集合
     */
    Set<Object> sMembers(String key);

    /**
     * 检查集合成员
     * 
     * @param key 缓存键
     * @param value 值
     * @return 是否是成员
     */
    boolean sIsMember(String key, Object value);

    /**
     * 移除集合成员
     * 
     * @param key 缓存键
     * @param values 值数组
     * @return 移除数量
     */
    long sRem(String key, Object... values);
}