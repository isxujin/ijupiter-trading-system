package net.ijupiter.trading.api.product.enums;

/**
 * 证券产品类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum ProductType {
    /**
     * 股票
     */
    STOCK("股票"),
    
    /**
     * 债券
     */
    BOND("债券"),
    
    /**
     * 基金
     */
    FUND("基金"),
    
    /**
     * 期货
     */
    FUTURES("期货"),
    
    /**
     * 期权
     */
    OPTION("期权"),
    
    /**
     * 权证
     */
    WARRANT("权证"),
    
    /**
     * 信托产品
     */
    TRUST("信托产品"),
    
    /**
     * 结构化产品
     */
    STRUCTURED("结构化产品");
    
    private final String description;
    
    ProductType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}