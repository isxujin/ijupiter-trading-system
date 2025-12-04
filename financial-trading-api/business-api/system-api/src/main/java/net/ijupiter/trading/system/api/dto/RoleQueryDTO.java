package net.ijupiter.trading.system.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 角色查询DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class RoleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 是否系统内置角色
     */
    private Boolean isSystem;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}