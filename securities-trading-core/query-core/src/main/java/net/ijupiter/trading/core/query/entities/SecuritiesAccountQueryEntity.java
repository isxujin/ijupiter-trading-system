package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券账户查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "securities_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritiesAccountQueryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_no")
    private String accountNo;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "securities_type")
    private String securitiesType;
    
    @Column(name = "account_name")
    private String accountName;
    
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