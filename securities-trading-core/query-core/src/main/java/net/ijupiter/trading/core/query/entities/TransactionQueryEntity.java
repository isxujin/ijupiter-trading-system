package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionQueryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_no")
    private String transactionNo;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "funding_account_id")
    private Long fundingAccountId;
    
    @Column(name = "securities_account_id")
    private Long securitiesAccountId;
    
    @Column(name = "transaction_type")
    private String transactionType;
    
    @Column(name = "security_code")
    private String securityCode;
    
    @Column(name = "security_name")
    private String securityName;
    
    @Column(name = "transaction_price")
    private BigDecimal transactionPrice;
    
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    
    @Column(name = "transaction_volume")
    private Long transactionVolume;
    
    @Column(name = "transaction_fee")
    private BigDecimal transactionFee;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "updated_by")
    private String updatedBy;
}