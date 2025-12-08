package net.ijupiter.trading.core.system.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.exceptions.BusinessException;
import net.ijupiter.trading.common.utils.StringUtils;
import net.ijupiter.trading.api.system.dtos.ParameterDTO;
import net.ijupiter.trading.core.system.entities.Parameter;
import net.ijupiter.trading.core.system.repositories.ParameterRepository;
import net.ijupiter.trading.api.system.services.ParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统参数服务实现类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ParameterDTO save(ParameterDTO dto) {
        Parameter parameter = convertToEntity(dto);
        Parameter savedParameter = parameterRepository.save(parameter);
        return convertToDTO(savedParameter);
    }

    @Override
    public Optional<ParameterDTO> findById(Long id) {
        return parameterRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<ParameterDTO> findAll() {
        return parameterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findAllById(List<Long> ids) {
        return parameterRepository.findAllById(ids).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return parameterRepository.existsById(id);
    }

    @Override
    public long count() {
        return parameterRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        parameterRepository.deleteById(id);
    }

    @Override
    public void delete(ParameterDTO dto) {
        Parameter parameter = parameterRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("参数不存在"));
        parameterRepository.delete(parameter);
    }

    @Override
    public void deleteAll() {
        parameterRepository.deleteAll();
    }

    @Override
    public ParameterDTO saveAndFlush(ParameterDTO dto) {
        Parameter parameter = convertToEntity(dto);
        Parameter savedParameter = parameterRepository.saveAndFlush(parameter);
        return convertToDTO(savedParameter);
    }

    @Override
    public List<ParameterDTO> saveAll(List<ParameterDTO> dtos) {
        List<Parameter> parameters = dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        List<Parameter> savedParameters = parameterRepository.saveAll(parameters);
        return savedParameters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ParameterDTO> findByParamCode(String paramCode) {
        return parameterRepository.findByParamCode(paramCode)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<ParameterDTO> findByParamName(String paramName) {
        return parameterRepository.findByParamName(paramName)
                .map(this::convertToDTO);
    }

    @Override
    public List<ParameterDTO> findByParamGroup(String paramGroup) {
        return parameterRepository.findByParamGroup(paramGroup).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByParamType(String paramType) {
        return parameterRepository.findByParamType(paramType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByStatus(Integer status) {
        return parameterRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByParamGroupAndStatus(String paramGroup, Integer status) {
        return parameterRepository.findByParamGroupAndStatus(paramGroup, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByIsSystem(Integer isSystem) {
        return parameterRepository.findByIsSystem(isSystem).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByIsEditable(Integer isEditable) {
        return parameterRepository.findByIsEditable(isEditable).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByParamCode(String paramCode) {
        return parameterRepository.existsByParamCode(paramCode);
    }

    @Override
    public boolean existsByParamName(String paramName) {
        return parameterRepository.existsByParamName(paramName);
    }

    @Override
    public List<ParameterDTO> searchByKeyword(String keyword) {
        return parameterRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder) {
        return parameterRepository.findBySortOrderBetween(minSortOrder, maxSortOrder).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findByParamGroupOrderBySortOrder(String paramGroup) {
        return parameterRepository.findByParamGroupOrderBySortOrder(paramGroup).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findSystemParameters() {
        return parameterRepository.findSystemParameters().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterDTO> findActiveSystemParameters(String paramGroup) {
        return parameterRepository.findActiveSystemParameters(paramGroup).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByParamGroup(String paramGroup) {
        return parameterRepository.countByParamGroup(paramGroup);
    }

    @Override
    public List<String> findAllParamGroups() {
        return parameterRepository.findAllParamGroups();
    }

    @Override
    @Transactional
    public ParameterDTO createParameter(ParameterDTO parameterDTO) {
        // 验证参数编码唯一性
        if (existsByParamCode(parameterDTO.getParamCode())) {
            throw new BusinessException("参数编码已存在：" + parameterDTO.getParamCode());
        }

        // 验证参数名称唯一性
        if (existsByParamName(parameterDTO.getParamName())) {
            throw new BusinessException("参数名称已存在：" + parameterDTO.getParamName());
        }

        // 设置默认值
        if (parameterDTO.getStatus() == null) {
            parameterDTO.setStatus(1);
        }
        if (parameterDTO.getIsSystem() == null) {
            parameterDTO.setIsSystem(0);
        }
        if (parameterDTO.getIsEditable() == null) {
            parameterDTO.setIsEditable(1);
        }
        if (parameterDTO.getSortOrder() == null) {
            parameterDTO.setSortOrder(0);
        }
        if (StringUtils.isEmpty(parameterDTO.getParamType())) {
            parameterDTO.setParamType("STRING");
        }

        // 如果未设置参数值，使用默认值
        if (StringUtils.isEmpty(parameterDTO.getParamValue()) && 
            StringUtils.isNotEmpty(parameterDTO.getDefaultValue())) {
            parameterDTO.setParamValue(parameterDTO.getDefaultValue());
        }

        Parameter parameter = convertToEntity(parameterDTO);
        Parameter savedParameter = parameterRepository.save(parameter);
        return convertToDTO(savedParameter);
    }

    @Override
    @Transactional
    public ParameterDTO updateParameter(ParameterDTO parameterDTO) {
        Parameter existingParameter = parameterRepository.findById(parameterDTO.getId())
                .orElseThrow(() -> new BusinessException("参数不存在"));

        // 验证参数编码唯一性（排除当前参数）
        if (!existingParameter.getParamCode().equals(parameterDTO.getParamCode()) && 
            existsByParamCode(parameterDTO.getParamCode())) {
            throw new BusinessException("参数编码已存在：" + parameterDTO.getParamCode());
        }

        // 验证参数名称唯一性（排除当前参数）
        if (!existingParameter.getParamName().equals(parameterDTO.getParamName()) && 
            existsByParamName(parameterDTO.getParamName())) {
            throw new BusinessException("参数名称已存在：" + parameterDTO.getParamName());
        }

        // 系统参数不允许修改部分关键字段
        if (existingParameter.getIsSystem() == 1) {
            if (!existingParameter.getParamCode().equals(parameterDTO.getParamCode())) {
                throw new BusinessException("系统参数不允许修改参数编码");
            }
            if (!existingParameter.getParamType().equals(parameterDTO.getParamType())) {
                throw new BusinessException("系统参数不允许修改参数类型");
            }
        }

        // 更新参数信息
        existingParameter.setParamCode(parameterDTO.getParamCode());
        existingParameter.setParamName(parameterDTO.getParamName());
        existingParameter.setParamValue(parameterDTO.getParamValue());
        existingParameter.setParamType(parameterDTO.getParamType());
        existingParameter.setDefaultValue(parameterDTO.getDefaultValue());
        existingParameter.setParamGroup(parameterDTO.getParamGroup());
        existingParameter.setDescription(parameterDTO.getDescription());
        existingParameter.setIsEditable(parameterDTO.getIsEditable());
        existingParameter.setSortOrder(parameterDTO.getSortOrder());
        existingParameter.setStatus(parameterDTO.getStatus());
        existingParameter.setLastModifier(parameterDTO.getLastModifier());
        existingParameter.setValidationRule(parameterDTO.getValidationRule());
        existingParameter.setOptions(parameterDTO.getOptions());

        Parameter savedParameter = parameterRepository.save(existingParameter);
        return convertToDTO(savedParameter);
    }

    @Override
    public String getParamValue(String paramCode) {
        Optional<Parameter> parameterOpt = parameterRepository.findByParamCode(paramCode);
        if (parameterOpt.isPresent()) {
            Parameter parameter = parameterOpt.get();
            return parameter.getParamValue();
        }
        return null;
    }

    @Override
    public String getParamValue(String paramCode, String defaultValue) {
        String value = getParamValue(paramCode);
        return value != null ? value : defaultValue;
    }

    @Override
    public Integer getIntegerParamValue(String paramCode) {
        String value = getParamValue(paramCode);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                log.warn("参数值不是有效的整数: paramCode={}, value={}", paramCode, value);
            }
        }
        return null;
    }

    @Override
    public Integer getIntegerParamValue(String paramCode, Integer defaultValue) {
        Integer value = getIntegerParamValue(paramCode);
        return value != null ? value : defaultValue;
    }

    @Override
    public Boolean getBooleanParamValue(String paramCode) {
        String value = getParamValue(paramCode);
        if (value != null) {
            return "true".equalsIgnoreCase(value) || "1".equals(value) || "yes".equalsIgnoreCase(value);
        }
        return null;
    }

    @Override
    public Boolean getBooleanParamValue(String paramCode, Boolean defaultValue) {
        Boolean value = getBooleanParamValue(paramCode);
        return value != null ? value : defaultValue;
    }

    @Override
    public Double getDoubleParamValue(String paramCode) {
        String value = getParamValue(paramCode);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                log.warn("参数值不是有效的数字: paramCode={}, value={}", paramCode, value);
            }
        }
        return null;
    }

    @Override
    public Double getDoubleParamValue(String paramCode, Double defaultValue) {
        Double value = getDoubleParamValue(paramCode);
        return value != null ? value : defaultValue;
    }

    @Override
    public Map<String, String> getParamGroupMap(String paramGroup) {
        List<Parameter> parameters = parameterRepository.findByParamGroup(paramGroup);
        return parameters.stream()
                .collect(Collectors.toMap(
                    Parameter::getParamCode,
                    Parameter::getParamValue,
                    (existing, replacement) -> existing
                ));
    }

    @Override
    public Map<String, ParameterDTO> getParamGroupDTOMap(String paramGroup) {
        List<Parameter> parameters = parameterRepository.findByParamGroup(paramGroup);
        return parameters.stream()
                .collect(Collectors.toMap(
                    Parameter::getParamCode,
                    this::convertToDTO,
                    (existing, replacement) -> existing
                ));
    }

    @Override
    public Map<String, String> getActiveParamGroupMap(String paramGroup) {
        List<Parameter> parameters = parameterRepository.findByParamGroupAndStatus(paramGroup, 1);
        return parameters.stream()
                .collect(Collectors.toMap(
                    Parameter::getParamCode,
                    Parameter::getParamValue,
                    (existing, replacement) -> existing
                ));
    }

    @Override
    @Transactional
    public boolean batchUpdateParamValues(Map<String, String> paramValueMap) {
        if (paramValueMap == null || paramValueMap.isEmpty()) {
            return true;
        }

        for (Map.Entry<String, String> entry : paramValueMap.entrySet()) {
            String paramCode = entry.getKey();
            String paramValue = entry.getValue();

            Optional<Parameter> parameterOpt = parameterRepository.findByParamCode(paramCode);
            if (parameterOpt.isPresent()) {
                Parameter parameter = parameterOpt.get();
                
                // 检查是否可编辑
                if (parameter.getIsEditable() == 0) {
                    log.warn("参数不可编辑，跳过更新: {}", paramCode);
                    continue;
                }

                // 验证参数值
                if (validateParameterValue(parameter.getId(), paramValue)) {
                    parameter.setParamValue(paramValue);
                    parameterRepository.save(parameter);
                } else {
                    log.error("参数值验证失败，跳过更新: paramCode={}, value={}", paramCode, paramValue);
                    return false;
                }
            } else {
                log.warn("参数不存在，跳过更新: {}", paramCode);
            }
        }

        return true;
    }

    @Override
    @Transactional
    public boolean enableParameter(Long paramId) {
        return updateParameterStatus(paramId, 1);
    }

    @Override
    @Transactional
    public boolean disableParameter(Long paramId) {
        return updateParameterStatus(paramId, 0);
    }

    private boolean updateParameterStatus(Long paramId, Integer status) {
        Parameter parameter = parameterRepository.findById(paramId)
                .orElseThrow(() -> new BusinessException("参数不存在"));

        // 系统参数不允许禁用
        if (parameter.getIsSystem() == 1 && status == 0) {
            throw new BusinessException("系统参数不允许禁用");
        }

        parameter.setStatus(status);
        parameterRepository.save(parameter);
        return true;
    }

    @Override
    @Transactional
    public boolean resetToDefaultValue(Long paramId) {
        Parameter parameter = parameterRepository.findById(paramId)
                .orElseThrow(() -> new BusinessException("参数不存在"));

        if (StringUtils.isEmpty(parameter.getDefaultValue())) {
            throw new BusinessException("参数没有设置默认值");
        }

        parameter.setParamValue(parameter.getDefaultValue());
        parameterRepository.save(parameter);
        return true;
    }

    @Override
    public boolean validateParameterValue(Long paramId, String value) {
        Parameter parameter = parameterRepository.findById(paramId)
                .orElseThrow(() -> new BusinessException("参数不存在"));

        String validationRule = parameter.getValidationRule();
        if (StringUtils.isEmpty(validationRule)) {
            return true; // 没有验证规则则认为验证通过
        }

        try {
            return value.matches(validationRule);
        } catch (Exception e) {
            log.error("验证参数值时发生错误: paramId={}, value={}, rule={}", paramId, value, validationRule, e);
            return false;
        }
    }

    @Override
    public String exportParameters(String paramGroup) {
        List<Parameter> parameters;
        if (StringUtils.isEmpty(paramGroup)) {
            parameters = parameterRepository.findAll();
        } else {
            parameters = parameterRepository.findByParamGroup(paramGroup);
        }

        List<ParameterDTO> parameterDTOs = parameters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        try {
            return objectMapper.writeValueAsString(parameterDTOs);
        } catch (JsonProcessingException e) {
            log.error("导出参数配置失败", e);
            throw new BusinessException("导出参数配置失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String importParameters(String configJson, Boolean isOverride) {
        try {
            List<ParameterDTO> parameterDTOs = objectMapper.readValue(configJson, new TypeReference<List<ParameterDTO>>() {});
            if (parameterDTOs == null || parameterDTOs.isEmpty()) {
                return "导入的参数配置为空";
            }

            int successCount = 0;
            int skipCount = 0;
            int errorCount = 0;
            StringBuilder errorMsg = new StringBuilder();

            for (ParameterDTO parameterDTO : parameterDTOs) {
                try {
                    Optional<Parameter> existingParamOpt = parameterRepository.findByParamCode(parameterDTO.getParamCode());
                    
                    if (existingParamOpt.isPresent()) {
                        // 参数已存在
                        if (Boolean.TRUE.equals(isOverride)) {
                            // 覆盖更新
                            Parameter existingParam = existingParamOpt.get();
                            parameterDTO.setId(existingParam.getId());
                            updateParameter(parameterDTO);
                            successCount++;
                        } else {
                            // 跳过
                            skipCount++;
                        }
                    } else {
                        // 新建参数
                        createParameter(parameterDTO);
                        successCount++;
                    }
                } catch (Exception e) {
                    errorCount++;
                    errorMsg.append("导入参数失败: ").append(parameterDTO.getParamCode())
                            .append(", 错误: ").append(e.getMessage()).append("; ");
                }
            }

            StringBuilder resultMsg = new StringBuilder();
            resultMsg.append("导入完成。成功: ").append(successCount);
            if (skipCount > 0) {
                resultMsg.append(", 跳过: ").append(skipCount);
            }
            if (errorCount > 0) {
                resultMsg.append(", 失败: ").append(errorCount);
                resultMsg.append(", 错误信息: ").append(errorMsg.toString());
            }

            return resultMsg.toString();
        } catch (JsonProcessingException e) {
            log.error("解析导入参数配置失败", e);
            throw new BusinessException("解析导入参数配置失败: " + e.getMessage());
        }
    }

    /**
     * 将实体转换为DTO
     *
     * @param parameter 参数实体
     * @return 参数DTO
     */
    private ParameterDTO convertToDTO(Parameter parameter) {
        return new ParameterDTO().convertFrom(parameter);
    }

    /**
     * 将DTO转换为实体
     *
     * @param parameterDTO 参数DTO
     * @return 参数实体
     */
    private Parameter convertToEntity(ParameterDTO parameterDTO) {
        return new Parameter().convertFrom(parameterDTO);
    }
}