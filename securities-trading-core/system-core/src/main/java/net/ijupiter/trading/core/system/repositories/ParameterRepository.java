package net.ijupiter.trading.core.system.repositories;

import net.ijupiter.trading.core.system.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 系统参数数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    /**
     * 根据参数编码查找参数
     *
     * @param paramCode 参数编码
     * @return 参数对象
     */
    Optional<Parameter> findByParamCode(String paramCode);

    /**
     * 根据参数名称查找参数
     *
     * @param paramName 参数名称
     * @return 参数对象
     */
    Optional<Parameter> findByParamName(String paramName);

    /**
     * 根据参数分组查找参数列表
     *
     * @param paramGroup 参数分组
     * @return 参数列表
     */
    List<Parameter> findByParamGroup(String paramGroup);

    /**
     * 根据参数类型查找参数列表
     *
     * @param paramType 参数类型
     * @return 参数列表
     */
    List<Parameter> findByParamType(String paramType);

    /**
     * 根据状态查找参数列表
     *
     * @param status 状态
     * @return 参数列表
     */
    List<Parameter> findByStatus(Integer status);

    /**
     * 根据参数分组和状态查找参数列表
     *
     * @param paramGroup 参数分组
     * @param status 状态
     * @return 参数列表
     */
    List<Parameter> findByParamGroupAndStatus(String paramGroup, Integer status);

    /**
     * 根据是否系统参数查找参数列表
     *
     * @param isSystem 是否系统参数
     * @return 参数列表
     */
    List<Parameter> findByIsSystem(Integer isSystem);

    /**
     * 根据是否可编辑查找参数列表
     *
     * @param isEditable 是否可编辑
     * @return 参数列表
     */
    List<Parameter> findByIsEditable(Integer isEditable);

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
     * @return 参数列表
     */
    @Query("SELECT p FROM Parameter p WHERE p.paramName LIKE %:keyword% OR p.paramCode LIKE %:keyword% " +
           "OR p.description LIKE %:keyword%")
    List<Parameter> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 根据排序号范围查找参数列表
     *
     * @param minSortOrder 最小排序号
     * @param maxSortOrder 最大排序号
     * @return 参数列表
     */
    @Query("SELECT p FROM Parameter p WHERE p.sortOrder BETWEEN :minSortOrder AND :maxSortOrder ORDER BY p.sortOrder")
    List<Parameter> findBySortOrderBetween(@Param("minSortOrder") Integer minSortOrder, 
                                           @Param("maxSortOrder") Integer maxSortOrder);

    /**
     * 根据参数分组查找参数列表，按排序号排序
     *
     * @param paramGroup 参数分组
     * @return 参数列表
     */
    @Query("SELECT p FROM Parameter p WHERE p.paramGroup = :paramGroup ORDER BY p.sortOrder")
    List<Parameter> findByParamGroupOrderBySortOrder(@Param("paramGroup") String paramGroup);

    /**
     * 查找系统参数列表
     *
     * @return 系统参数列表
     */
    @Query("SELECT p FROM Parameter p WHERE p.isSystem = 1 AND p.status = 1")
    List<Parameter> findSystemParameters();

    /**
     * 查找启用状态的系统参数列表
     *
     * @param paramGroup 参数分组（可选）
     * @return 启用状态的系统参数列表
     */
    @Query("SELECT p FROM Parameter p WHERE p.isSystem = 1 AND p.status = 1 " +
           "AND (:paramGroup IS NULL OR p.paramGroup = :paramGroup) ORDER BY p.sortOrder")
    List<Parameter> findActiveSystemParameters(@Param("paramGroup") String paramGroup);

    /**
     * 统计指定分组的参数数量
     *
     * @param paramGroup 参数分组
     * @return 参数数量
     */
    @Query("SELECT COUNT(p) FROM Parameter p WHERE p.paramGroup = :paramGroup")
    Long countByParamGroup(@Param("paramGroup") String paramGroup);

    /**
     * 获取所有参数分组
     *
     * @return 参数分组列表
     */
    @Query("SELECT DISTINCT p.paramGroup FROM Parameter p WHERE p.paramGroup IS NOT NULL ORDER BY p.paramGroup")
    List<String> findAllParamGroups();
}