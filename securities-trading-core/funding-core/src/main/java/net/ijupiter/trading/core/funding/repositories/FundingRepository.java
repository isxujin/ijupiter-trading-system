package net.ijupiter.trading.core.funding.repositories;

import net.ijupiter.trading.api.funding.dtos.FundingAccountDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransferDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * 资金仓储接口
 */
public interface FundingRepository {
    
    /**
     * 保存资金账户
     * @param accountDTO 账户信息
     * @return 保存后的账户信息
     */
    FundingAccountDTO saveAccount(FundingAccountDTO accountDTO);
    
    /**
     * 根据ID获取资金账户
     * @param id 账户ID
     * @return 账户信息
     */
    Optional<FundingAccountDTO> findAccountById(Long id);
    
    /**
     * 根据账户编号获取资金账户
     * @param accountCode 账户编号
     * @return 账户信息
     */
    Optional<FundingAccountDTO> findAccountByCode(String accountCode);
    
    /**
     * 根据客户ID获取资金账户
     * @param customerId 客户ID
     * @return 资金账户列表
     */
    List<FundingAccountDTO> findAccountsByCustomerId(Long customerId);
    
    /**
     * 获取所有资金账户
     * @return 资金账户列表
     */
    List<FundingAccountDTO> findAllAccounts();
    
    /**
     * 更新资金账户
     * @param accountDTO 账户信息
     * @return 更新后的账户信息
     */
    FundingAccountDTO updateAccount(FundingAccountDTO accountDTO);
    
    /**
     * 删除资金账户
     * @param id 账户ID
     */
    void deleteAccountById(Long id);
    
    /**
     * 保存资金转账
     * @param transferDTO 转账信息
     * @return 保存后的转账信息
     */
    FundingTransferDTO saveTransfer(FundingTransferDTO transferDTO);
    
    /**
     * 根据转账编号获取资金转账
     * @param transferCode 转账编号
     * @return 转账信息
     */
    Optional<FundingTransferDTO> findTransferByCode(String transferCode);
    
    /**
     * 根据客户ID获取资金转账记录
     * @param customerId 客户ID
     * @return 资金转账记录列表
     */
    List<FundingTransferDTO> findTransfersByCustomerId(Long customerId);
    
    /**
     * 更新资金转账
     * @param transferDTO 转账信息
     * @return 更新后的转账信息
     */
    FundingTransferDTO updateTransfer(FundingTransferDTO transferDTO);
    
    /**
     * 保存资金流水
     * @param transactionDTO 流水信息
     * @return 保存后的流水信息
     */
    FundingTransactionDTO saveTransaction(FundingTransactionDTO transactionDTO);
    
    /**
     * 根据账户编号获取资金流水
     * @param accountCode 账户编号
     * @return 资金流水记录列表
     */
    List<FundingTransactionDTO> findTransactionsByAccountCode(String accountCode);
    
    /**
     * 根据客户ID获取资金流水
     * @param customerId 客户ID
     * @return 资金流水记录列表
     */
    List<FundingTransactionDTO> findTransactionsByCustomerId(Long customerId);
    
    /**
     * 根据流水编号获取资金流水
     * @param transactionCode 流水编号
     * @return 流水信息
     */
    Optional<FundingTransactionDTO> findTransactionByCode(String transactionCode);
}