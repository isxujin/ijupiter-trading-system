package net.ijupiter.trading.core.system.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.exceptions.BusinessException;
import net.ijupiter.trading.api.system.dtos.DictionaryDTO;
import net.ijupiter.trading.api.system.services.DictionaryService;
import net.ijupiter.trading.api.system.dtos.DictionaryItemDTO;
import net.ijupiter.trading.core.system.entities.Dictionary;
import net.ijupiter.trading.core.system.entities.DictionaryItem;
import net.ijupiter.trading.core.system.repositories.DictionaryRepository;
import net.ijupiter.trading.core.system.repositories.DictionaryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据字典服务实现类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryItemRepository dictionaryItemRepository;

    @Override
    public DictionaryDTO save(DictionaryDTO dto) {
        Dictionary dictionary = convertToEntity(dto);
        Dictionary savedDictionary = dictionaryRepository.save(dictionary);
        return convertToDTO(savedDictionary);
    }

    @Override
    public Optional<DictionaryDTO> findById(Long id) {
        return dictionaryRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<DictionaryDTO> findAll() {
        return dictionaryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryDTO> findAllById(List<Long> ids) {
        return dictionaryRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return dictionaryRepository.existsById(id);
    }

    @Override
    public long count() {
        return dictionaryRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        dictionaryRepository.deleteById(id);
    }

    @Override
    public void delete(DictionaryDTO dto) {
        Dictionary dictionary = dictionaryRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("字典不存在"));
        dictionaryRepository.delete(dictionary);
    }

    @Override
    public void deleteAll() {
        dictionaryRepository.deleteAll();
    }

    @Override
    public DictionaryDTO saveAndFlush(DictionaryDTO dto) {
        Dictionary dictionary = convertToEntity(dto);
        Dictionary savedDictionary = dictionaryRepository.saveAndFlush(dictionary);
        return convertToDTO(savedDictionary);
    }

    @Override
    public List<DictionaryDTO> saveAll(List<DictionaryDTO> dtos) {
        List<Dictionary> dictionaries = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        List<Dictionary> savedDictionaries = dictionaryRepository.saveAll(dictionaries);
        return savedDictionaries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DictionaryDTO> findByDictCode(String dictCode) {
        return dictionaryRepository.findByDictCode(dictCode)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<DictionaryDTO> findByDictName(String dictName) {
        return dictionaryRepository.findByDictName(dictName)
                .map(this::convertToDTO);
    }

    @Override
    public List<DictionaryDTO> findByDictType(String dictType) {
        return dictionaryRepository.findByDictType(dictType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryDTO> findByStatus(Integer status) {
        return dictionaryRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryDTO> findByDictTypeAndStatus(String dictType, Integer status) {
        return dictionaryRepository.findByDictTypeAndStatus(dictType, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryDTO> findSystemDictionaries() {
        return dictionaryRepository.findSystemDictionaries().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryDTO> findBusinessDictionaries() {
        return dictionaryRepository.findBusinessDictionaries().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDictCode(String dictCode) {
        return dictionaryRepository.existsByDictCode(dictCode);
    }

    @Override
    public boolean existsByDictName(String dictName) {
        return dictionaryRepository.existsByDictName(dictName);
    }

    @Override
    public List<DictionaryDTO> searchByKeyword(String keyword) {
        return dictionaryRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DictionaryDTO> findActiveByDictCode(String dictCode) {
        return dictionaryRepository.findActiveByDictCode(dictCode)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public DictionaryDTO createDictionary(DictionaryDTO dictionaryDTO) {
        // 验证字典编码和字典名唯一性
        if (existsByDictCode(dictionaryDTO.getDictCode())) {
            throw new BusinessException("字典编码已存在：" + dictionaryDTO.getDictCode());
        }
        if (existsByDictName(dictionaryDTO.getDictName())) {
            throw new BusinessException("字典名称已存在：" + dictionaryDTO.getDictName());
        }

        // 转换为实体
        Dictionary dictionary = convertToEntity(dictionaryDTO);

        Dictionary savedDictionary = dictionaryRepository.save(dictionary);
        return convertToDTO(savedDictionary);
    }

    @Override
    @Transactional
    public DictionaryDTO updateDictionary(DictionaryDTO dictionaryDTO) {
        Dictionary existingDictionary = dictionaryRepository.findById(dictionaryDTO.getId())
                .orElseThrow(() -> new BusinessException("字典不存在"));

        // 验证字典编码和字典名唯一性（排除当前字典）
        if (!existingDictionary.getDictCode().equals(dictionaryDTO.getDictCode()) && 
            existsByDictCode(dictionaryDTO.getDictCode())) {
            throw new BusinessException("字典编码已存在：" + dictionaryDTO.getDictCode());
        }
        if (!existingDictionary.getDictName().equals(dictionaryDTO.getDictName()) && 
            existsByDictName(dictionaryDTO.getDictName())) {
            throw new BusinessException("字典名称已存在：" + dictionaryDTO.getDictName());
        }

        // 更新字典基本信息
        existingDictionary.setDictCode(dictionaryDTO.getDictCode());
        existingDictionary.setDictName(dictionaryDTO.getDictName());
        existingDictionary.setDictType(dictionaryDTO.getDictType());
        existingDictionary.setStatus(dictionaryDTO.getStatus());
        existingDictionary.setRemark(dictionaryDTO.getRemark());

        Dictionary savedDictionary = dictionaryRepository.save(existingDictionary);
        return convertToDTO(savedDictionary);
    }

    @Override
    @Transactional
    public boolean enableDictionary(Long dictionaryId) {
        return updateDictionaryStatus(dictionaryId, 1);
    }

    @Override
    @Transactional
    public boolean disableDictionary(Long dictionaryId) {
        return updateDictionaryStatus(dictionaryId, 0);
    }

    private boolean updateDictionaryStatus(Long dictionaryId, Integer status) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new BusinessException("字典不存在"));

        dictionary.setStatus(status);
        dictionaryRepository.save(dictionary);
        return true;
    }

    @Override
    public Optional<DictionaryDTO> findDictionaryWithItems(String dictCode) {
        Optional<Dictionary> dictionaryOpt = dictionaryRepository.findByDictCode(dictCode);
        if (dictionaryOpt.isEmpty()) {
            return Optional.empty();
        }

        Dictionary dictionary = dictionaryOpt.get();
        DictionaryDTO dictionaryDTO = convertToDTO(dictionary);

        // 填充字典项信息
        List<DictionaryItem> items = dictionaryItemRepository.findByDictionaryId(dictionary.getId());
        List<DictionaryItemDTO> itemDTOs = items.stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
        dictionaryDTO.setItems(itemDTOs);
        dictionaryDTO.setItemCount(items.size());

        return Optional.of(dictionaryDTO);
    }

    @Override
    public Map<String, String> getDictCodeNameMap() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        return dictionaries.stream()
                .collect(Collectors.toMap(
                        Dictionary::getDictCode,
                        Dictionary::getDictName,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    public Map<String, String> getItemValueLabelMap(String dictCode) {
        List<DictionaryItem> items = dictionaryItemRepository.findByDictCode(dictCode);
        return items.stream()
                .collect(Collectors.toMap(
                        DictionaryItem::getItemValue,
                        DictionaryItem::getItemLabel,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    public Map<String, String> getActiveItemValueLabelMap(String dictCode) {
        List<DictionaryItem> items = dictionaryItemRepository.findByDictCodeAndStatus(dictCode, 1);
        return items.stream()
                .collect(Collectors.toMap(
                        DictionaryItem::getItemValue,
                        DictionaryItem::getItemLabel,
                        (existing, replacement) -> existing
                ));
    }

    @Override
    public Optional<DictionaryItemDTO> findItemByItemCode(String itemCode) {
        return dictionaryItemRepository.findByItemCode(itemCode)
                .map(this::convertItemToDTO);
    }

    @Override
    public List<DictionaryItemDTO> findItemsByDictionaryId(Long dictionaryId) {
        return dictionaryItemRepository.findByDictionaryId(dictionaryId).stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DictionaryItemDTO> findItemsByDictCode(String dictCode) {
        return dictionaryItemRepository.findByDictCode(dictCode).stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DictionaryItemDTO> findItemByDictCodeAndItemValue(String dictCode, String itemValue) {
        return dictionaryItemRepository.findByDictCodeAndItemValue(dictCode, itemValue)
                .map(this::convertItemToDTO);
    }

    @Override
    @Transactional
    public DictionaryItemDTO createDictionaryItem(DictionaryItemDTO dictionaryItemDTO) {
        // 查找所属字典
        Dictionary dictionary = dictionaryRepository.findById(dictionaryItemDTO.getDictionaryId())
                .orElseThrow(() -> new BusinessException("字典不存在：" + dictionaryItemDTO.getDictionaryId()));

        // 转换为实体
        DictionaryItem dictionaryItem = convertItemToEntity(dictionaryItemDTO);
        dictionaryItem.setDictionary(dictionary);

        DictionaryItem savedItem = dictionaryItemRepository.save(dictionaryItem);
        return convertItemToDTO(savedItem);
    }

    @Override
    @Transactional
    public List<DictionaryItemDTO> createDictionaryItems(String dictCode, List<DictionaryItemDTO> items) {
        // 查找所属字典
        Dictionary dictionary = dictionaryRepository.findByDictCode(dictCode)
                .orElseThrow(() -> new BusinessException("字典不存在：" + dictCode));

        // 转换为实体并保存
        List<DictionaryItem> dictionaryItems = items.stream()
                .map(itemDTO -> {
                    DictionaryItem item = convertItemToEntity(itemDTO);
                    item.setDictionary(dictionary);
                    return item;
                })
                .collect(Collectors.toList());

        List<DictionaryItem> savedItems = dictionaryItemRepository.saveAll(dictionaryItems);
        return savedItems.stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DictionaryItemDTO updateDictionaryItem(DictionaryItemDTO dictionaryItemDTO) {
        DictionaryItem existingItem = dictionaryItemRepository.findById(dictionaryItemDTO.getId())
                .orElseThrow(() -> new BusinessException("字典项不存在"));

        // 更新字典项基本信息
        existingItem.setItemCode(dictionaryItemDTO.getItemCode());
        existingItem.setItemValue(dictionaryItemDTO.getItemValue());
        existingItem.setItemLabel(dictionaryItemDTO.getItemLabel());
        existingItem.setSortOrder(dictionaryItemDTO.getSortOrder());
        existingItem.setStatus(dictionaryItemDTO.getStatus());
        existingItem.setRemark(dictionaryItemDTO.getRemark());

        DictionaryItem savedItem = dictionaryItemRepository.save(existingItem);
        return convertItemToDTO(savedItem);
    }

    @Override
    @Transactional
    public boolean enableDictionaryItem(Long itemId) {
        return updateDictionaryItemStatus(itemId, 1);
    }

    @Override
    @Transactional
    public boolean disableDictionaryItem(Long itemId) {
        return updateDictionaryItemStatus(itemId, 0);
    }

    @Override
    @Transactional
    public boolean deleteDictionaryItem(Long itemId) {
        if (!dictionaryItemRepository.existsById(itemId)) {
            throw new BusinessException("字典项不存在");
        }

        dictionaryItemRepository.deleteById(itemId);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAllDictionaryItems(String dictCode) {
        Dictionary dictionary = dictionaryRepository.findByDictCode(dictCode)
                .orElseThrow(() -> new BusinessException("字典不存在：" + dictCode));

        List<DictionaryItem> items = dictionaryItemRepository.findByDictionaryId(dictionary.getId());
        dictionaryItemRepository.deleteAll(items);
        return true;
    }

    @Override
    @Transactional
    public boolean updateItemsSortOrder(List<DictionaryItemDTO> items) {
        for (DictionaryItemDTO itemDTO : items) {
            DictionaryItem item = dictionaryItemRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new BusinessException("字典项不存在：" + itemDTO.getId()));

            item.setSortOrder(itemDTO.getSortOrder());
            dictionaryItemRepository.save(item);
        }
        return true;
    }

    @Override
    public Integer getItemCount(String dictCode) {
        Dictionary dictionary = dictionaryRepository.findByDictCode(dictCode)
                .orElseThrow(() -> new BusinessException("字典不存在：" + dictCode));

        List<DictionaryItem> items = dictionaryItemRepository.findByDictionaryId(dictionary.getId());
        return items.size();
    }

    private boolean updateDictionaryItemStatus(Long itemId, Integer status) {
        DictionaryItem item = dictionaryItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("字典项不存在"));

        item.setStatus(status);
        dictionaryItemRepository.save(item);
        return true;
    }

    /**
     * 将实体转换为DTO
     *
     * @param dictionary 字典实体
     * @return 字典DTO
     */
    private DictionaryDTO convertToDTO(Dictionary dictionary) {
        DictionaryDTO dictionaryDTO = new DictionaryDTO().convertFrom(dictionary);

        // 设置是否为系统字典
        dictionaryDTO.setIsSystemDict("system".equals(dictionary.getDictType()));

        // 设置字典项数量
        List<DictionaryItem> items = dictionaryItemRepository.findByDictionaryId(dictionary.getId());
        dictionaryDTO.setItemCount(items.size());

        return dictionaryDTO;
    }

    /**
     * 将DTO转换为实体
     *
     * @param dictionaryDTO 字典DTO
     * @return 字典实体
     */
    private Dictionary convertToEntity(DictionaryDTO dictionaryDTO) {
        return new Dictionary().convertFrom(dictionaryDTO);
    }

    /**
     * 将字典项实体转换为DTO
     *
     * @param item 字典项实体
     * @return 字典项DTO
     */
    private DictionaryItemDTO convertItemToDTO(DictionaryItem item) {
        DictionaryItemDTO itemDTO = new DictionaryItemDTO().convertFrom(item);

        // 设置字典信息
        itemDTO.setDictionaryId(item.getDictionary().getId());
        itemDTO.setDictCode(item.getDictionary().getDictCode());
        itemDTO.setDictName(item.getDictionary().getDictName());

        return itemDTO;
    }

    /**
     * 将字典项DTO转换为实体
     *
     * @param itemDTO 字典项DTO
     * @return 字典项实体
     */
    private DictionaryItem convertItemToEntity(DictionaryItemDTO itemDTO) {
        return new DictionaryItem().convertFrom(itemDTO);
    }
}