package net.ijupiter.trading.core.product.aggregates;

import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.product.commands.*;
import net.ijupiter.trading.api.product.events.*;
import net.ijupiter.trading.api.product.enums.ProductStatus;
import net.ijupiter.trading.api.product.enums.ProductType;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

/**
 * 证券产品聚合
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String productCode;
    private String productName;
    private ProductType productType;
    private ProductStatus status;
    private BigDecimal faceValue;
    private BigDecimal issuePrice;
    private BigDecimal minTradeUnit;
    private BigDecimal maxTradeUnit;
    private BigDecimal tradeUnit;
    private BigDecimal priceTick;

    /**
     * 创建证券产品命令处理器
     * 
     * @param command 创建证券产品命令
     */
    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        ProductCreatedEvent event = new ProductCreatedEvent(
                command.getProductId(),
                command.getProductCode(),
                command.getProductName(),
                command.getProductType(),
                ProductStatus.PENDING,
                command.getFaceValue(),
                command.getIssuePrice(),
                command.getMinTradeUnit(),
                command.getMaxTradeUnit(),
                command.getTradeUnit(),
                command.getPriceTick()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 更新证券产品命令处理器
     * 
     * @param command 更新证券产品命令
     */
    @CommandHandler
    public void handle(UpdateProductCommand command) {
        if (!canUpdate()) {
            throw new IllegalStateException("证券产品无法更新，当前状态：" + status);
        }

        ProductUpdatedEvent event = new ProductUpdatedEvent(
                command.getProductId(),
                command.getProductName()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 停牌证券产品命令处理器
     * 
     * @param command 停牌证券产品命令
     */
    @CommandHandler
    public void handle(SuspendProductCommand command) {
        if (!canSuspend()) {
            throw new IllegalStateException("证券产品无法停牌，当前状态：" + status);
        }

        ProductSuspendedEvent event = new ProductSuspendedEvent(
                command.getProductId(),
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 复牌证券产品命令处理器
     * 
     * @param command 复牌证券产品命令
     */
    @CommandHandler
    public void handle(ResumeProductCommand command) {
        if (!canResume()) {
            throw new IllegalStateException("证券产品无法复牌，当前状态：" + status);
        }

        ProductResumedEvent event = new ProductResumedEvent(
                command.getProductId(),
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 退市证券产品命令处理器
     * 
     * @param command 退市证券产品命令
     */
    @CommandHandler
    public void handle(DelistProductCommand command) {
        if (!canDelist()) {
            throw new IllegalStateException("证券产品无法退市，当前状态：" + status);
        }

        ProductDelistedEvent event = new ProductDelistedEvent(
                command.getProductId(),
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 处理证券产品创建事件
     * 
     * @param event 证券产品创建事件
     */
    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.productCode = event.getProductCode();
        this.productName = event.getProductName();
        this.productType = event.getProductType();
        this.status = event.getStatus();
        this.faceValue = event.getFaceValue();
        this.issuePrice = event.getIssuePrice();
        this.minTradeUnit = event.getMinTradeUnit();
        this.maxTradeUnit = event.getMaxTradeUnit();
        this.tradeUnit = event.getTradeUnit();
        this.priceTick = event.getPriceTick();
    }

    /**
     * 处理证券产品更新事件
     * 
     * @param event 证券产品更新事件
     */
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.productName = event.getProductName();
    }

    /**
     * 处理证券产品停牌事件
     * 
     * @param event 证券产品停牌事件
     */
    @EventSourcingHandler
    public void on(ProductSuspendedEvent event) {
        this.status = ProductStatus.SUSPENDED;
    }

    /**
     * 处理证券产品复牌事件
     * 
     * @param event 证券产品复牌事件
     */
    @EventSourcingHandler
    public void on(ProductResumedEvent event) {
        this.status = ProductStatus.TRADING;
    }

    /**
     * 处理证券产品退市事件
     * 
     * @param event 证券产品退市事件
     */
    @EventSourcingHandler
    public void on(ProductDelistedEvent event) {
        this.status = ProductStatus.DELISTED;
    }

    /**
     * 判断证券产品是否可以更新
     * 
     * @return 是否可以更新
     */
    private boolean canUpdate() {
        return ProductStatus.PENDING.equals(status) || 
               ProductStatus.ISSUED.equals(status) || 
               ProductStatus.TRADING.equals(status);
    }

    /**
     * 判断证券产品是否可以停牌
     * 
     * @return 是否可以停牌
     */
    private boolean canSuspend() {
        return ProductStatus.ISSUED.equals(status) || 
               ProductStatus.TRADING.equals(status);
    }

    /**
     * 判断证券产品是否可以复牌
     * 
     * @return 是否可以复牌
     */
    private boolean canResume() {
        return ProductStatus.SUSPENDED.equals(status);
    }

    /**
     * 判断证券产品是否可以退市
     * 
     * @return 是否可以退市
     */
    private boolean canDelist() {
        return ProductStatus.ISSUED.equals(status) || 
               ProductStatus.TRADING.equals(status) || 
               ProductStatus.SUSPENDED.equals(status);
    }
}