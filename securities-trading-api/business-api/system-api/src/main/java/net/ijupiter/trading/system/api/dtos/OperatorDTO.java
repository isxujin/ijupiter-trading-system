package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.util.List;

/**
 * 操作员DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OperatorDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 操作员ID
     */
    @NotNull(message = "操作员ID不能为空")
    private String operatorId;

    /**
     * 操作员名称
     */
    @NotBlank(message = "操作员名称不能为空")
    private String operatorName;

    /**
     * 登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    private List<String> roleIds;

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

}