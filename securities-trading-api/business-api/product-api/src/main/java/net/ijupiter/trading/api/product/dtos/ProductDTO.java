package net.ijupiter.trading.api.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.product.enums.ProductStatus;
import net.ijupiter.trading.api.product.enums.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券产品数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productId;
    private String productCode;
    private String productName;
    private ProductType productType;
    private ProductStatus status;
    private BigDecimal faceValue;
    private BigDecimal issuePrice;
    private BigDecimal currentPrice;
    private BigDecimal minTradeUnit;
    private BigDecimal maxTradeUnit;
    private BigDecimal tradeUnit;
    private BigDecimal priceTick;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime suspendTime;
    private LocalDateTime resumeTime;
    private LocalDateTime delistTime;
    private String suspendReason;
    private String resumeReason;
    private String delistReason;

    /**
     * 是否处于待发行状态
     * 
     * @return 是否待发行
     */
    public boolean isPending() {
        return ProductStatus.PENDING.equals(status);
    }

    /**
     * 是否已发行
     * 
     * @return 是否已发行
     */
    public boolean isIssued() {
        return ProductStatus.ISSUED.equals(status);
    }

    /**
     * 是否处于交易中状态
     * 
     * @return 是否交易中
     */
    public boolean isTrading() {
        return ProductStatus.TRADING.equals(status);
    }

    /**
     * 是否处于停牌状态
     * 
     * @return 是否停牌
     */
    public boolean isSuspended() {
        return ProductStatus.SUSPENDED.equals(status);
    }

    /**
     * 是否已退市
     * 
     * @return 是否已退市
     */
    public boolean isDelisted() {
        return ProductStatus.DELISTED.equals(status);
    }

    /**
     * 是否已到期
     * 
     * @return 是否已到期
     */
    public boolean isExpired() {
        return ProductStatus.EXPIRED.equals(status);
    }

    /**
     * 是否已违约
     * 
     * @return 是否已违约
     */
    public boolean isDefaulted() {
        return ProductStatus.DEFAULTED.equals(status);
    }

    /**
     * 是否可以进行交易
     * 
     * @return 是否可交易
     */
    public boolean canTrade() {
        return ProductStatus.TRADING.equals(status);
    }

    /**
     * 获取收益率
     * 
     * @return 收益率
     */
    public BigDecimal getReturnRate() {
        if (faceValue == null || faceValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return currentPrice.subtract(faceValue).divide(faceValue, 6, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算涨跌幅
     * 
     * @param comparePrice 比较价格
     * @return 涨跌幅
     */
    public BigDecimal calculateChangeRate(BigDecimal comparePrice) {
        if (comparePrice == null || comparePrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return currentPrice.subtract(comparePrice).divide(comparePrice, 6, BigDecimal.ROUND_HALF_UP);
    }
}