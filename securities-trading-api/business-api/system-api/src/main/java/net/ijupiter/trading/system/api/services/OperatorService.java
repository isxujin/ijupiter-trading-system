package net.ijupiter.trading.system.api.services;

import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.system.api.dtos.OperatorDTO;
import net.ijupiter.trading.system.api.dtos.OperatorQueryDTO;

import java.util.List;

/**
 * 操作员服务接口
 * 
 * @author iJupiter
 * @version 1.0.1
 */
public interface OperatorService extends BaseService<OperatorDTO, String> {

    /**
     * 查询操作员列表
     *
     * @param queryDTO 查询条件
     * @return 操作员列表
     */
    List<OperatorDTO> queryOperators(OperatorQueryDTO queryDTO);

    /**
     * 根据ID查询操作员
     *
     * @param operatorId 操作员ID
     * @return 操作员
     */
    OperatorDTO getOperatorById(String operatorId);

    /**
     * 根据登录名查询操作员
     *
     * @param loginName 登录名
     * @return 操作员
     */
    OperatorDTO getOperatorByLoginName(String loginName);

    /**
     * 新增操作员
     *
     * @param operatorDTO 操作员
     * @return 新增的操作员
     */
    OperatorDTO createOperator(OperatorDTO operatorDTO);

    /**
     * 更新操作员
     *
     * @param operatorDTO 操作员
     * @return 更新后的操作员
     */
    OperatorDTO updateOperator(OperatorDTO operatorDTO);

    /**
     * 删除操作员
     *
     * @param operatorId 操作员ID
     * @return 是否删除成功
     */
    Boolean deleteOperator(String operatorId);

    /**
     * 批量删除操作员
     *
     * @param operatorIds 操作员ID列表
     * @return 删除成功的数量
     */
    Integer batchDeleteOperators(List<String> operatorIds);

    /**
     * 重置操作员密码
     *
     * @param operatorId 操作员ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    Boolean resetOperatorPassword(String operatorId, String newPassword);

    /**
     * 更新操作员状态
     *
     * @param operatorId 操作员ID
     * @param status 状态（0-禁用，1-启用）
     * @return 是否更新成功
     */
    Boolean updateOperatorStatus(String operatorId, Integer status);

    /**
     * 绑定角色
     *
     * @param operatorId 操作员ID
     * @param roleIds 角色ID列表
     * @return 是否绑定成功
     */
    Boolean bindRoles(String operatorId, List<String> roleIds);

    /**
     * 解绑角色
     *
     * @param operatorId 操作员ID
     * @param roleIds 角色ID列表
     * @return 是否解绑成功
     */
    Boolean unbindRoles(String operatorId, List<String> roleIds);
}