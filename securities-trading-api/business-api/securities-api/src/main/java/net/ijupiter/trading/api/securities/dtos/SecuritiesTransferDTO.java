package net.ijupiter.trading.api.securities.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券转托管DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SecuritiesTransferDTO extends BaseDTO<SecuritiesTransferDTO> {
    /**
     * 转托管编号
     */
    private String transferCode;
    
    /**
     * 转出客户ID
     */
    private Long fromCustomerId;
    
    /**
     * 转出客户编号
     */
    private String fromCustomerCode;
    
    /**
     * 转出账户编号
     */
    private String fromAccountCode;
    
    /**
     * 转入券商ID
     */
    private String toBrokerId;
    
    /**
     * 转入券商名称
     */
    private String toBrokerName;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 转托管数量
     */
    private BigDecimal quantity;
    
    /**
     * 转托管类型(1:转出,2:转入)
     */
    private Integer transferType;
    
    /**
     * 转托管状态(1:待处理,2:处理中,3:已完成,4:已取消,5:失败)
     */
    private Integer status;
    
    /**
     * 转托管时间
     */
    private LocalDateTime transferTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
}