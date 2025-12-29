package net.ijupiter.trading.web.tradingengine.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;
import net.ijupiter.trading.api.trading.models.TradingStatistics;
import net.ijupiter.trading.api.trading.commands.CreateTradeCommand;
import net.ijupiter.trading.api.trading.services.TradingEngineService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

/**
 * 交易引擎控制器
 */
@Slf4j
@RestController
@RequestMapping("/trading-engine")
public class TradingEngineController extends BaseController {
    
    @Autowired
    private TradingEngineService tradingEngineService;
    
    /**
     * 查询所有交易记录
     */
    @GetMapping("/all")
    public ResponseEntity<List<TradingEngineDTO>> findAll() {
        log.info("查询所有交易记录");
        List<TradingEngineDTO> trades = tradingEngineService.findAll();
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据ID查询交易记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<TradingEngineDTO> findById(@PathVariable Long id) {
        log.info("根据ID查询交易记录: {}", id);
        Optional<TradingEngineDTO> trade = tradingEngineService.findById(id);
        return trade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据交易编号查询交易记录
     */
    @GetMapping("/code/{tradeCode}")
    public ResponseEntity<TradingEngineDTO> findByTradeCode(@PathVariable String tradeCode) {
        log.info("根据交易编号查询交易记录: {}", tradeCode);
        Optional<TradingEngineDTO> trade = tradingEngineService.findByTradeCode(tradeCode);
        return trade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据客户ID查询交易记录
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TradingEngineDTO>> findByCustomerId(@PathVariable Long customerId) {
        log.info("根据客户ID查询交易记录: {}", customerId);
        List<TradingEngineDTO> trades = tradingEngineService.findByCustomerId(customerId);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据状态查询交易记录
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TradingEngineDTO>> findByStatus(@PathVariable Integer status) {
        log.info("根据状态查询交易记录: {}", status);
        List<TradingEngineDTO> trades = tradingEngineService.findByStatus(status);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据证券代码查询交易记录
     */
    @GetMapping("/security/{securityCode}")
    public ResponseEntity<List<TradingEngineDTO>> findBySecurityCode(@PathVariable String securityCode) {
        log.info("根据证券代码查询交易记录: {}", securityCode);
        List<TradingEngineDTO> trades = tradingEngineService.findBySecurityCode(securityCode);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据交易类型查询交易记录
     */
    @GetMapping("/type/{tradeType}")
    public ResponseEntity<List<TradingEngineDTO>> findByTradeType(@PathVariable Integer tradeType) {
        log.info("根据交易类型查询交易记录: {}", tradeType);
        List<TradingEngineDTO> trades = tradingEngineService.findByTradeType(tradeType);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据交易市场查询交易记录
     */
    @GetMapping("/market/{market}")
    public ResponseEntity<List<TradingEngineDTO>> findByMarket(@PathVariable Integer market) {
        log.info("根据交易市场查询交易记录: {}", market);
        List<TradingEngineDTO> trades = tradingEngineService.findByMarket(market);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据订单编号查询交易记录
     */
    @GetMapping("/order/{orderCode}")
    public ResponseEntity<List<TradingEngineDTO>> findByOrderCode(@PathVariable String orderCode) {
        log.info("根据订单编号查询交易记录: {}", orderCode);
        List<TradingEngineDTO> trades = tradingEngineService.findByOrderCode(orderCode);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 根据交易日期范围查询交易记录
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<TradingEngineDTO>> findByTradeDate(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        log.info("根据交易日期范围查询交易记录: {} - {}", startDate, endDate);
        List<TradingEngineDTO> trades = tradingEngineService.findByTradeDate(startDate, endDate);
        return ResponseEntity.ok(trades);
    }
    
    /**
     * 创建交易记录
     */
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createTrade(
            @RequestParam String orderCode,
            @RequestParam Integer tradeType,
            @RequestParam Long customerId,
            @RequestParam String customerCode,
            @RequestParam String securityCode,
            @RequestParam String securityName,
            @RequestParam BigDecimal quantity,
            @RequestParam BigDecimal price,
            @RequestParam Integer market,
            @RequestParam(required = false) Long operatorId,
            @RequestParam(required = false) String remark) {
        
        log.info("创建交易记录: orderCode={}, tradeType={}, securityCode={}", 
                orderCode, tradeType, securityCode);
        
        CreateTradeCommand command = CreateTradeCommand.builder()
                .orderCode(orderCode)
                .tradeType(tradeType)
                .customerId(customerId)
                .customerCode(customerCode)
                .securityCode(securityCode)
                .securityName(securityName)
                .quantity(quantity)
                .price(price)
                .market(market)
                .operatorId(operatorId != null ? operatorId : 1L)
                .remark(remark)
                .build();
        
//        return tradingEngineService.createTrade(command)
//                .thenApply(tradeCode -> {
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", true);
//                    result.put("tradeCode", tradeCode);
//                    result.put("message", "交易记录创建成功");
//                    return ResponseEntity.status(HttpStatus.CREATED).body(result);
//                })
//                .exceptionally(throwable -> {
//                    log.error("创建交易记录失败", throwable);
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", false);
//                    result.put("message", "交易记录创建失败: " + throwable.getMessage());
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//                });
        return null;
    }
    
    /**
     * 撮合交易
     */
    @PostMapping("/match/{tradeCode}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> matchTrade(
            @PathVariable String tradeCode,
            @RequestParam Long buyerCustomerId,
            @RequestParam Long sellerCustomerId,
            @RequestParam BigDecimal matchPrice,
            @RequestParam BigDecimal matchQuantity,
            @RequestParam(required = false) Long operatorId) {
        
        log.info("撮合交易: {}", tradeCode);
        
//        return tradingEngineService.matchTrade(tradeCode, buyerCustomerId, sellerCustomerId,
//                matchPrice, matchQuantity, operatorId != null ? operatorId : 1L)
//                .thenApply(v -> {
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", true);
//                    result.put("message", "交易撮合成功");
//                    return ResponseEntity.ok(result);
//                })
//                .exceptionally(throwable -> {
//                    log.error("撮合交易失败", throwable);
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", false);
//                    result.put("message", "交易撮合失败: " + throwable.getMessage());
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//                });
        return null;
    }
    
    /**
     * 执行交易
     */
    @PostMapping("/execute/{tradeCode}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> executeTrade(
            @PathVariable String tradeCode,
            @RequestParam BigDecimal executePrice,
            @RequestParam BigDecimal executeQuantity,
            @RequestParam(required = false) Long operatorId) {
        
        log.info("执行交易: {}", tradeCode);
        
//        return tradingEngineService.executeTrade(tradeCode, executePrice,
//                executeQuantity, operatorId != null ? operatorId : 1L)
//                .thenApply(v -> {
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", true);
//                    result.put("message", "交易执行成功");
//                    return ResponseEntity.ok(result);
//                })
//                .exceptionally(throwable -> {
//                    log.error("执行交易失败", throwable);
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", false);
//                    result.put("message", "交易执行失败: " + throwable.getMessage());
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//                });
        return null;
    }
    
    /**
     * 取消交易
     */
    @PostMapping("/cancel/{tradeCode}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> cancelTrade(
            @PathVariable String tradeCode,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) Long operatorId) {
        
        log.info("取消交易: {}", tradeCode);
        
//        return tradingEngineService.cancelTrade(tradeCode,
//                reason != null ? reason : "用户取消",
//                operatorId != null ? operatorId : 1L)
//                .thenApply(v -> {
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", true);
//                    result.put("message", "交易取消成功");
//                    return ResponseEntity.ok(result);
//                })
//                .exceptionally(throwable -> {
//                    log.error("取消交易失败", throwable);
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", false);
//                    result.put("message", "交易取消失败: " + throwable.getMessage());
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//                });
        return null;
    }
    
    /**
     * 批量撮合交易
     */
    @PostMapping("/batch-match")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> batchMatchTrades(
            @RequestBody List<String> tradeCodes,
            @RequestParam(required = false) Long operatorId) {
        
        log.info("批量撮合交易: {}", tradeCodes);
        
//        return tradingEngineService.batchMatchTrades(tradeCodes, operatorId != null ? operatorId : 1L)
//                .thenApply(v -> {
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", true);
//                    result.put("message", "批量交易撮合成功");
//                    return ResponseEntity.ok(result);
//                })
//                .exceptionally(throwable -> {
//                    log.error("批量撮合交易失败", throwable);
//                    Map<String, Object> result = new HashMap<>();
//                    result.put("success", false);
//                    result.put("message", "批量交易撮合失败: " + throwable.getMessage());
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//                });
        return null;
    }
    
    /**
     * 保存交易记录
     */
    @PostMapping("/save")
    public ResponseEntity<TradingEngineDTO> saveTrade(@RequestBody TradingEngineDTO tradingEngineDTO) {
        log.info("保存交易记录: {}", tradingEngineDTO.getTradeCode());
        TradingEngineDTO savedTrade = tradingEngineService.save(tradingEngineDTO);
        return ResponseEntity.ok(savedTrade);
    }
    
    /**
     * 删除交易记录
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        log.info("删除交易记录: {}", id);
        tradingEngineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * 获取交易统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<TradingStatistics> getTradingStatistics() {
        log.info("获取交易统计信息");
        TradingStatistics statistics = tradingEngineService.getTradingStatistics();
        return ResponseEntity.ok(statistics);
    }
}