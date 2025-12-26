package net.ijupiter.trading.api.settlement.dtos;

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
 * 清算DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SettlementDTO extends BaseDTO<SettlementDTO> {
    /**
     * 清算编号
     */
    private String settlementCode;
    
    /**
     * 清算类型(1:资金清算,2:证券清算,3:衍生品清算)
     */
    private Integer settlementType;
    
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 买方客户ID
     */
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    private Long sellerCustomerId;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 清算数量
     */
    private BigDecimal quantity;
    
    /**
     * 清算价格
     */
    private BigDecimal price;
    
    /**
     * 清算金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 印花税
     */
    private BigDecimal tax;
    
    /**
     * 清算状态(1:待清算,2:清算中,3:已清算,4:清算失败)
     */
    private Integer status;
    
    /**
     * 清算日期
     */
    private LocalDateTime settlementDate;
    
    /**
     * 清算确认日期
     */
    private LocalDateTime confirmDate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
}