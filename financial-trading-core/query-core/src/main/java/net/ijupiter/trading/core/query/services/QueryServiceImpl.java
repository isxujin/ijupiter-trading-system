package net.ijupiter.trading.core.query.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.api.query.queries.*;
import net.ijupiter.trading.api.query.services.QueryService;
import net.ijupiter.trading.core.query.model.*;
import net.ijupiter.trading.core.query.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询服务实现
 * 
 * @author ijupiter
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    
    private final AccountViewRepository accountViewRepository;
    private final OrderViewRepository orderViewRepository;
    private final FundAccountViewRepository fundAccountViewRepository;
    private final ProductViewRepository productViewRepository;
    private final TradeViewRepository tradeViewRepository;
    private final SettlementViewRepository settlementViewRepository;
    
    @Override
    public List<AccountQueryDTO> queryAccounts(AccountQuery query) {
        log.debug("查询账户信息: {}", query);
        
        List<AccountView> accountViews;
        
        // 根据查询条件确定查询方法
        if (query.getAccountId() != null) {
            accountViews = accountViewRepository.findById(query.getAccountId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getCustomerId() != null && query.getAccountType() != null) {
            accountViews = accountViewRepository.findByCustomerIdAndAccountType(query.getCustomerId(), query.getAccountType());
        } else if (query.getCustomerId() != null) {
            accountViews = accountViewRepository.findByCustomerId(query.getCustomerId());
        } else if (query.getAccountType() != null) {
            accountViews = accountViewRepository.findByAccountType(query.getAccountType());
        } else if (query.getStatus() != null) {
            accountViews = accountViewRepository.findByStatus(query.getStatus());
        } else {
            // 查询所有账户
            accountViews = accountViewRepository.findAll();
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1; // Spring Data的页码从0开始
            int size = query.getSize();
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            
            // 这里简化处理，实际应该在Repository层支持分页
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, accountViews.size());
            
            if (startIndex >= accountViews.size()) {
                accountViews = List.of();
            } else {
                accountViews = accountViews.subList(startIndex, endIndex);
            }
        }
        
        return accountViews.stream()
                .map(this::convertToAccountQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public AccountQueryDTO getAccount(String accountId) {
        log.debug("查询单个账户信息: {}", accountId);
        
        return accountViewRepository.findById(accountId)
                .map(this::convertToAccountQueryDTO)
                .orElse(null);
    }
    
    @Override
    public List<OrderQueryDTO> queryOrders(OrderQuery query) {
        log.debug("查询订单信息: {}", query);
        
        List<OrderView> orderViews;
        
        // 根据查询条件确定查询方法
        if (query.getOrderId() != null) {
            orderViews = orderViewRepository.findById(query.getOrderId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getCustomerId() != null && query.getProductCode() != null) {
            orderViews = orderViewRepository.findByCustomerIdAndProductCode(query.getCustomerId(), query.getProductCode());
        } else if (query.getCustomerId() != null && query.getStatus() != null) {
            orderViews = orderViewRepository.findByCustomerIdAndStatus(query.getCustomerId(), query.getStatus());
        } else if (query.getCustomerId() != null) {
            orderViews = orderViewRepository.findByCustomerId(query.getCustomerId());
        } else if (query.getAccountId() != null) {
            orderViews = orderViewRepository.findByAccountId(query.getAccountId());
        } else if (query.getProductCode() != null) {
            orderViews = orderViewRepository.findByProductCode(query.getProductCode());
        } else if (query.getStatus() != null) {
            orderViews = orderViewRepository.findByStatus(query.getStatus());
        } else if (query.getOrderType() != null) {
            orderViews = orderViewRepository.findByOrderType(query.getOrderType());
        } else if (query.getOrderSide() != null) {
            orderViews = orderViewRepository.findByOrderSide(query.getOrderSide());
        } else {
            // 查询所有订单
            orderViews = orderViewRepository.findAll();
        }
        
        // 应用时间范围过滤
        if (query.getStartTime() != null && query.getEndTime() != null) {
            orderViews = orderViews.stream()
                    .filter(order -> !order.getOrderTime().isBefore(query.getStartTime()) && 
                                     !order.getOrderTime().isAfter(query.getEndTime()))
                    .collect(Collectors.toList());
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1;
            int size = query.getSize();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, orderViews.size());
            
            if (startIndex >= orderViews.size()) {
                orderViews = List.of();
            } else {
                orderViews = orderViews.subList(startIndex, endIndex);
            }
        }
        
        return orderViews.stream()
                .map(this::convertToOrderQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderQueryDTO getOrder(String orderId) {
        log.debug("查询单个订单信息: {}", orderId);
        
        return orderViewRepository.findById(orderId)
                .map(this::convertToOrderQueryDTO)
                .orElse(null);
    }
    
    @Override
    public List<FundAccountQueryDTO> queryFundAccounts(FundAccountQuery query) {
        log.debug("查询资金账户信息: {}", query);
        
        List<FundAccountView> fundAccountViews;
        
        // 根据查询条件确定查询方法
        if (query.getFundAccountId() != null) {
            fundAccountViews = fundAccountViewRepository.findById(query.getFundAccountId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getAccountId() != null) {
            fundAccountViews = fundAccountViewRepository.findByAccountId(query.getAccountId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getCustomerId() != null && query.getAccountType() != null) {
            fundAccountViews = fundAccountViewRepository.findByCustomerIdAndAccountType(query.getCustomerId(), query.getAccountType());
        } else if (query.getCustomerId() != null && query.getStatus() != null) {
            fundAccountViews = fundAccountViewRepository.findByCustomerIdAndStatus(query.getCustomerId(), query.getStatus());
        } else if (query.getCustomerId() != null) {
            fundAccountViews = fundAccountViewRepository.findByCustomerId(query.getCustomerId());
        } else if (query.getAccountType() != null) {
            fundAccountViews = fundAccountViewRepository.findByAccountType(query.getAccountType());
        } else if (query.getStatus() != null) {
            fundAccountViews = fundAccountViewRepository.findByStatus(query.getStatus());
        } else if (query.getCurrency() != null) {
            fundAccountViews = fundAccountViewRepository.findByCurrency(query.getCurrency());
        } else {
            // 查询所有资金账户
            fundAccountViews = fundAccountViewRepository.findAll();
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1;
            int size = query.getSize();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, fundAccountViews.size());
            
            if (startIndex >= fundAccountViews.size()) {
                fundAccountViews = List.of();
            } else {
                fundAccountViews = fundAccountViews.subList(startIndex, endIndex);
            }
        }
        
        return fundAccountViews.stream()
                .map(this::convertToFundAccountQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public FundAccountQueryDTO getFundAccount(String fundAccountId) {
        log.debug("查询单个资金账户信息: {}", fundAccountId);
        
        return fundAccountViewRepository.findById(fundAccountId)
                .map(this::convertToFundAccountQueryDTO)
                .orElse(null);
    }
    
    @Override
    public List<ProductQueryDTO> queryProducts(ProductQuery query) {
        log.debug("查询产品信息: {}", query);
        
        List<ProductView> productViews;
        
        // 根据查询条件确定查询方法
        if (query.getProductId() != null) {
            productViews = productViewRepository.findById(query.getProductId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getProductCode() != null) {
            productViews = productViewRepository.findByProductCode(query.getProductCode())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getProductType() != null && query.getStatus() != null) {
            productViews = productViewRepository.findByProductTypeAndStatus(query.getProductType(), query.getStatus());
        } else if (query.getMarket() != null && query.getStatus() != null) {
            productViews = productViewRepository.findByMarketAndStatus(query.getMarket(), query.getStatus());
        } else if (query.getProductType() != null) {
            productViews = productViewRepository.findByProductType(query.getProductType());
        } else if (query.getStatus() != null) {
            productViews = productViewRepository.findByStatus(query.getStatus());
        } else if (query.getMarket() != null) {
            productViews = productViewRepository.findByMarket(query.getMarket());
        } else if (query.getCurrency() != null) {
            productViews = productViewRepository.findByCurrency(query.getCurrency());
        } else {
            // 查询所有产品
            productViews = productViewRepository.findAll();
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1;
            int size = query.getSize();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, productViews.size());
            
            if (startIndex >= productViews.size()) {
                productViews = List.of();
            } else {
                productViews = productViews.subList(startIndex, endIndex);
            }
        }
        
        return productViews.stream()
                .map(this::convertToProductQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductQueryDTO getProduct(String productId) {
        log.debug("查询单个产品信息: {}", productId);
        
        return productViewRepository.findById(productId)
                .map(this::convertToProductQueryDTO)
                .orElse(null);
    }
    
    @Override
    public ProductQueryDTO getProductByCode(String productCode) {
        log.debug("根据产品代码查询产品信息: {}", productCode);
        
        return productViewRepository.findByProductCode(productCode)
                .map(this::convertToProductQueryDTO)
                .orElse(null);
    }
    
    @Override
    public List<TradeQueryDTO> queryTrades(TradeQuery query) {
        log.debug("查询成交记录: {}", query);
        
        List<TradeView> tradeViews;
        
        // 根据查询条件确定查询方法
        if (query.getTradeId() != null) {
            tradeViews = tradeViewRepository.findById(query.getTradeId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getOrderId() != null) {
            tradeViews = tradeViewRepository.findByOrderId(query.getOrderId());
        } else if (query.getCustomerId() != null && query.getProductCode() != null) {
            tradeViews = tradeViewRepository.findByCustomerIdAndProductCode(query.getCustomerId(), query.getProductCode());
        } else if (query.getCustomerId() != null) {
            tradeViews = tradeViewRepository.findByCustomerId(query.getCustomerId());
        } else if (query.getAccountId() != null) {
            tradeViews = tradeViewRepository.findByAccountId(query.getAccountId());
        } else if (query.getProductCode() != null) {
            tradeViews = tradeViewRepository.findByProductCode(query.getProductCode());
        } else if (query.getSide() != null) {
            tradeViews = tradeViewRepository.findBySide(query.getSide());
        } else if (query.getMarket() != null) {
            tradeViews = tradeViewRepository.findByMarket(query.getMarket());
        } else {
            // 查询所有成交记录
            tradeViews = tradeViewRepository.findAll();
        }
        
        // 应用时间范围过滤
        if (query.getStartTime() != null && query.getEndTime() != null) {
            tradeViews = tradeViews.stream()
                    .filter(trade -> !trade.getTradeTime().isBefore(query.getStartTime()) && 
                                      !trade.getTradeTime().isAfter(query.getEndTime()))
                    .collect(Collectors.toList());
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1;
            int size = query.getSize();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, tradeViews.size());
            
            if (startIndex >= tradeViews.size()) {
                tradeViews = List.of();
            } else {
                tradeViews = tradeViews.subList(startIndex, endIndex);
            }
        }
        
        return tradeViews.stream()
                .map(this::convertToTradeQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public TradeQueryDTO getTrade(String tradeId) {
        log.debug("查询单个成交记录: {}", tradeId);
        
        return tradeViewRepository.findById(tradeId)
                .map(this::convertToTradeQueryDTO)
                .orElse(null);
    }
    
    @Override
    public List<SettlementQueryDTO> querySettlements(SettlementQuery query) {
        log.debug("查询结算记录: {}", query);
        
        List<SettlementView> settlementViews;
        
        // 根据查询条件确定查询方法
        if (query.getSettlementId() != null) {
            settlementViews = settlementViewRepository.findById(query.getSettlementId())
                    .map(List::of)
                    .orElse(List.of());
        } else if (query.getTradeId() != null) {
            settlementViews = settlementViewRepository.findByTradeId(query.getTradeId());
        } else if (query.getOrderId() != null) {
            settlementViews = settlementViewRepository.findByOrderId(query.getOrderId());
        } else if (query.getCustomerId() != null && query.getSettlementDate() != null) {
            settlementViews = settlementViewRepository.findByCustomerIdAndSettlementDate(query.getCustomerId(), query.getSettlementDate());
        } else if (query.getCustomerId() != null) {
            settlementViews = settlementViewRepository.findByCustomerId(query.getCustomerId());
        } else if (query.getAccountId() != null) {
            settlementViews = settlementViewRepository.findByAccountId(query.getAccountId());
        } else if (query.getProductCode() != null) {
            settlementViews = settlementViewRepository.findByProductCode(query.getProductCode());
        } else if (query.getSettlementDate() != null) {
            settlementViews = settlementViewRepository.findBySettlementDate(query.getSettlementDate());
        } else if (query.getStatus() != null) {
            settlementViews = settlementViewRepository.findByStatus(query.getStatus());
        } else {
            // 查询所有结算记录
            settlementViews = settlementViewRepository.findAll();
        }
        
        // 应用时间范围过滤
        if (query.getStartTime() != null && query.getEndTime() != null) {
            settlementViews = settlementViews.stream()
                    .filter(settlement -> !settlement.getCreateTime().isBefore(query.getStartTime()) && 
                                           !settlement.getCreateTime().isAfter(query.getEndTime()))
                    .collect(Collectors.toList());
        }
        
        // 应用分页
        if (query.getPage() != null && query.getSize() != null) {
            int page = query.getPage() - 1;
            int size = query.getSize();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, settlementViews.size());
            
            if (startIndex >= settlementViews.size()) {
                settlementViews = List.of();
            } else {
                settlementViews = settlementViews.subList(startIndex, endIndex);
            }
        }
        
        return settlementViews.stream()
                .map(this::convertToSettlementQueryDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SettlementQueryDTO getSettlement(String settlementId) {
        log.debug("查询单个结算记录: {}", settlementId);
        
        return settlementViewRepository.findById(settlementId)
                .map(this::convertToSettlementQueryDTO)
                .orElse(null);
    }
    
    // ==================== 转换方法 ====================
    
    private AccountQueryDTO convertToAccountQueryDTO(AccountView accountView) {
        return AccountQueryDTO.builder()
                .accountId(accountView.getAccountId())
                .customerId(accountView.getCustomerId())
                .accountNo(accountView.getAccountNo())
                .accountName(accountView.getAccountName())
                .accountType(accountView.getAccountType())
                .status(accountView.getStatus())
                .balance(accountView.getBalance())
                .availableBalance(accountView.getAvailableBalance())
                .frozenAmount(accountView.getFrozenAmount())
                .currency(accountView.getCurrency())
                .createTime(accountView.getCreateTime())
                .updateTime(accountView.getUpdateTime())
                .build();
    }
    
    private OrderQueryDTO convertToOrderQueryDTO(OrderView orderView) {
        return OrderQueryDTO.builder()
                .orderId(orderView.getOrderId())
                .customerId(orderView.getCustomerId())
                .accountId(orderView.getAccountId())
                .orderNo(orderView.getOrderNo())
                .productCode(orderView.getProductCode())
                .productName(orderView.getProductName())
                .orderType(orderView.getOrderType())
                .orderSide(orderView.getOrderSide())
                .status(orderView.getStatus())
                .price(orderView.getPrice())
                .quantity(orderView.getQuantity())
                .executedQuantity(orderView.getExecutedQuantity())
                .executedAmount(orderView.getExecutedAmount())
                .avgPrice(orderView.getAvgPrice())
                .amount(orderView.getAmount())
                .orderTime(orderView.getOrderTime())
                .executeTime(orderView.getExecuteTime())
                .cancelTime(orderView.getCancelTime())
                .createTime(orderView.getCreateTime())
                .updateTime(orderView.getUpdateTime())
                .build();
    }
    
    private FundAccountQueryDTO convertToFundAccountQueryDTO(FundAccountView fundAccountView) {
        return FundAccountQueryDTO.builder()
                .fundAccountId(fundAccountView.getFundAccountId())
                .customerId(fundAccountView.getCustomerId())
                .accountId(fundAccountView.getAccountId())
                .accountNo(fundAccountView.getAccountNo())
                .accountName(fundAccountView.getAccountName())
                .accountType(fundAccountView.getAccountType())
                .status(fundAccountView.getStatus())
                .totalAssets(fundAccountView.getTotalAssets())
                .availableBalance(fundAccountView.getAvailableBalance())
                .frozenBalance(fundAccountView.getFrozenBalance())
                .transitBalance(fundAccountView.getTransitBalance())
                .currency(fundAccountView.getCurrency())
                .createTime(fundAccountView.getCreateTime())
                .updateTime(fundAccountView.getUpdateTime())
                .build();
    }
    
    private ProductQueryDTO convertToProductQueryDTO(ProductView productView) {
        return ProductQueryDTO.builder()
                .productId(productView.getProductId())
                .productCode(productView.getProductCode())
                .productName(productView.getProductName())
                .productType(productView.getProductType())
                .status(productView.getStatus())
                .market(productView.getMarket())
                .currency(productView.getCurrency())
                .minQuantity(productView.getMinQuantity())
                .maxQuantity(productView.getMaxQuantity())
                .quantityPrecision(productView.getQuantityPrecision())
                .pricePrecision(productView.getPricePrecision())
                .limitUpPrice(productView.getLimitUpPrice())
                .limitDownPrice(productView.getLimitDownPrice())
                .previousClose(productView.getPreviousClose())
                .latestPrice(productView.getLatestPrice())
                .createTime(productView.getCreateTime())
                .updateTime(productView.getUpdateTime())
                .build();
    }
    
    private TradeQueryDTO convertToTradeQueryDTO(TradeView tradeView) {
        return TradeQueryDTO.builder()
                .tradeId(tradeView.getTradeId())
                .orderId(tradeView.getOrderId())
                .customerId(tradeView.getCustomerId())
                .accountId(tradeView.getAccountId())
                .productCode(tradeView.getProductCode())
                .productName(tradeView.getProductName())
                .quantity(tradeView.getQuantity())
                .price(tradeView.getPrice())
                .amount(tradeView.getAmount())
                .side(tradeView.getSide())
                .market(tradeView.getMarket())
                .tradeTime(tradeView.getTradeTime())
                .tradeNo(tradeView.getTradeNo())
                .createTime(tradeView.getCreateTime())
                .build();
    }
    
    private SettlementQueryDTO convertToSettlementQueryDTO(SettlementView settlementView) {
        return SettlementQueryDTO.builder()
                .settlementId(settlementView.getSettlementId())
                .orderId(settlementView.getOrderId())
                .tradeId(settlementView.getTradeId())
                .customerId(settlementView.getCustomerId())
                .accountId(settlementView.getAccountId())
                .productCode(settlementView.getProductCode())
                .settlementDate(settlementView.getSettlementDate())
                .settlementAmount(settlementView.getSettlementAmount())
                .settlementQuantity(settlementView.getSettlementQuantity())
                .settlementPrice(settlementView.getSettlementPrice())
                .commission(settlementView.getCommission())
                .tax(settlementView.getTax())
                .otherFee(settlementView.getOtherFee())
                .status(settlementView.getStatus())
                .settlementTime(settlementView.getSettlementTime())
                .createTime(settlementView.getCreateTime())
                .updateTime(settlementView.getUpdateTime())
                .build();
    }
}