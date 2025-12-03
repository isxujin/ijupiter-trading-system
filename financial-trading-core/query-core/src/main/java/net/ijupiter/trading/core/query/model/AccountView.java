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
 * 账户视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "account_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountView {
    
    @Id
    private String accountId;
    
    @Column(name = "customer_id")
    private String customerId;
    
    @Column(name = "account_no")
    private String accountNo;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "account_type")
    private String accountType;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "balance", precision = 19, scale = 4)
    private BigDecimal balance;
    
    @Column(name = "available_balance", precision = 19, scale = 4)
    private BigDecimal availableBalance;
    
    @Column(name = "frozen_amount", precision = 19, scale = 4)
    private BigDecimal frozenAmount;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}