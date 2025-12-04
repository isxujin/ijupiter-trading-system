package net.ijupiter.trading.core.system.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.system.api.dto.DataDictDTO;
import net.ijupiter.trading.system.api.dto.DataDictItemDTO;
import net.ijupiter.trading.system.api.dto.DataDictQueryDTO;
import net.ijupiter.trading.system.api.services.DataDictService;
import net.ijupiter.trading.core.system.entities.DataDict;
import net.ijupiter.trading.core.system.repositories.DataDictRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 数据字典服务实现
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataDictServiceImpl implements DataDictService {

    private final DataDictRepository dataDictRepository;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 实体转DTO
     */
    private DataDictDTO convertToDTO(DataDict dataDict) {
        DataDictDTO dto = new DataDictDTO();
        BeanUtils.copyProperties(dataDict, dto);

        // 时间格式化
        if (dataDict.getCreateTime() != null) {
            dto.setCreateTime(dataDict.getCreateTime().format(DATE_TIME_FORMATTER));
        }
        if (dataDict.getUpdateTime() != null) {
            dto.setUpdateTime(dataDict.getUpdateTime().format(DATE_TIME_FORMATTER));
        }

        return dto;
    }

    @Override
    public List<DataDictDTO> queryDataDicts(DataDictQueryDTO queryDTO) {
        return List.of();
    }

    @Override
    public DataDictDTO getDataDictById(String dictId) {
        return null;
    }

    @Override
    public DataDictDTO getDataDictByCode(String dictCode) {
        return null;
    }

    @Override
    public DataDictDTO createDataDict(DataDictDTO dictDTO) {
        return null;
    }

    @Override
    public DataDictDTO updateDataDict(DataDictDTO dictDTO) {
        return null;
    }

    @Override
    public Boolean deleteDataDict(String dictId) {
        return null;
    }

    @Override
    public Integer batchDeleteDataDicts(List<String> dictIds) {
        return 0;
    }

    @Override
    public List<DataDictItemDTO> queryDataDictItems(String dictId) {
        return List.of();
    }

    @Override
    public DataDictItemDTO getDataDictItemById(String itemId) {
        return null;
    }

    @Override
    public DataDictItemDTO createDataDictItem(DataDictItemDTO itemDTO) {
        return null;
    }

    @Override
    public DataDictItemDTO updateDataDictItem(DataDictItemDTO itemDTO) {
        return null;
    }

    @Override
    public Boolean deleteDataDictItem(String itemId) {
        return null;
    }

    @Override
    public Integer batchDeleteDataDictItems(List<String> itemIds) {
        return 0;
    }
}