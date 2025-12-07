package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统配置Repository
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String>, JpaSpecificationExecutor<SystemConfig> {

    /**
     * 根据配置键查询系统配置
     *
     * @param configKey 配置键
     * @return 系统配置
     */
    SystemConfig findByConfigKey(String configKey);

    /**
     * 根据配置类型查询系统配置
     *
     * @param configType 配置类型
     * @return 系统配置列表
     */
    List<SystemConfig> findByConfigType(String configType);

    /**
     * 根据配置键和配置类型查询系统配置
     *
     * @param configKey  配置键
     * @param configType 配置类型
     * @return 系统配置
     */
    SystemConfig findByConfigKeyAndConfigType(String configKey, String configType);

    /**
     * 根据配置键模糊查询系统配置
     *
     * @param configKey 配置键
     * @return 系统配置列表
     */
    @Query("SELECT sc FROM SystemConfig sc WHERE sc.configKey LIKE %:configKey%")
    List<SystemConfig> findByConfigKeyContaining(@Param("configKey") String configKey);

    /**
     * 根据是否系统内置配置查询
     *
     * @param isSystem 是否系统内置配置
     * @return 系统配置列表
     */
    List<SystemConfig> findByIsSystem(Boolean isSystem);
}