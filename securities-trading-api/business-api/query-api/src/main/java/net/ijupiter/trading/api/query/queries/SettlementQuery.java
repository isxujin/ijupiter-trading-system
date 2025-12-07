package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 结算记录查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementQuery {
    
    /**
     * 结算ID
     */
    private String settlementId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 成交ID
     */
    private String tradeId;
    
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
     * 结算日期
     */
    private String settlementDate;
    
    /**
     * 结算状态
     */
    private String status;
    
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
     * 根据结算ID查询单个结算记录
     */
    public static SettlementQuery bySettlementId(String settlementId) {
        SettlementQuery query = new SettlementQuery();
        query.setSettlementId(settlementId);
        return query;
    }
    
    /**
     * 根据订单ID查询结算记录列表
     */
    public static SettlementQuery byOrderId(String orderId) {
        SettlementQuery query = new SettlementQuery();
        query.setOrderId(orderId);
        return query;
    }
    
    /**
     * 根据成交ID查询结算记录
     */
    public static SettlementQuery byTradeId(String tradeId) {
        SettlementQuery query = new SettlementQuery();
        query.setTradeId(tradeId);
        return query;
    }
    
    /**
     * 根据客户ID查询结算记录列表
     */
    public static SettlementQuery byCustomerId(String customerId) {
        SettlementQuery query = new SettlementQuery();
        query.setCustomerId(customerId);
        return query;
    }
    
    /**
     * 根据结算日期查询结算记录列表
     */
    public static SettlementQuery bySettlementDate(String settlementDate) {
        SettlementQuery query = new SettlementQuery();
        query.setSettlementDate(settlementDate);
        return query;
    }
    
    /**
     * 查询所有结算记录
     */
    public static SettlementQuery all() {
        return new SettlementQuery();
    }
}