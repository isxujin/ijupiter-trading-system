package net.ijupiter.trading.core.settlement.repositories;

import net.ijupiter.trading.api.settlement.dtos.SettlementDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 清算数据访问接口
 */
public interface SettlementRepository {
    
    /**
     * 查询所有清算记录
     */
    List<SettlementDTO> findAllSettlements();
    
    /**
     * 根据ID查询清算记录
     */
    Optional<SettlementDTO> findSettlementById(Long id);
    
    /**
     * 保存清算记录
     */
    SettlementDTO saveSettlement(SettlementDTO settlementDTO);
    
    /**
     * 更新清算记录
     */
    void updateSettlement(SettlementDTO settlementDTO);
    
    /**
     * 删除清算记录
     */
    void deleteSettlementById(Long id);
    
    /**
     * 根据清算类型查询清算记录
     */
    List<SettlementDTO> findSettlementsByType(Integer settlementType);
    
    /**
     * 根据状态查询清算记录
     */
    List<SettlementDTO> findSettlementsByStatus(Integer status);
    
    /**
     * 根据清算日期范围查询清算记录
     */
    List<SettlementDTO> findSettlementsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根据买方客户ID查询清算记录
     */
    List<SettlementDTO> findSettlementsByBuyerCustomerId(Long buyerCustomerId);
    
    /**
     * 根据卖方客户ID查询清算记录
     */
    List<SettlementDTO> findSettlementsBySellerCustomerId(Long sellerCustomerId);
    
    /**
     * 根据清算编号查询清算记录
     */
    Optional<SettlementDTO> findSettlementByCode(String settlementCode);
}