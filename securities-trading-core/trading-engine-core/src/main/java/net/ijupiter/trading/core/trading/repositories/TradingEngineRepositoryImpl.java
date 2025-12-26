package net.ijupiter.trading.core.trading.repositories;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 交易引擎数据访问实现(基于内存模拟)
 */
@Slf4j
public class TradingEngineRepositoryImpl implements TradingEngineRepository {
    
    /**
     * 模拟数据库存储
     */
    private static final Map<Long, TradingEngineDTO> DB = new ConcurrentHashMap<>();
    private static final Map<String, Long> CODE_TO_ID_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);
    
    static {
        // 初始化一些测试数据
        TradingEngineDTO trade1 = TradingEngineDTO.builder()
//                .id(1L)
                .tradeCode("T202312010001")
                .orderCode("O202312010001")
                .tradeType(1) // 买入
                .customerId(1001L)
                .customerCode("C1001")
                .securityCode("600000")
                .securityName("浦发银行")
                .quantity(java.math.BigDecimal.valueOf(1000))
                .price(java.math.BigDecimal.valueOf(12.50))
                .amount(java.math.BigDecimal.valueOf(12500))
                .fee(java.math.BigDecimal.valueOf(3.75))
                .status(3) // 全部成交
                .buyerCustomerId(1001L)
                .sellerCustomerId(1002L)
                .matchTime(LocalDateTime.now().minusHours(2))
                .executeTime(LocalDateTime.now().minusHours(1))
                .market(1) // 主板
                .remark("限价单买入")
                .build();
        
        TradingEngineDTO trade2 = TradingEngineDTO.builder()
//                .id(2L)
                .tradeCode("T202312010002")
                .orderCode("O202312010002")
                .tradeType(2) // 卖出
                .customerId(1003L)
                .customerCode("C1003")
                .securityCode("000001")
                .securityName("平安银行")
                .quantity(java.math.BigDecimal.valueOf(500))
                .price(java.math.BigDecimal.valueOf(15.80))
                .amount(java.math.BigDecimal.valueOf(7900))
                .fee(java.math.BigDecimal.valueOf(2.37))
                .status(2) // 部分成交
                .buyerCustomerId(1004L)
                .sellerCustomerId(1003L)
                .matchTime(LocalDateTime.now().minusHours(1))
                .executeTime(null) // 还未完全成交
                .market(1) // 主板
                .remark("限价单卖出")
                .build();
        
        TradingEngineDTO trade3 = TradingEngineDTO.builder()
//                .id(3L)
                .tradeCode("T202312010003")
                .orderCode("O202312010003")
                .tradeType(1) // 买入
                .customerId(1005L)
                .customerCode("C1005")
                .securityCode("510050")
                .securityName("50ETF期权")
                .quantity(java.math.BigDecimal.valueOf(10))
                .price(java.math.BigDecimal.valueOf(0.25))
                .amount(java.math.BigDecimal.valueOf(2500))
                .fee(java.math.BigDecimal.valueOf(7.50))
                .status(1) // 待撮合
                .buyerCustomerId(null) // 还未撮合
                .sellerCustomerId(null)
                .matchTime(null)
                .executeTime(null)
                .market(3) // 科创板
                .remark("期权买入")
                .build();
        
        DB.put(1L, trade1);
        DB.put(2L, trade2);
        DB.put(3L, trade3);
        
        CODE_TO_ID_MAP.put("T202312010001", 1L);
        CODE_TO_ID_MAP.put("T202312010002", 2L);
        CODE_TO_ID_MAP.put("T202312010003", 3L);
        
        ID_SEQUENCE.set(4);
    }
    
    @Override
    public List<TradingEngineDTO> findAllTrades() {
        return new ArrayList<>(DB.values());
    }
    
    @Override
    public Optional<TradingEngineDTO> findTradeById(Long id) {
        return Optional.ofNullable(DB.get(id));
    }
    
    @Override
    public TradingEngineDTO saveTrade(TradingEngineDTO tradingEngineDTO) {
        if (tradingEngineDTO.getId() == null) {
            // 创建新记录
            Long newId = ID_SEQUENCE.getAndIncrement();
            tradingEngineDTO.setId(newId);
            
            if (tradingEngineDTO.getCreateTime() == null) {
                tradingEngineDTO.setCreateTime(LocalDateTime.now());
            }
            if (tradingEngineDTO.getUpdateTime() == null) {
                tradingEngineDTO.setUpdateTime(LocalDateTime.now());
            }
            
            DB.put(newId, tradingEngineDTO);
            
            if (tradingEngineDTO.getTradeCode() != null) {
                CODE_TO_ID_MAP.put(tradingEngineDTO.getTradeCode(), newId);
            }
        } else {
            // 更新现有记录
            tradingEngineDTO.setUpdateTime(LocalDateTime.now());
            DB.put(tradingEngineDTO.getId(), tradingEngineDTO);
            
            if (tradingEngineDTO.getTradeCode() != null) {
                CODE_TO_ID_MAP.put(tradingEngineDTO.getTradeCode(), tradingEngineDTO.getId());
            }
        }
        return tradingEngineDTO;
    }
    
    @Override
    public void updateTrade(TradingEngineDTO tradingEngineDTO) {
        if (tradingEngineDTO.getId() != null && DB.containsKey(tradingEngineDTO.getId())) {
            tradingEngineDTO.setUpdateTime(LocalDateTime.now());
            DB.put(tradingEngineDTO.getId(), tradingEngineDTO);
            
            if (tradingEngineDTO.getTradeCode() != null) {
                CODE_TO_ID_MAP.put(tradingEngineDTO.getTradeCode(), tradingEngineDTO.getId());
            }
        } else {
            log.warn("尝试更新不存在的交易记录: {}", tradingEngineDTO.getId());
        }
    }
    
    @Override
    public void deleteTradeById(Long id) {
        TradingEngineDTO removed = DB.remove(id);
        if (removed != null && removed.getTradeCode() != null) {
            CODE_TO_ID_MAP.remove(removed.getTradeCode());
        }
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByCustomerId(Long customerId) {
        return DB.values().stream()
                .filter(t -> customerId.equals(t.getCustomerId()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByStatus(Integer status) {
        return DB.values().stream()
                .filter(t -> status.equals(t.getStatus()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return DB.values().stream()
                .filter(t -> t.getExecuteTime() != null)
                .filter(t -> !t.getExecuteTime().isBefore(startDate) && !t.getExecuteTime().isAfter(endDate))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<TradingEngineDTO> findTradesBySecurityCode(String securityCode) {
        return DB.values().stream()
                .filter(t -> securityCode.equals(t.getSecurityCode()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByTradeType(Integer tradeType) {
        return DB.values().stream()
                .filter(t -> tradeType.equals(t.getTradeType()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByMarket(Integer market) {
        return DB.values().stream()
                .filter(t -> market.equals(t.getMarket()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public Optional<TradingEngineDTO> findTradeByCode(String tradeCode) {
        Long id = CODE_TO_ID_MAP.get(tradeCode);
        return id != null ? Optional.ofNullable(DB.get(id)) : Optional.empty();
    }
    
    @Override
    public List<TradingEngineDTO> findTradesByOrderCode(String orderCode) {
        return DB.values().stream()
                .filter(t -> orderCode.equals(t.getOrderCode()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}