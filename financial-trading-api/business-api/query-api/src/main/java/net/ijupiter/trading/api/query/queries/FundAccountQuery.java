package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资金账户查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundAccountQuery {
    
    /**
     * 资金账户ID
     */
    private String fundAccountId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 账户类型
     */
    private String accountType;
    
    /**
     * 账户状态
     */
    private String status;
    
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
     * 根据资金账户ID查询单个账户
     */
    public static FundAccountQuery byFundAccountId(String fundAccountId) {
        FundAccountQuery query = new FundAccountQuery();
        query.setFundAccountId(fundAccountId);
        return query;
    }
    
    /**
     * 根据客户ID查询资金账户列表
     */
    public static FundAccountQuery byCustomerId(String customerId) {
        FundAccountQuery query = new FundAccountQuery();
        query.setCustomerId(customerId);
        return query;
    }
    
    /**
     * 根据账户ID查询资金账户
     */
    public static FundAccountQuery byAccountId(String accountId) {
        FundAccountQuery query = new FundAccountQuery();
        query.setAccountId(accountId);
        return query;
    }
    
    /**
     * 查询所有资金账户
     */
    public static FundAccountQuery all() {
        return new FundAccountQuery();
    }
}