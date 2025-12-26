package net.ijupiter.trading.core.settlement.repositories;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.settlement.dtos.SettlementDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 清算数据访问实现(基于内存模拟)
 */
@Slf4j
public class SettlementRepositoryImpl implements SettlementRepository {
    
    /**
     * 模拟数据库存储
     */
    private static final Map<Long, SettlementDTO> DB = new ConcurrentHashMap<>();
    private static final Map<String, Long> CODE_TO_ID_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);
    
    static {
        // 初始化一些测试数据
        SettlementDTO settlement1 = SettlementDTO.builder()
//                .id(1L)
                .settlementCode("S202312010001")
                .settlementType(2) // 证券清算
                .tradeCode("T202312010001")
                .buyerCustomerId(1001L)
                .sellerCustomerId(1002L)
                .securityCode("600000")
                .securityName("浦发银行")
                .quantity(java.math.BigDecimal.valueOf(1000))
                .price(java.math.BigDecimal.valueOf(12.50))
                .amount(java.math.BigDecimal.valueOf(12500))
                .fee(java.math.BigDecimal.valueOf(3.75))
                .tax(java.math.BigDecimal.valueOf(12.50))
                .status(3) // 已清算
                .settlementDate(LocalDateTime.now().minusDays(1))
                .confirmDate(LocalDateTime.now().minusDays(1).plusHours(1))
                .remark("证券交易清算")
                .build();
        
        SettlementDTO settlement2 = SettlementDTO.builder()
//                .id(2L)
                .settlementCode("S202312010002")
                .settlementType(1) // 资金清算
                .tradeCode("T202312010002")
                .buyerCustomerId(1003L)
                .sellerCustomerId(1004L)
                .securityCode("000001")
                .securityName("平安银行")
                .quantity(java.math.BigDecimal.valueOf(500))
                .price(java.math.BigDecimal.valueOf(15.80))
                .amount(java.math.BigDecimal.valueOf(7900))
                .fee(java.math.BigDecimal.valueOf(2.37))
                .tax(java.math.BigDecimal.ZERO) // 买入不收印花税
                .status(2) // 清算中
                .settlementDate(LocalDateTime.now().minusDays(1))
                .remark("资金转账清算")
                .build();
        
        SettlementDTO settlement3 = SettlementDTO.builder()
//                .id(3L)
                .settlementCode("S202312010003")
                .settlementType(3) // 衍生品清算
                .tradeCode("T202312010003")
                .buyerCustomerId(1005L)
                .sellerCustomerId(1006L)
                .securityCode("510050")
                .securityName("50ETF期权")
                .quantity(java.math.BigDecimal.valueOf(10))
                .price(java.math.BigDecimal.valueOf(0.25))
                .amount(java.math.BigDecimal.valueOf(2500))
                .fee(java.math.BigDecimal.valueOf(7.50))
                .tax(java.math.BigDecimal.ZERO)
                .status(1) // 待清算
                .settlementDate(LocalDateTime.now())
                .remark("期权合约清算")
                .build();
        
        DB.put(1L, settlement1);
        DB.put(2L, settlement2);
        DB.put(3L, settlement3);
        
        CODE_TO_ID_MAP.put("S202312010001", 1L);
        CODE_TO_ID_MAP.put("S202312010002", 2L);
        CODE_TO_ID_MAP.put("S202312010003", 3L);
        
        ID_SEQUENCE.set(4);
    }
    
    @Override
    public List<SettlementDTO> findAllSettlements() {
        return new ArrayList<>(DB.values());
    }
    
    @Override
    public Optional<SettlementDTO> findSettlementById(Long id) {
        return Optional.ofNullable(DB.get(id));
    }
    
    @Override
    public SettlementDTO saveSettlement(SettlementDTO settlementDTO) {
        if (settlementDTO.getId() == null) {
            // 创建新记录
            Long newId = ID_SEQUENCE.getAndIncrement();
            settlementDTO.setId(newId);
            
            if (settlementDTO.getCreateTime() == null) {
                settlementDTO.setCreateTime(LocalDateTime.now());
            }
            if (settlementDTO.getUpdateTime() == null) {
                settlementDTO.setUpdateTime(LocalDateTime.now());
            }
            
            DB.put(newId, settlementDTO);
            
            if (settlementDTO.getSettlementCode() != null) {
                CODE_TO_ID_MAP.put(settlementDTO.getSettlementCode(), newId);
            }
        } else {
            // 更新现有记录
            settlementDTO.setUpdateTime(LocalDateTime.now());
            DB.put(settlementDTO.getId(), settlementDTO);
            
            if (settlementDTO.getSettlementCode() != null) {
                CODE_TO_ID_MAP.put(settlementDTO.getSettlementCode(), settlementDTO.getId());
            }
        }
        return settlementDTO;
    }
    
    @Override
    public void updateSettlement(SettlementDTO settlementDTO) {
        if (settlementDTO.getId() != null && DB.containsKey(settlementDTO.getId())) {
            settlementDTO.setUpdateTime(LocalDateTime.now());
            DB.put(settlementDTO.getId(), settlementDTO);
            
            if (settlementDTO.getSettlementCode() != null) {
                CODE_TO_ID_MAP.put(settlementDTO.getSettlementCode(), settlementDTO.getId());
            }
        } else {
            log.warn("尝试更新不存在的清算记录: {}", settlementDTO.getId());
        }
    }
    
    @Override
    public void deleteSettlementById(Long id) {
        SettlementDTO removed = DB.remove(id);
        if (removed != null && removed.getSettlementCode() != null) {
            CODE_TO_ID_MAP.remove(removed.getSettlementCode());
        }
    }
    
    @Override
    public List<SettlementDTO> findSettlementsByType(Integer settlementType) {
        return DB.values().stream()
                .filter(s -> settlementType.equals(s.getSettlementType()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<SettlementDTO> findSettlementsByStatus(Integer status) {
        return DB.values().stream()
                .filter(s -> status.equals(s.getStatus()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<SettlementDTO> findSettlementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return DB.values().stream()
                .filter(s -> s.getSettlementDate() != null)
                .filter(s -> !s.getSettlementDate().isBefore(startDate) && !s.getSettlementDate().isAfter(endDate))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<SettlementDTO> findSettlementsByBuyerCustomerId(Long buyerCustomerId) {
        return DB.values().stream()
                .filter(s -> buyerCustomerId.equals(s.getBuyerCustomerId()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<SettlementDTO> findSettlementsBySellerCustomerId(Long sellerCustomerId) {
        return DB.values().stream()
                .filter(s -> sellerCustomerId.equals(s.getSellerCustomerId()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public Optional<SettlementDTO> findSettlementByCode(String settlementCode) {
        Long id = CODE_TO_ID_MAP.get(settlementCode);
        return id != null ? Optional.ofNullable(DB.get(id)) : Optional.empty();
    }
}