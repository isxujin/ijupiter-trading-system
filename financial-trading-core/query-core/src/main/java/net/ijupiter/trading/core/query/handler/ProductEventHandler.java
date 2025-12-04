package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.core.query.model.ProductView;
import net.ijupiter.trading.core.query.repositories.ProductViewRepository;
import net.ijupiter.trading.api.product.events.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 产品事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventHandler {
    
    private final ProductViewRepository productViewRepository;
    
    @EventHandler
    public void on(ProductCreatedEvent event) {
        log.debug("处理产品创建事件: {}", event.getProductId());
        
        ProductView productView = ProductView.builder()
                .productId(event.getProductId())
                .productCode(event.getProductCode())
                .productName(event.getProductName())
                .productType(event.getProductType().name())
                .status(event.getStatus().name())
                .market("MAIN")
                .currency("CNY")
                .minQuantity(event.getMinTradeUnit())
                .maxQuantity(event.getMaxTradeUnit())
                .quantityPrecision(2)
                .pricePrecision(2)
                .limitUpPrice(calculateLimitUpPrice(event.getIssuePrice()))
                .limitDownPrice(calculateLimitDownPrice(event.getIssuePrice()))
                .previousClose(event.getIssuePrice())
                .latestPrice(event.getIssuePrice())
                .createTime(event.getCreateTime())
                .updateTime(event.getCreateTime())
                .build();
                
        productViewRepository.save(productView);
    }
    
//    @EventHandler
//    public void on(ProductStatusChangedEvent event) {
//        log.debug("处理产品状态变更事件: {}", event.getProductId());
//
//        productViewRepository.findById(event.getProductId())
//                .ifPresent(productView -> {
//                    productView.setStatus(event.getNewStatus().name());
//                    productView.setUpdateTime(LocalDateTime.now());
//                    productViewRepository.save(productView);
//                });
//    }
//
//    @EventHandler
//    public void on(ProductPriceUpdatedEvent event) {
//        log.debug("处理产品价格更新事件: {}", event.getProductId());
//
//        productViewRepository.findById(event.getProductId())
//                .ifPresent(productView -> {
//                    productView.setLatestPrice(event.getNewPrice());
//                    productView.setPreviousClose(event.getOldPrice());
//                    productView.setLimitUpPrice(calculateLimitUpPrice(event.getNewPrice()));
//                    productView.setLimitDownPrice(calculateLimitDownPrice(event.getNewPrice()));
//                    productView.setUpdateTime(LocalDateTime.now());
//                    productViewRepository.save(productView);
//                });
//    }
    
    private BigDecimal calculateLimitUpPrice(BigDecimal price) {
        // 涨停价 = 前收盘价 * (1 + 涨跌幅限制)，这里假设涨跌幅限制为10%
        return price.multiply(BigDecimal.valueOf(1.1));
    }
    
    private BigDecimal calculateLimitDownPrice(BigDecimal price) {
        // 跌停价 = 前收盘价 * (1 - 涨跌幅限制)，这里假设涨跌幅限制为10%
        return price.multiply(BigDecimal.valueOf(0.9));
    }
}