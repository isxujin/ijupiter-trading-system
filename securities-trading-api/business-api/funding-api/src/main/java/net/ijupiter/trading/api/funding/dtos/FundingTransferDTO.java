package net.ijupiter.trading.api.funding.dtos;

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
 * 资金转账DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FundingTransferDTO extends BaseDTO<FundingTransferDTO> {
    /**
     * 转账编号
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
     * 转入客户ID
     */
    private Long toCustomerId;
    
    /**
     * 转入客户编号
     */
    private String toCustomerCode;
    
    /**
     * 转入账户编号
     */
    private String toAccountCode;
    
    /**
     * 转账金额
     */
    private BigDecimal amount;
    
    /**
     * 转账类型(1:内部转账,2:外部转账)
     */
    private Integer transferType;
    
    /**
     * 转账状态(1:待处理,2:处理中,3:已完成,4:已取消,5:失败)
     */
    private Integer status;
    
    /**
     * 转账时间
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