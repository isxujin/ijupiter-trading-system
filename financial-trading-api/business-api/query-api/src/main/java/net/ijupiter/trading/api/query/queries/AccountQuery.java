package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户查询
 * 
 * @author ijupiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountQuery {
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户编号
     */
    private String accountNo;
    
    /**
     * 账户类型
     */
    private String accountType;
    
    /**
     * 账户状态
     */
    private String status;
    
    /**
     * 分页页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer size;
    
    /**
     * 根据账户ID查询单个账户
     */
    public static AccountQuery byAccountId(String accountId) {
        AccountQuery query = new AccountQuery();
        query.setAccountId(accountId);
        return query;
    }
    
    /**
     * 根据客户ID查询账户列表
     */
    public static AccountQuery byCustomerId(String customerId) {
        AccountQuery query = new AccountQuery();
        query.setCustomerId(customerId);
        return query;
    }
    
    /**
     * 根据账户类型查询账户列表
     */
    public static AccountQuery byAccountType(String accountType) {
        AccountQuery query = new AccountQuery();
        query.setAccountType(accountType);
        return query;
    }
    
    /**
     * 查询所有账户
     */
    public static AccountQuery all() {
        return new AccountQuery();
    }
}