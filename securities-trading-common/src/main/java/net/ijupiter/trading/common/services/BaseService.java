package net.ijupiter.trading.common.services;

import java.util.List;
import java.util.Optional;

/**
 * 服务接口基类
 * 
 * @param <T> 数据传输对象类型
 * @param <ID> 数据传输对象ID类型
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface BaseService<T, ID> {

    /**
     * 保存实体
     *
     * @param dto 数据传输对象
     * @return 保存后的数据传输对象
     */
    T save(T dto);

    /**
     * 根据ID查找对象
     *
     * @param id ID
     * @return 数据传输对象
     */
    Optional<T> findById(ID id);

    /**
     * 查找所有对象
     *
     * @return 数据传输对象列表
     */
    List<T> findAll();

    /**
     * 根据ID列表查找对象
     *
     * @param ids ID列表
     * @return 对象列表
     */
    List<T> findAllById(List<ID> ids);

    /**
     * 根据ID判断对象是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    boolean existsById(ID id);

    /**
     * 统计对象数量
     *
     * @return 对象数量
     */
    long count();

    /**
     * 根据ID删除对象
     *
     * @param id ID
     */
    void deleteById(ID id);

    /**
     * 删除对象
     *
     * @param dto 数据传输对象
     */
    void delete(T dto);

    /**
     * 删除所有对象
     */
    void deleteAll();

    /**
     * 保存实体并立即刷新
     *
     * @param dto 数据传输对象
     * @return 保存后的对象
     */
    T saveAndFlush(T dto);

    /**
     * 批量保存实体
     *
     * @param dtos 数据传输对象列表
     * @return 保存后的对象列表
     */
    List<T> saveAll(List<T> dtos);
}