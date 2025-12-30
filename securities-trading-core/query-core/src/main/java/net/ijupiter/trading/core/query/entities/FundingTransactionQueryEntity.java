package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金流水查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "fund_funding_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundingTransactionQueryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_no")
    private String transactionNo;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "funding_account_id")
    private Long fundingAccountId;
    
    @Column(name = "transaction_type")
    private String transactionType;
    
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    
    @Column(name = "transaction_channel")
    private String transactionChannel;
    
    @Column(name = "transaction_description")
    private String transactionDescription;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "updated_by")
    private String updatedBy;
}