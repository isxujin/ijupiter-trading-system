package net.ijupiter.trading.adapter.cache.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.spi.cache.CacheAdapterService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存适配器
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheAdapter implements CacheAdapterService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        log.debug("设置缓存: key={}, value={}", key, value);
        
        try {
            redisTemplate.opsForValue().set(key, value);
            log.debug("缓存设置成功");
        } catch (Exception e) {
            log.error("缓存设置失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("缓存设置失败", e);
        }
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        log.debug("设置带超时的缓存: key={}, value={}, timeout={}, unit={}", key, value, timeout, timeUnit);
        
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            log.debug("带超时缓存设置成功");
        } catch (Exception e) {
            log.error("带超时缓存设置失败: key={}, timeout={}, unit={}, error={}", 
                    key, timeout, timeUnit, e.getMessage(), e);
            throw new RuntimeException("带超时缓存设置失败", e);
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        log.debug("设置条件缓存: key={}, value={}", key, value);
        
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
            log.debug("条件缓存设置结果: {}", result);
            return result != null && result;
        } catch (Exception e) {
            log.error("条件缓存设置失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("条件缓存设置失败", e);
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit) {
        log.debug("设置带超时的条件缓存: key={}, value={}, timeout={}, unit={}", key, value, timeout, timeUnit);
        
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
            log.debug("带超时条件缓存设置结果: {}", result);
            return result != null && result;
        } catch (Exception e) {
            log.error("带超时条件缓存设置失败: key={}, timeout={}, unit={}, error={}", 
                    key, timeout, timeUnit, e.getMessage(), e);
            throw new RuntimeException("带超时条件缓存设置失败", e);
        }
    }

    @Override
    public Object get(String key) {
        log.debug("获取缓存: key={}", key);
        
        try {
            Object value = redisTemplate.opsForValue().get(key);
            log.debug("缓存获取结果: key={}, value={}", key, value);
            return value;
        } catch (Exception e) {
            log.error("缓存获取失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("缓存获取失败", e);
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        log.debug("获取类型化缓存: key={}, clazz={}", key, clazz);
        
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null && clazz.isInstance(value)) {
                log.debug("类型化缓存获取结果: key={}, value={}", key, value);
                return clazz.cast(value);
            }
            log.debug("类型化缓存获取结果为空或不匹配类型");
            return null;
        } catch (Exception e) {
            log.error("类型化缓存获取失败: key={}, clazz={}, error={}", key, clazz, e.getMessage(), e);
            throw new RuntimeException("类型化缓存获取失败", e);
        }
    }

    @Override
    public void delete(String key) {
        log.debug("删除缓存: key={}", key);
        
        try {
            redisTemplate.delete(key);
            log.debug("缓存删除成功");
        } catch (Exception e) {
            log.error("缓存删除失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("缓存删除失败", e);
        }
    }

    @Override
    public void delete(Collection<String> keys) {
        log.debug("批量删除缓存: keys={}", keys);
        
        try {
            redisTemplate.delete(keys);
            log.debug("批量缓存删除成功");
        } catch (Exception e) {
            log.error("批量缓存删除失败: keys={}, error={}", keys, e.getMessage(), e);
            throw new RuntimeException("批量缓存删除失败", e);
        }
    }

    @Override
    public boolean exists(String key) {
        log.debug("检查缓存存在性: key={}", key);
        
        try {
            Boolean result = redisTemplate.hasKey(key);
            log.debug("缓存存在性检查结果: key={}, exists={}", key, result);
            return result != null && result;
        } catch (Exception e) {
            log.error("缓存存在性检查失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("缓存存在性检查失败", e);
        }
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        log.debug("设置缓存过期时间: key={}, timeout={}, unit={}", key, timeout, timeUnit);
        
        try {
            Boolean result = redisTemplate.expire(key, timeout, timeUnit);
            log.debug("缓存过期时间设置结果: key={}, result={}", key, result);
            return result != null && result;
        } catch (Exception e) {
            log.error("缓存过期时间设置失败: key={}, timeout={}, unit={}, error={}", 
                    key, timeout, timeUnit, e.getMessage(), e);
            throw new RuntimeException("缓存过期时间设置失败", e);
        }
    }

    @Override
    public long getExpire(String key) {
        log.debug("获取缓存过期时间: key={}", key);
        
        try {
            Long result = redisTemplate.getExpire(key);
            log.debug("缓存过期时间获取结果: key={}, expire={}", key, result);
            return result != null ? result : -1;
        } catch (Exception e) {
            log.error("缓存过期时间获取失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("缓存过期时间获取失败", e);
        }
    }

    @Override
    public void hSet(String key, String field, Object value) {
        log.debug("设置哈希缓存: key={}, field={}, value={}", key, field, value);
        
        try {
            redisTemplate.opsForHash().put(key, field, value);
            log.debug("哈希缓存设置成功");
        } catch (Exception e) {
            log.error("哈希缓存设置失败: key={}, field={}, error={}", key, field, e.getMessage(), e);
            throw new RuntimeException("哈希缓存设置失败", e);
        }
    }

    @Override
    public Object hGet(String key, String field) {
        log.debug("获取哈希缓存: key={}, field={}", key, field);
        
        try {
            Object value = redisTemplate.opsForHash().get(key, field);
            log.debug("哈希缓存获取结果: key={}, field={}, value={}", key, field, value);
            return value;
        } catch (Exception e) {
            log.error("哈希缓存获取失败: key={}, field={}, error={}", key, field, e.getMessage(), e);
            throw new RuntimeException("哈希缓存获取失败", e);
        }
    }

    @Override
    public Map<Object, Object> hGetAll(String key) {
        log.debug("获取全部哈希缓存: key={}", key);
        
        try {
            Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
            log.debug("全部哈希缓存获取结果: key={}, values={}", key, values);
            return values;
        } catch (Exception e) {
            log.error("全部哈希缓存获取失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("全部哈希缓存获取失败", e);
        }
    }

    @Override
    public void hDelete(String key, String... fields) {
        log.debug("删除哈希缓存字段: key={}, fields={}", key, fields);
        
        try {
            redisTemplate.opsForHash().delete(key, (Object[]) fields);
            log.debug("哈希缓存字段删除成功");
        } catch (Exception e) {
            log.error("哈希缓存字段删除失败: key={}, fields={}, error={}", key, fields, e.getMessage(), e);
            throw new RuntimeException("哈希缓存字段删除失败", e);
        }
    }

    @Override
    public void lPush(String key, Object value) {
        log.debug("左推列表缓存: key={}, value={}", key, value);
        
        try {
            redisTemplate.opsForList().leftPush(key, value);
            log.debug("列表缓存左推成功");
        } catch (Exception e) {
            log.error("列表缓存左推失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("列表缓存左推失败", e);
        }
    }

    @Override
    public void rPush(String key, Object value) {
        log.debug("右推列表缓存: key={}, value={}", key, value);
        
        try {
            redisTemplate.opsForList().rightPush(key, value);
            log.debug("列表缓存右推成功");
        } catch (Exception e) {
            log.error("列表缓存右推失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("列表缓存右推失败", e);
        }
    }

    @Override
    public Object lPop(String key) {
        log.debug("左弹出列表缓存: key={}", key);
        
        try {
            Object value = redisTemplate.opsForList().leftPop(key);
            log.debug("列表缓存左弹出结果: key={}, value={}", key, value);
            return value;
        } catch (Exception e) {
            log.error("列表缓存左弹出失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("列表缓存左弹出失败", e);
        }
    }

    @Override
    public Object rPop(String key) {
        log.debug("右弹出列表缓存: key={}", key);
        
        try {
            Object value = redisTemplate.opsForList().rightPop(key);
            log.debug("列表缓存右弹出结果: key={}, value={}", key, value);
            return value;
        } catch (Exception e) {
            log.error("列表缓存右弹出失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("列表缓存右弹出失败", e);
        }
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        log.debug("获取列表缓存范围: key={}, start={}, end={}", key, start, end);
        
        try {
            List<Object> values = redisTemplate.opsForList().range(key, start, end);
            log.debug("列表缓存范围获取结果: key={}, start={}, end={}, values={}", key, start, end, values);
            return values;
        } catch (Exception e) {
            log.error("列表缓存范围获取失败: key={}, start={}, end={}, error={}", key, start, end, e.getMessage(), e);
            throw new RuntimeException("列表缓存范围获取失败", e);
        }
    }

    @Override
    public void sAdd(String key, Object... values) {
        log.debug("添加集合缓存: key={}, values={}", key, values);
        
        try {
            redisTemplate.opsForSet().add(key, values);
            log.debug("集合缓存添加成功");
        } catch (Exception e) {
            log.error("集合缓存添加失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("集合缓存添加失败", e);
        }
    }

    @Override
    public Set<Object> sMembers(String key) {
        log.debug("获取集合缓存成员: key={}", key);
        
        try {
            Set<Object> members = redisTemplate.opsForSet().members(key);
            log.debug("集合缓存成员获取结果: key={}, members={}", key, members);
            return members;
        } catch (Exception e) {
            log.error("集合缓存成员获取失败: key={}, error={}", key, e.getMessage(), e);
            throw new RuntimeException("集合缓存成员获取失败", e);
        }
    }

    @Override
    public boolean sIsMember(String key, Object value) {
        log.debug("检查集合成员: key={}, value={}", key, value);
        
        try {
            Boolean result = redisTemplate.opsForSet().isMember(key, value);
            log.debug("集合成员检查结果: key={}, value={}, isMember={}", key, value, result);
            return result != null && result;
        } catch (Exception e) {
            log.error("集合成员检查失败: key={}, value={}, error={}", key, value, e.getMessage(), e);
            throw new RuntimeException("集合成员检查失败", e);
        }
    }

    @Override
    public long sRem(String key, Object... values) {
        log.debug("移除集合成员: key={}, values={}", key, values);
        
        try {
            Long result = redisTemplate.opsForSet().remove(key, values);
            log.debug("集合成员移除结果: key={}, values={}, count={}", key, values, result);
            return result != null ? result : 0;
        } catch (Exception e) {
            log.error("集合成员移除失败: key={}, values={}, error={}", key, values, e.getMessage(), e);
            throw new RuntimeException("集合成员移除失败", e);
        }
    }
}