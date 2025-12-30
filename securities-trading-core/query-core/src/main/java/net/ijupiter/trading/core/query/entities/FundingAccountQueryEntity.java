package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "fund_funding_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundingAccountQueryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_no")
    private String accountNo;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "account_type")
    private String accountType;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "balance")
    private BigDecimal balance;
    
    @Column(name = "frozen_amount")
    private BigDecimal frozenAmount;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "updated_by")
    private String updatedBy;
}