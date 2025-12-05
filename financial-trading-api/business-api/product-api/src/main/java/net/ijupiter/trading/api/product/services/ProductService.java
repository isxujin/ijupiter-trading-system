package net.ijupiter.trading.api.product.services;

import net.ijupiter.trading.api.product.commands.CreateProductCommand;
import net.ijupiter.trading.api.product.commands.DelistProductCommand;
import net.ijupiter.trading.api.product.commands.ResumeProductCommand;
import net.ijupiter.trading.api.product.commands.SuspendProductCommand;
import net.ijupiter.trading.api.product.commands.UpdateProductCommand;
import net.ijupiter.trading.api.product.dtos.ProductDTO;
import net.ijupiter.trading.api.product.dtos.ProductTradingRules;
import net.ijupiter.trading.api.product.enums.ProductStatus;
import net.ijupiter.trading.api.product.enums.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 证券产品服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface ProductService {

    /**
     * 创建证券产品
     * 
     * @param command 创建证券产品命令
     * @return 产品ID
     */
    String createProduct(CreateProductCommand command);

    /**
     * 更新证券产品
     * 
     * @param command 更新证券产品命令
     * @return 是否成功
     */
    boolean updateProduct(UpdateProductCommand command);

    /**
     * 停牌证券产品
     * 
     * @param command 停牌证券产品命令
     * @return 是否成功
     */
    boolean suspendProduct(SuspendProductCommand command);

    /**
     * 复牌证券产品
     * 
     * @param command 复牌证券产品命令
     * @return 是否成功
     */
    boolean resumeProduct(ResumeProductCommand command);

    /**
     * 退市证券产品
     * 
     * @param command 退市证券产品命令
     * @return 是否成功
     */
    boolean delistProduct(DelistProductCommand command);

    /**
     * 查询证券产品
     * 
     * @param productId 产品ID
     * @return 产品信息
     */
    ProductDTO getProduct(String productId);

    /**
     * 根据产品代码查询证券产品
     * 
     * @param productCode 产品代码
     * @return 产品信息
     */
    ProductDTO getProductByCode(String productCode);

    /**
     * 根据产品状态查询证券产品
     * 
     * @param status 产品状态
     * @return 产品列表
     */
    List<ProductDTO> getProductsByStatus(ProductStatus status);

    /**
     * 根据产品类型查询证券产品
     * 
     * @param productType 产品类型
     * @return 产品列表
     */
    List<ProductDTO> getProductsByType(ProductType productType);

    /**
     * 根据产品状态和类型查询证券产品
     * 
     * @param status 产品状态
     * @param productType 产品类型
     * @return 产品列表
     */
    List<ProductDTO> getProductsByStatusAndType(ProductStatus status, ProductType productType);

    /**
     * 查询所有证券产品
     * 
     * @return 产品列表
     */
    List<ProductDTO> getAllProducts();

    /**
     * 查询可交易的证券产品
     * 
     * @return 产品列表
     */
    List<ProductDTO> getTradableProducts();

    /**
     * 检查证券产品是否存在
     * 
     * @param productId 产品ID
     * @return 是否存在
     */
    boolean productExists(String productId);

    /**
     * 检查产品代码是否存在
     * 
     * @param productCode 产品代码
     * @return 是否存在
     */
    boolean productCodeExists(String productCode);

    /**
     * 检查证券产品是否可交易
     * 
     * @param productId 产品ID
     * @return 是否可交易
     */
    boolean isTradable(String productId);

    /**
     * 查询指定时间范围内创建的证券产品
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 产品列表
     */
    List<ProductDTO> getProductsByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 批量停牌证券产品
     * 
     * @param productIds 产品ID列表
     * @param reason 停牌原因
     * @return 是否成功
     */
    boolean batchSuspendProducts(List<String> productIds, String reason);

    /**
     * 批量复牌证券产品
     * 
     * @param productIds 产品ID列表
     * @param reason 复牌原因
     * @return 是否成功
     */
    boolean batchResumeProducts(List<String> productIds, String reason);

    /**
     * 查询产品的最新价格
     * 
     * @param productId 产品ID
     * @return 最新价格
     */
    BigDecimal getLatestPrice(String productId);

    /**
     * 更新产品的最新价格
     * 
     * @param productId 产品ID
     * @param price 最新价格
     * @return 是否成功
     */
    boolean updateLatestPrice(String productId, BigDecimal price);

    /**
     * 查询产品的交易规则
     * 
     * @param productId 产品ID
     * @return 交易规则
     */
    ProductTradingRules getTradingRules(String productId);
}