package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.util.List;

/**
 * 角色DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseDTO<RoleDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private String roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 是否系统内置角色
     */
    private Boolean isSystem;

    /**
     * 权限ID列表
     */
    private List<String> permissionIds;

    /**
     * 权限名称列表
     */
    private List<String> permissionNames;

}