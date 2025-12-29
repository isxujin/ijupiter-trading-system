package net.ijupiter.trading.api.customer.services;

import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.common.services.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * 客户服务接口
 */
public interface CustomerService extends BaseService<CustomerDTO, Long> {
    
    /**
     * 获取所有客户
     * @return 客户列表
     */
    List<CustomerDTO> findAll();
    
    /**
     * 根据ID获取客户
     * @param id 客户ID
     * @return 客户信息
     */
    Optional<CustomerDTO> findById(Long id);
    
    /**
     * 根据客户编号获取客户
     * @param customerCode 客户编号
     * @return 客户信息
     */
    Optional<CustomerDTO> findByCustomerCode(String customerCode);
    
    /**
     * 检查客户编号是否存在
     * @param customerCode 客户编号
     * @return 是否存在
     */
    boolean existsByCustomerCode(String customerCode);
    
    /**
     * 获取客户统计信息
     * @return 统计信息
     */
    CustomerStatistics getCustomerStatistics();
    
    /**
     * 客户统计信息
     */
    class CustomerStatistics {
        private long totalCustomers;
        private long activeCustomers;
        private long frozenCustomers;
        private long closedCustomers;
        private long todayNewCustomers;
        
        public CustomerStatistics(long totalCustomers, long activeCustomers, long frozenCustomers, 
                                long closedCustomers, long todayNewCustomers) {
            this.totalCustomers = totalCustomers;
            this.activeCustomers = activeCustomers;
            this.frozenCustomers = frozenCustomers;
            this.closedCustomers = closedCustomers;
            this.todayNewCustomers = todayNewCustomers;
        }
        
        public long getTotalCustomers() { return totalCustomers; }
        public long getActiveCustomers() { return activeCustomers; }
        public long getFrozenCustomers() { return frozenCustomers; }
        public long getClosedCustomers() { return closedCustomers; }
        public long getTodayNewCustomers() { return todayNewCustomers; }
    }
}