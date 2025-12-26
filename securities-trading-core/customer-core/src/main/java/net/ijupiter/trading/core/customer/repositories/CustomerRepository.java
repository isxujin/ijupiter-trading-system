package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.dtos.CustomerAccountDTO;

import java.util.List;
import java.util.Optional;

/**
 * 客户仓储接口
 */
public interface CustomerRepository {
    
    /**
     * 保存客户
     * @param customerDTO 客户信息
     * @return 保存后的客户信息
     */
    CustomerDTO save(CustomerDTO customerDTO);
    
    /**
     * 根据ID查找客户
     * @param id 客户ID
     * @return 客户信息
     */
    Optional<CustomerDTO> findById(Long id);
    
    /**
     * 根据客户编号查找客户
     * @param customerCode 客户编号
     * @return 客户信息
     */
    Optional<CustomerDTO> findByCustomerCode(String customerCode);
    
    /**
     * 获取所有客户
     * @return 客户列表
     */
    List<CustomerDTO> findAll();
    
    /**
     * 更新客户信息
     * @param customerDTO 客户信息
     * @return 更新后的客户信息
     */
    CustomerDTO update(CustomerDTO customerDTO);
    
    /**
     * 删除客户
     * @param id 客户ID
     */
    void deleteById(Long id);
    
    /**
     * 检查客户编号是否存在
     * @param customerCode 客户编号
     * @return 是否存在
     */
    boolean existsByCustomerCode(String customerCode);
    
    /**
     * 保存客户账户
     * @param accountDTO 客户账户信息
     * @return 保存后的客户账户信息
     */
    CustomerAccountDTO saveAccount(CustomerAccountDTO accountDTO);
    
    /**
     * 根据账户ID查找客户账户
     * @param accountId 账户ID
     * @return 客户账户信息
     */
    Optional<CustomerAccountDTO> findAccountById(Long accountId);
    
    /**
     * 根据账户编号查找客户账户
     * @param accountCode 账户编号
     * @return 客户账户信息
     */
    Optional<CustomerAccountDTO> findAccountByCode(String accountCode);
    
    /**
     * 获取客户的所有账户
     * @param customerId 客户ID
     * @return 客户账户列表
     */
    List<CustomerAccountDTO> findAccountsByCustomerId(Long customerId);
    
    /**
     * 更新客户账户
     * @param accountDTO 客户账户信息
     * @return 更新后的客户账户信息
     */
    CustomerAccountDTO updateAccount(CustomerAccountDTO accountDTO);
    
    /**
     * 删除客户账户
     * @param accountId 账户ID
     */
    void deleteAccountById(Long accountId);
}