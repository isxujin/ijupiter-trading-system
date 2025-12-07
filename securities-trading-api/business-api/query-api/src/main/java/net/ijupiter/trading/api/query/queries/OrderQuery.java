package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderQuery {
    
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
     * 订单类型
     */
    private String orderType;
    
    /**
     * 订单方向
     */
    private String orderSide;
    
    /**
     * 订单状态
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
     * 根据订单ID查询单个订单
     */
    public static OrderQuery byOrderId(String orderId) {
        OrderQuery query = new OrderQuery();
        query.setOrderId(orderId);
        return query;
    }
    
    /**
     * 根据客户ID查询订单列表
     */
    public static OrderQuery byCustomerId(String customerId) {
        OrderQuery query = new OrderQuery();
        query.setCustomerId(customerId);
        return query;
    }
    
    /**
     * 根据账户ID查询订单列表
     */
    public static OrderQuery byAccountId(String accountId) {
        OrderQuery query = new OrderQuery();
        query.setAccountId(accountId);
        return query;
    }
    
    /**
     * 根据产品代码查询订单列表
     */
    public static OrderQuery byProductCode(String productCode) {
        OrderQuery query = new OrderQuery();
        query.setProductCode(productCode);
        return query;
    }
    
    /**
     * 查询所有订单
     */
    public static OrderQuery all() {
        return new OrderQuery();
    }
}