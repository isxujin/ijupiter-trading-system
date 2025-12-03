package net.ijupiter.trading.core.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "fund_account_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundAccountView {
    
    @Id
    private String fundAccountId;
    
    @Column(name = "customer_id")
    private String customerId;
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "account_no")
    private String accountNo;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "account_type")
    private String accountType;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "total_assets", precision = 19, scale = 4)
    private BigDecimal totalAssets;
    
    @Column(name = "available_balance", precision = 19, scale = 4)
    private BigDecimal availableBalance;
    
    @Column(name = "frozen_balance", precision = 19, scale = 4)
    private BigDecimal frozenBalance;
    
    @Column(name = "transit_balance", precision = 19, scale = 4)
    private BigDecimal transitBalance;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}