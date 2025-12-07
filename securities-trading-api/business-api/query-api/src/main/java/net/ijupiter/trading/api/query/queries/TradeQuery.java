package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 成交记录查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeQuery {
    
    /**
     * 成交ID
     */
    private String tradeId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 产品代码
     */
    private String productCode;
    
    /**
     * 成交方向
     */
    private String side;
    
    /**
     * 交易市场
     */
    private String market;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 分页页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer size;
    
    /**
     * 根据成交ID查询单个成交记录
     */
    public static TradeQuery byTradeId(String tradeId) {
        TradeQuery query = new TradeQuery();
        query.setTradeId(tradeId);
        return query;
    }
    
    /**
     * 根据订单ID查询成交记录列表
     */
    public static TradeQuery byOrderId(String orderId) {
        TradeQuery query = new TradeQuery();
        query.setOrderId(orderId);
        return query;
    }
    
    /**
     * 根据客户ID查询成交记录列表
     */
    public static TradeQuery byCustomerId(String customerId) {
        TradeQuery query = new TradeQuery();
        query.setCustomerId(customerId);
        return query;
    }
    
    /**
     * 根据产品代码查询成交记录列表
     */
    public static TradeQuery byProductCode(String productCode) {
        TradeQuery query = new TradeQuery();
        query.setProductCode(productCode);
        return query;
    }
    
    /**
     * 查询所有成交记录
     */
    public static TradeQuery all() {
        return new TradeQuery();
    }
}