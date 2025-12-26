package net.ijupiter.trading.api.settlement.services;

import net.ijupiter.trading.api.settlement.models.SettlementStatistics;
import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.settlement.dtos.SettlementDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 清算服务接口
 */
public interface SettlementService extends BaseService<SettlementDTO, Long> {
    
    /**
     * 根据清算类型查询清算记录
     */
    List<SettlementDTO> findBySettlementType(Integer settlementType);
    
    /**
     * 根据状态查询清算记录
     */
    List<SettlementDTO> findByStatus(Integer status);
    
    /**
     * 根据清算日期范围查询清算记录
     */
    List<SettlementDTO> findBySettlementDate(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根据买方客户ID查询清算记录
     */
    List<SettlementDTO> findByBuyerCustomerId(Long buyerCustomerId);
    
    /**
     * 根据卖方客户ID查询清算记录
     */
    List<SettlementDTO> findBySellerCustomerId(Long sellerCustomerId);
    
    /**
     * 根据清算编号查询清算记录
     */
    Optional<SettlementDTO> findBySettlementCode(String settlementCode);
    
    /**
     * 获取清算统计数据
     */
    SettlementStatistics getSettlementStatistics();
}