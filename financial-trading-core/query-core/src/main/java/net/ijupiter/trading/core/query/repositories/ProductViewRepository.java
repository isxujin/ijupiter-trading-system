package net.ijupiter.trading.core.query.repository;

import net.ijupiter.trading.core.query.model.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 产品视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, String> {
    
    /**
     * 根据产品代码查询产品
     */
    Optional<ProductView> findByProductCode(String productCode);
    
    /**
     * 根据产品类型查询产品列表
     */
    List<ProductView> findByProductType(String productType);
    
    /**
     * 根据产品状态查询产品列表
     */
    List<ProductView> findByStatus(String status);
    
    /**
     * 根据交易市场查询产品列表
     */
    List<ProductView> findByMarket(String market);
    
    /**
     * 根据币种查询产品列表
     */
    List<ProductView> findByCurrency(String currency);
    
    /**
     * 根据产品类型和状态查询产品列表
     */
    List<ProductView> findByProductTypeAndStatus(String productType, String status);
    
    /**
     * 根据交易市场和状态查询产品列表
     */
    List<ProductView> findByMarketAndStatus(String market, String status);
    
    /**
     * 统计产品数量
     */
    @Query("SELECT COUNT(p) FROM ProductView p WHERE p.productType = :productType")
    long countByProductType(@Param("productType") String productType);
}