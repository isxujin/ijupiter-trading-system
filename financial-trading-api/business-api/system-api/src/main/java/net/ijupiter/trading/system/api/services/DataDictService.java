package net.ijupiter.trading.system.api.services;

import net.ijupiter.trading.system.api.dto.DataDictDTO;
import net.ijupiter.trading.system.api.dto.DataDictQueryDTO;
import net.ijupiter.trading.system.api.dto.DataDictItemDTO;

import java.util.List;

/**
 * 数据字典服务接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
public interface DataDictService {

    /**
     * 查询数据字典列表
     *
     * @param queryDTO 查询条件
     * @return 数据字典列表
     */
    List<DataDictDTO> queryDataDicts(DataDictQueryDTO queryDTO);

    /**
     * 根据ID查询数据字典
     *
     * @param dictId 字典ID
     * @return 数据字典
     */
    DataDictDTO getDataDictById(String dictId);

    /**
     * 根据编码查询数据字典
     *
     * @param dictCode 字典编码
     * @return 数据字典
     */
    DataDictDTO getDataDictByCode(String dictCode);

    /**
     * 新增数据字典
     *
     * @param dictDTO 数据字典
     * @return 新增的数据字典
     */
    DataDictDTO createDataDict(DataDictDTO dictDTO);

    /**
     * 更新数据字典
     *
     * @param dictDTO 数据字典
     * @return 更新后的数据字典
     */
    DataDictDTO updateDataDict(DataDictDTO dictDTO);

    /**
     * 删除数据字典
     *
     * @param dictId 字典ID
     * @return 是否删除成功
     */
    Boolean deleteDataDict(String dictId);

    /**
     * 批量删除数据字典
     *
     * @param dictIds 字典ID列表
     * @return 删除成功的数量
     */
    Integer batchDeleteDataDicts(List<String> dictIds);

    /**
     * 查询数据字典项列表
     *
     * @param dictId 字典ID
     * @return 数据字典项列表
     */
    List<DataDictItemDTO> queryDataDictItems(String dictId);

    /**
     * 根据ID查询数据字典项
     *
     * @param itemId 字典项ID
     * @return 数据字典项
     */
    DataDictItemDTO getDataDictItemById(String itemId);

    /**
     * 新增数据字典项
     *
     * @param itemDTO 数据字典项
     * @return 新增的数据字典项
     */
    DataDictItemDTO createDataDictItem(DataDictItemDTO itemDTO);

    /**
     * 更新数据字典项
     *
     * @param itemDTO 数据字典项
     * @return 更新后的数据字典项
     */
    DataDictItemDTO updateDataDictItem(DataDictItemDTO itemDTO);

    /**
     * 删除数据字典项
     *
     * @param itemId 字典项ID
     * @return 是否删除成功
     */
    Boolean deleteDataDictItem(String itemId);

    /**
     * 批量删除数据字典项
     *
     * @param itemIds 字典项ID列表
     * @return 删除成功的数量
     */
    Integer batchDeleteDataDictItems(List<String> itemIds);
}