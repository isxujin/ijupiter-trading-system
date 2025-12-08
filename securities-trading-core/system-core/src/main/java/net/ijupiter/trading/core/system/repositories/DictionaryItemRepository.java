package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据字典项数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long> {

    /**
     * 根据字典项编码查找字典项
     *
     * @param itemCode 字典项编码
     * @return 字典项对象
     */
    Optional<DictionaryItem> findByItemCode(String itemCode);

    /**
     * 根据字典ID查找字典项列表
     *
     * @param dictionaryId 字典ID
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di WHERE di.dictionary.id = :dictionaryId ORDER BY di.sortOrder")
    List<DictionaryItem> findByDictionaryId(@Param("dictionaryId") Long dictionaryId);

    /**
     * 根据字典编码查找字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di JOIN di.dictionary d WHERE d.dictCode = :dictCode ORDER BY di.sortOrder")
    List<DictionaryItem> findByDictCode(@Param("dictCode") String dictCode);

    /**
     * 根据字典项值查找字典项
     *
     * @param itemValue 字典项值
     * @return 字典项对象
     */
    Optional<DictionaryItem> findByItemValue(String itemValue);

    /**
     * 根据状态查找字典项列表
     *
     * @param status 状态
     * @return 字典项列表
     */
    List<DictionaryItem> findByStatus(Integer status);

    /**
     * 根据字典ID和状态查找字典项列表
     *
     * @param dictionaryId 字典ID
     * @param status 状态
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di WHERE di.dictionary.id = :dictionaryId AND di.status = :status ORDER BY di.sortOrder")
    List<DictionaryItem> findByDictionaryIdAndStatus(@Param("dictionaryId") Long dictionaryId, @Param("status") Integer status);

    /**
     * 根据字典编码和状态查找字典项列表
     *
     * @param dictCode 字典编码
     * @param status 状态
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di JOIN di.dictionary d WHERE d.dictCode = :dictCode AND di.status = :status ORDER BY di.sortOrder")
    List<DictionaryItem> findByDictCodeAndStatus(@Param("dictCode") String dictCode, @Param("status") Integer status);

    /**
     * 根据字典ID和字典项值查找字典项
     *
     * @param dictionaryId 字典ID
     * @param itemValue 字典项值
     * @return 字典项对象
     */
    @Query("SELECT di FROM DictionaryItem di WHERE di.dictionary.id = :dictionaryId AND di.itemValue = :itemValue")
    Optional<DictionaryItem> findByDictionaryIdAndItemValue(@Param("dictionaryId") Long dictionaryId, @Param("itemValue") String itemValue);

    /**
     * 根据字典编码和字典项值查找字典项
     *
     * @param dictCode 字典编码
     * @param itemValue 字典项值
     * @return 字典项对象
     */
    @Query("SELECT di FROM DictionaryItem di JOIN di.dictionary d WHERE d.dictCode = :dictCode AND di.itemValue = :itemValue")
    Optional<DictionaryItem> findByDictCodeAndItemValue(@Param("dictCode") String dictCode, @Param("itemValue") String itemValue);

    /**
     * 检查字典项编码是否存在
     *
     * @param itemCode 字典项编码
     * @return 是否存在
     */
    boolean existsByItemCode(String itemCode);

    /**
     * 模糊搜索字典项（支持字典项编码、字典项值、字典项标签、备注）
     *
     * @param keyword 关键字
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di WHERE di.itemCode LIKE %:keyword% OR di.itemValue LIKE %:keyword% " +
           "OR di.itemLabel LIKE %:keyword% OR di.remark LIKE %:keyword%")
    List<DictionaryItem> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据排序号范围查找字典项列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 字典项列表
     */
    @Query("SELECT di FROM DictionaryItem di WHERE di.sortOrder BETWEEN :minSortOrder AND :maxSortOrder ORDER BY di.sortOrder")
    List<DictionaryItem> findBySortOrderBetween(@Param("minSortOrder") Integer minSortOrder, @Param("maxSortOrder") Integer maxSortOrder);
}