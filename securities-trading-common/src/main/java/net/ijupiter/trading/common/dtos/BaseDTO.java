package net.ijupiter.trading.common.dtos;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据传输对象基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseDTO<T extends BaseDTO<T>> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    protected Long id;

    /**
     * 将源对象转换为当前 DTO 子类实例
     * @param source 源对象
     * @return 当前DTO子类实例（已填充源对象属性）
     */
    @SuppressWarnings("unchecked")
    public  T convertFrom(Object source) {
        // 1. 校验源对象非空
        if (source == null) {
            throw new IllegalArgumentException("转换失败：源对象不能为null");
        }

        try {
            // 2. 核心：将源对象属性拷贝到当前DTO实例（this）中
            BeanUtils.copyProperties(source, this);
        } catch (Exception e) {
            // 增加对 BeanUtils.copyProperties 可能抛出的异常处理
            throw new RuntimeException("转换失败：拷贝属性时发生异常", e);
        }
        // 3. 返回当前实例（泛型保证类型安全，无需强制转换）
        return (T) this;

    }
}