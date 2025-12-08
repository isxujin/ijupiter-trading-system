package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据字典数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    /**
     * 根据字典编码查找字典
     *
     * @param dictCode 字典编码
     * @return 字典对象
     */
    Optional<Dictionary> findByDictCode(String dictCode);

    /**
     * 根据字典名称查找字典
     *
     * @param dictName 字典名称
     * @return 字典对象
     */
    Optional<Dictionary> findByDictName(String dictName);

    /**
     * 根据字典类型查找字典列表
     *
     * @param dictType 字典类型
     * @return 字典列表
     */
    List<Dictionary> findByDictType(String dictType);

    /**
     * 根据状态查找字典列表
     *
     * @param status 状态
     * @return 字典列表
     */
    List<Dictionary> findByStatus(Integer status);

    /**
     * 根据字典类型和状态查找字典列表
     *
     * @param dictType 字典类型
     * @param status 状态
     * @return 字典列表
     */
    @Query("SELECT d FROM Dictionary d WHERE d.dictType = :dictType AND d.status = :status")
    List<Dictionary> findByDictTypeAndStatus(@Param("dictType") String dictType, @Param("status") Integer status);

    /**
     * 查找系统字典列表
     *
     * @return 系统字典列表
     */
    @Query("SELECT d FROM Dictionary d WHERE d.dictType = 'system' ORDER BY d.dictName")
    List<Dictionary> findSystemDictionaries();

    /**
     * 查找业务字典列表
     *
     * @return 业务字典列表
     */
    @Query("SELECT d FROM Dictionary d WHERE d.dictType = 'business' ORDER BY d.dictName")
    List<Dictionary> findBusinessDictionaries();

    /**
     * 检查字典编码是否存在
     *
     * @param dictCode 字典编码
     * @return 是否存在
     */
    boolean existsByDictCode(String dictCode);

    /**
     * 检查字典名称是否存在
     *
     * @param dictName 字典名称
     * @return 是否存在
     */
    boolean existsByDictName(String dictName);

    /**
     * 模糊搜索字典（支持字典编码、字典名称、备注）
     *
     * @param keyword 关键字
     * @return 字典列表
     */
    @Query("SELECT d FROM Dictionary d WHERE d.dictCode LIKE %:keyword% OR d.dictName LIKE %:keyword% " +
           "OR d.remark LIKE %:keyword%")
    List<Dictionary> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据字典编码查找启用的字典
     *
     * @param dictCode 字典编码
     * @return 字典对象
     */
    @Query("SELECT d FROM Dictionary d WHERE d.dictCode = :dictCode AND d.status = 1")
    Optional<Dictionary> findActiveByDictCode(@Param("dictCode") String dictCode);
}