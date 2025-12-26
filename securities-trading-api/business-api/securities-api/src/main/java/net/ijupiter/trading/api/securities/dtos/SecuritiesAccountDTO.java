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
 * 证券账户DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SecuritiesAccountDTO extends BaseDTO<SecuritiesAccountDTO> {
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 账户编号
     */
    private String accountCode;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 证券总市值
     */
    private BigDecimal totalMarketValue;
    
    /**
     * 总资产(证券市值+资金余额)
     */
    private BigDecimal totalAssets;
    
    /**
     * 冻结证券市值
     */
    private BigDecimal frozenMarketValue;
    
    /**
     * 可用证券市值
     */
    private BigDecimal availableMarketValue;
    
    /**
     * 账户状态(1:正常,2:冻结,3:注销)
     */
    private Integer status;
    
    /**
     * 开户日期
     */
    private LocalDateTime openDate;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 备注
     */
    private String remark;
}