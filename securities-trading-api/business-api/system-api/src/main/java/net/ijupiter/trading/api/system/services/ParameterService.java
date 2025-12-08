package net.ijupiter.trading.api.system.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.system.dtos.ParameterDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统参数服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface ParameterService extends BaseService<ParameterDTO, Long> {

    /**
     * 根据参数编码查找参数
     *
     * @param paramCode 参数编码
     * @return 参数DTO
     */
    Optional<ParameterDTO> findByParamCode(String paramCode);

    /**
     * 根据参数名称查找参数
     *
     * @param paramName 参数名称
     * @return 参数DTO
     */
    Optional<ParameterDTO> findByParamName(String paramName);

    /**
     * 根据参数分组查找参数列表
     *
     * @param paramGroup 参数分组
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByParamGroup(String paramGroup);

    /**
     * 根据参数类型查找参数列表
     *
     * @param paramType 参数类型
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByParamType(String paramType);

    /**
     * 根据状态查找参数列表
     *
     * @param status 状态
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByStatus(Integer status);

    /**
     * 根据参数分组和状态查找参数列表
     *
     * @param paramGroup 参数分组
     * @param status 状态
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByParamGroupAndStatus(String paramGroup, Integer status);

    /**
     * 根据是否系统参数查找参数列表
     *
     * @param isSystem 是否系统参数
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByIsSystem(Integer isSystem);

    /**
     * 根据是否可编辑查找参数列表
     *
     * @param isEditable 是否可编辑
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByIsEditable(Integer isEditable);

    /**
     * 检查参数编码是否存在
     *
     * @param paramCode 参数编码
     * @return 是否存在
     */
    boolean existsByParamCode(String paramCode);

    /**
     * 检查参数名称是否存在
     *
     * @param paramName 参数名称
     * @return 是否存在
     */
    boolean existsByParamName(String paramName);

    /**
     * 模糊搜索参数（支持参数名称、参数编码、参数描述）
     *
     * @param keyword 关键字
     * @return 参数DTO列表
     */
    List<ParameterDTO> searchByKeyword(String keyword);

    /**
     * 根据排序号范围查找参数列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 参数DTO列表
     */
    List<ParameterDTO> findBySortOrderBetween(Integer minSortOrder, Integer maxSortOrder);

    /**
     * 根据参数分组查找参数列表，按排序号排序
     *
     * @param paramGroup 参数分组
     * @return 参数DTO列表
     */
    List<ParameterDTO> findByParamGroupOrderBySortOrder(String paramGroup);

    /**
     * 查找系统参数列表
     *
     * @return 系统参数DTO列表
     */
    List<ParameterDTO> findSystemParameters();

    /**
     * 查找启用状态的系统参数列表
     *
     * @param paramGroup 参数分组（可选）
     * @return 启用状态的系统参数DTO列表
     */
    List<ParameterDTO> findActiveSystemParameters(String paramGroup);

    /**
     * 统计指定分组的参数数量
     *
     * @param paramGroup 参数分组
     * @return 参数数量
     */
    Long countByParamGroup(String paramGroup);

    /**
     * 获取所有参数分组
     *
     * @return 参数分组列表
     */
    List<String> findAllParamGroups();

    /**
     * 创建参数
     *
     * @param parameterDTO 参数DTO
     * @return 创建后的参数DTO
     */
    ParameterDTO createParameter(ParameterDTO parameterDTO);

    /**
     * 更新参数信息
     *
     * @param parameterDTO 参数DTO
     * @return 更新后的参数DTO
     */
    ParameterDTO updateParameter(ParameterDTO parameterDTO);

    /**
     * 根据参数编码获取参数值
     *
     * @param paramCode 参数编码
     * @return 参数值
     */
    String getParamValue(String paramCode);

    /**
     * 根据参数编码获取参数值，如果不存在则返回默认值
     *
     * @param paramCode 参数编码
     * @param defaultValue 默认值
     * @return 参数值
     */
    String getParamValue(String paramCode, String defaultValue);

    /**
     * 根据参数编码获取Integer类型参数值
     *
     * @param paramCode 参数编码
     * @return Integer类型参数值
     */
    Integer getIntegerParamValue(String paramCode);

    /**
     * 根据参数编码获取Integer类型参数值，如果不存在或转换失败则返回默认值
     *
     * @param paramCode 参数编码
     * @param defaultValue 默认值
     * @return Integer类型参数值
     */
    Integer getIntegerParamValue(String paramCode, Integer defaultValue);

    /**
     * 根据参数编码获取Boolean类型参数值
     *
     * @param paramCode 参数编码
     * @return Boolean类型参数值
     */
    Boolean getBooleanParamValue(String paramCode);

    /**
     * 根据参数编码获取Boolean类型参数值，如果不存在则返回默认值
     *
     * @param paramCode 参数编码
     * @param defaultValue 默认值
     * @return Boolean类型参数值
     */
    Boolean getBooleanParamValue(String paramCode, Boolean defaultValue);

    /**
     * 根据参数编码获取Double类型参数值
     *
     * @param paramCode 参数编码
     * @return Double类型参数值
     */
    Double getDoubleParamValue(String paramCode);

    /**
     * 根据参数编码获取Double类型参数值，如果不存在或转换失败则返回默认值
     *
     * @param paramCode 参数编码
     * @param defaultValue 默认值
     * @return Double类型参数值
     */
    Double getDoubleParamValue(String paramCode, Double defaultValue);

    /**
     * 根据参数分组获取参数映射（编码->值）
     *
     * @param paramGroup 参数分组
     * @return 参数映射
     */
    Map<String, String> getParamGroupMap(String paramGroup);

    /**
     * 根据参数分组获取参数映射（编码->参数DTO）
     *
     * @param paramGroup 参数分组
     * @return 参数映射
     */
    Map<String, ParameterDTO> getParamGroupDTOMap(String paramGroup);

    /**
     * 根据参数分组获取启用的参数映射（编码->值）
     *
     * @param paramGroup 参数分组
     * @return 参数映射
     */
    Map<String, String> getActiveParamGroupMap(String paramGroup);

    /**
     * 批量更新参数值
     *
     * @param paramValueMap 参数值映射（编码->值）
     * @return 是否更新成功
     */
    boolean batchUpdateParamValues(Map<String, String> paramValueMap);

    /**
     * 启用参数
     *
     * @param paramId 参数ID
     * @return 是否启用成功
     */
    boolean enableParameter(Long paramId);

    /**
     * 禁用参数
     *
     * @param paramId 参数ID
     * @return 是否禁用成功
     */
    boolean disableParameter(Long paramId);

    /**
     * 重置参数值为默认值
     *
     * @param paramId 参数ID
     * @return 是否重置成功
     */
    boolean resetToDefaultValue(Long paramId);

    /**
     * 验证参数值是否符合验证规则
     *
     * @param paramId 参数ID
     * @param value 参数值
     * @return 是否验证通过
     */
    boolean validateParameterValue(Long paramId, String value);

    /**
     * 导出参数配置
     *
     * @param paramGroup 参数分组（可选）
     * @return 参数配置JSON字符串
     */
    String exportParameters(String paramGroup);

    /**
     * 导入参数配置
     *
     * @param configJson 参数配置JSON字符串
     * @param isOverride 是否覆盖已存在的参数
     * @return 导入结果信息
     */
    String importParameters(String configJson, Boolean isOverride);
}