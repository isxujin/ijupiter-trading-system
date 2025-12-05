package net.ijupiter.trading.system.api.services;

import net.ijupiter.trading.system.api.dtos.SystemConfigDTO;
import net.ijupiter.trading.system.api.dtos.SystemConfigQueryDTO;

import java.util.List;

/**
 * 系统配置服务接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
public interface SystemConfigService {

    /**
     * 查询系统配置列表
     *
     * @param queryDTO 查询条件
     * @return 系统配置列表
     */
    List<SystemConfigDTO> querySystemConfigs(SystemConfigQueryDTO queryDTO);

    /**
     * 根据ID查询系统配置
     *
     * @param configId 配置ID
     * @return 系统配置
     */
    SystemConfigDTO getSystemConfigById(String configId);

    /**
     * 根据配置键查询系统配置
     *
     * @param configKey 配置键
     * @return 系统配置
     */
    SystemConfigDTO getSystemConfigByKey(String configKey);

    /**
     * 新增系统配置
     *
     * @param configDTO 系统配置
     * @return 新增的系统配置
     */
    SystemConfigDTO createSystemConfig(SystemConfigDTO configDTO);

    /**
     * 更新系统配置
     *
     * @param configDTO 系统配置
     * @return 更新后的系统配置
     */
    SystemConfigDTO updateSystemConfig(SystemConfigDTO configDTO);

    /**
     * 删除系统配置
     *
     * @param configId 配置ID
     * @return 是否删除成功
     */
    Boolean deleteSystemConfig(String configId);

    /**
     * 批量删除系统配置
     *
     * @param configIds 配置ID列表
     * @return 删除成功的数量
     */
    Integer batchDeleteSystemConfigs(List<String> configIds);
}