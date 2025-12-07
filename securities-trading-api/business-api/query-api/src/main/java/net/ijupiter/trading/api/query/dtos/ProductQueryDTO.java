package net.ijupiter.trading.api.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 产品查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryDTO {
    
    /**
     * 产品ID
     */
    private String productId;
    
    /**
     * 产品代码
     */
    private String productCode;
    
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 产品类型
     */
    private String productType;
    
    /**
     * 产品状态
     */
    private String status;
    
    /**
     * 交易市场
     */
    private String market;
    
    /**
     * 交易币种
     */
    private String currency;
    
    /**
     * 最小交易数量
     */
    private BigDecimal minQuantity;
    
    /**
     * 最大交易数量
     */
    private BigDecimal maxQuantity;
    
    /**
     * 数量精度
     */
    private Integer quantityPrecision;
    
    /**
     * 价格精度
     */
    private Integer pricePrecision;
    
    /**
     * 涨跌停价格
     */
    private BigDecimal limitUpPrice;
    
    /**
     * 跌停价格
     */
    private BigDecimal limitDownPrice;
    
    /**
     * 昨收价
     */
    private BigDecimal previousClose;
    
    /**
     * 最新价
     */
    private BigDecimal latestPrice;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}