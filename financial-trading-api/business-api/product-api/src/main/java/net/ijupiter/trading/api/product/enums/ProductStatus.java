package net.ijupiter.trading.api.product.enums;

/**
 * 证券产品状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum ProductStatus {
    /**
     * 待发行
     */
    PENDING("待发行"),
    
    /**
     * 已发行
     */
    ISSUED("已发行"),
    
    /**
     * 交易中
     */
    TRADING("交易中"),
    
    /**
     * 停牌
     */
    SUSPENDED("停牌"),
    
    /**
     * 已退市
     */
    DELISTED("已退市"),
    
    /**
     * 已到期
     */
    EXPIRED("已到期"),
    
    /**
     * 已违约
     */
    DEFAULTED("已违约");
    
    private final String description;
    
    ProductStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}