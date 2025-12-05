package net.ijupiter.trading.common.services;

import java.util.List;
import java.util.Optional;

/**
 * 服务接口基类
 * 
 * @param <T> 实体类型
 * @param <ID> 实体ID类型
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface BaseService<T, ID> {

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 保存后的实体对象
     */
    T save(T entity);

    /**
     * 根据ID查找实体
     *
     * @param id ID
     * @return 实体对象
     */
    Optional<T> findById(ID id);

    /**
     * 查找所有实体
     *
     * @return 实体对象列表
     */
    List<T> findAll();

    /**
     * 根据ID列表查找实体
     *
     * @param ids ID列表
     * @return 实体对象列表
     */
    List<T> findAllById(List<ID> ids);

    /**
     * 根据ID判断实体是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    boolean existsById(ID id);

    /**
     * 统计实体数量
     *
     * @return 实体数量
     */
    long count();

    /**
     * 根据ID删除实体
     *
     * @param id ID
     */
    void deleteById(ID id);

    /**
     * 删除实体
     *
     * @param entity 实体对象
     */
    void delete(T entity);

    /**
     * 删除所有实体
     */
    void deleteAll();

    /**
     * 保存实体并立即刷新
     *
     * @param entity 实体对象
     * @return 保存后的实体对象
     */
    T saveAndFlush(T entity);

    /**
     * 批量保存实体
     *
     * @param entities 实体对象列表
     * @return 保存后的实体对象列表
     */
    List<T> saveAll(List<T> entities);
}