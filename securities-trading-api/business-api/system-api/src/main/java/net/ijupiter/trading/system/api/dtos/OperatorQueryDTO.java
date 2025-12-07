package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 操作员查询DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class OperatorQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}