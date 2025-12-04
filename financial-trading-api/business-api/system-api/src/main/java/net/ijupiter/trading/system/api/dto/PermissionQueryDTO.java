package net.ijupiter.trading.system.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 权限查询DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class PermissionQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型（menu-菜单，button-按钮，api-接口）
     */
    private String permissionType;

    /**
     * 父权限ID
     */
    private String parentId;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}