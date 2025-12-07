package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuery {
    
    /**
     * 产品ID
     */
    private String productId;
    
    /**
     * 产品代码
     */
    private String productCode;
    
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
     * 币种
     */
    private String currency;
    
    /**
     * 分页页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer size;
    
    /**
     * 根据产品ID查询单个产品
     */
    public static ProductQuery byProductId(String productId) {
        ProductQuery query = new ProductQuery();
        query.setProductId(productId);
        return query;
    }
    
    /**
     * 根据产品代码查询单个产品
     */
    public static ProductQuery byProductCode(String productCode) {
        ProductQuery query = new ProductQuery();
        query.setProductCode(productCode);
        return query;
    }
    
    /**
     * 根据产品类型查询产品列表
     */
    public static ProductQuery byProductType(String productType) {
        ProductQuery query = new ProductQuery();
        query.setProductType(productType);
        return query;
    }
    
    /**
     * 根据市场查询产品列表
     */
    public static ProductQuery byMarket(String market) {
        ProductQuery query = new ProductQuery();
        query.setMarket(market);
        return query;
    }
    
    /**
     * 查询所有产品
     */
    public static ProductQuery all() {
        return new ProductQuery();
    }
}