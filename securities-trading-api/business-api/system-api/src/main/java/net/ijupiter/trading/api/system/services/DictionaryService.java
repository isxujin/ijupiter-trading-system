package net.ijupiter.trading.api.system.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.system.dtos.DictionaryDTO;
import net.ijupiter.trading.api.system.dtos.DictionaryItemDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据字典服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface DictionaryService extends BaseService<DictionaryDTO, Long> {

    /**
     * 根据字典编码查找字典
     *
     * @param dictCode 字典编码
     * @return 字典DTO
     */
    Optional<DictionaryDTO> findByDictCode(String dictCode);

    /**
     * 根据字典名称查找字典
     *
     * @param dictName 字典名称
     * @return 字典DTO
     */
    Optional<DictionaryDTO> findByDictName(String dictName);

    /**
     * 根据字典类型查找字典列表
     *
     * @param dictType 字典类型
     * @return 字典DTO列表
     */
    List<DictionaryDTO> findByDictType(String dictType);

    /**
     * 根据状态查找字典列表
     *
     * @param status 状态
     * @return 字典DTO列表
     */
    List<DictionaryDTO> findByStatus(Integer status);

    /**
     * 根据字典类型和状态查找字典列表
     *
     * @param dictType 字典类型
     * @param status 状态
     * @return 字典DTO列表
     */
    List<DictionaryDTO> findByDictTypeAndStatus(String dictType, Integer status);

    /**
     * 查找系统字典列表
     *
     * @return 系统字典DTO列表
     */
    List<DictionaryDTO> findSystemDictionaries();

    /**
     * 查找业务字典列表
     *
     * @return 业务字典DTO列表
     */
    List<DictionaryDTO> findBusinessDictionaries();

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
     * @return 字典DTO列表
     */
    List<DictionaryDTO> searchByKeyword(String keyword);

    /**
     * 根据字典编码查找启用的字典
     *
     * @param dictCode 字典编码
     * @return 字典DTO
     */
    Optional<DictionaryDTO> findActiveByDictCode(String dictCode);

    /**
     * 创建字典及其项
     *
     * @param dictionaryDTO 字典DTO
     * @return 创建后的字典DTO
     */
    DictionaryDTO createDictionary(DictionaryDTO dictionaryDTO);

    /**
     * 更新字典信息
     *
     * @param dictionaryDTO 字典DTO
     * @return 更新后的字典DTO
     */
    DictionaryDTO updateDictionary(DictionaryDTO dictionaryDTO);

    /**
     * 启用字典
     *
     * @param dictionaryId 字典ID
     * @return 是否启用成功
     */
    boolean enableDictionary(Long dictionaryId);

    /**
     * 禁用字典
     *
     * @param dictionaryId 字典ID
     * @return 是否禁用成功
     */
    boolean disableDictionary(Long dictionaryId);

    /**
     * 根据字典编码查找字典及其项信息
     *
     * @param dictCode 字典编码
     * @return 字典DTO（包含项信息）
     */
    Optional<DictionaryDTO> findDictionaryWithItems(String dictCode);

    /**
     * 获取所有字典编码和名称的映射
     *
     * @return 字典编码和名称的映射
     */
    Map<String, String> getDictCodeNameMap();

    /**
     * 获取指定字典编码的选项值映射
     *
     * @param dictCode 字典编码
     * @return 选项值和标签的映射
     */
    Map<String, String> getItemValueLabelMap(String dictCode);

    /**
     * 获取指定字典编码的启用选项值映射
     *
     * @param dictCode 字典编码
     * @return 启用选项值和标签的映射
     */
    Map<String, String> getActiveItemValueLabelMap(String dictCode);

    /**
     * 根据字典项编码查找字典项
     *
     * @param itemCode 字典项编码
     * @return 字典项DTO
     */
    Optional<DictionaryItemDTO> findItemByItemCode(String itemCode);

    /**
     * 根据字典ID查找字典项列表
     *
     * @param dictionaryId 字典ID
     * @return 字典项DTO列表
     */
    List<DictionaryItemDTO> findItemsByDictionaryId(Long dictionaryId);

    /**
     * 根据字典编码查找字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项DTO列表
     */
    List<DictionaryItemDTO> findItemsByDictCode(String dictCode);

    /**
     * 根据字典编码和字典项值查找字典项
     *
     * @param dictCode 字典编码
     * @param itemValue 字典项值
     * @return 字典项DTO
     */
    Optional<DictionaryItemDTO> findItemByDictCodeAndItemValue(String dictCode, String itemValue);

    /**
     * 创建字典项
     *
     * @param dictionaryItemDTO 字典项DTO
     * @return 创建后的字典项DTO
     */
    DictionaryItemDTO createDictionaryItem(DictionaryItemDTO dictionaryItemDTO);

    /**
     * 批量创建字典项
     *
     * @param dictCode 字典编码
     * @param items 字典项DTO列表
     * @return 创建后的字典项DTO列表
     */
    List<DictionaryItemDTO> createDictionaryItems(String dictCode, List<DictionaryItemDTO> items);

    /**
     * 更新字典项信息
     *
     * @param dictionaryItemDTO 字典项DTO
     * @return 更新后的字典项DTO
     */
    DictionaryItemDTO updateDictionaryItem(DictionaryItemDTO dictionaryItemDTO);

    /**
     * 启用字典项
     *
     * @param itemId 字典项ID
     * @return 是否启用成功
     */
    boolean enableDictionaryItem(Long itemId);

    /**
     * 禁用字典项
     *
     * @param itemId 字典项ID
     * @return 是否禁用成功
     */
    boolean disableDictionaryItem(Long itemId);

    /**
     * 删除字典项
     *
     * @param itemId 字典项ID
     * @return 是否删除成功
     */
    boolean deleteDictionaryItem(Long itemId);

    /**
     * 根据字典编码删除所有字典项
     *
     * @param dictCode 字典编码
     * @return 是否删除成功
     */
    boolean deleteAllDictionaryItems(String dictCode);

    /**
     * 批量更新字典项排序
     *
     * @param items 字典项DTO列表（需包含ID和排序号）
     * @return 是否更新成功
     */
    boolean updateItemsSortOrder(List<DictionaryItemDTO> items);

    /**
     * 获取字典项的数量
     *
     * @param dictCode 字典编码
     * @return 字典项数量
     */
    Integer getItemCount(String dictCode);
}