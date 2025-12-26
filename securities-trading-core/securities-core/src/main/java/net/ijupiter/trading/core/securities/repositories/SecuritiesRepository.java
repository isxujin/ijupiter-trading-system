package net.ijupiter.trading.core.securities.repositories;

import net.ijupiter.trading.api.securities.dtos.SecuritiesAccountDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesPositionDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesTransferDTO;

import java.util.List;
import java.util.Optional;

/**
 * 证券仓储接口
 */
public interface SecuritiesRepository {
    
    /**
     * 保存证券账户
     * @param accountDTO 账户信息
     * @return 保存后的账户信息
     */
    SecuritiesAccountDTO saveAccount(SecuritiesAccountDTO accountDTO);
    
    /**
     * 根据ID获取证券账户
     * @param id 账户ID
     * @return 账户信息
     */
    Optional<SecuritiesAccountDTO> findAccountById(Long id);
    
    /**
     * 根据账户编号获取证券账户
     * @param accountCode 账户编号
     * @return 账户信息
     */
    Optional<SecuritiesAccountDTO> findAccountByCode(String accountCode);
    
    /**
     * 根据客户ID获取证券账户
     * @param customerId 客户ID
     * @return 证券账户列表
     */
    List<SecuritiesAccountDTO> findAccountsByCustomerId(Long customerId);
    
    /**
     * 获取所有证券账户
     * @return 证券账户列表
     */
    List<SecuritiesAccountDTO> findAllAccounts();
    
    /**
     * 更新证券账户
     * @param accountDTO 账户信息
     * @return 更新后的账户信息
     */
    SecuritiesAccountDTO updateAccount(SecuritiesAccountDTO accountDTO);
    
    /**
     * 删除证券账户
     * @param id 账户ID
     */
    void deleteAccountById(Long id);
    
    /**
     * 保存证券持仓
     * @param positionDTO 持仓信息
     * @return 保存后的持仓信息
     */
    SecuritiesPositionDTO savePosition(SecuritiesPositionDTO positionDTO);
    
    /**
     * 根据ID获取证券持仓
     * @param id 持仓ID
     * @return 持仓信息
     */
    Optional<SecuritiesPositionDTO> findPositionById(Long id);
    
    /**
     * 根据账户编号获取证券持仓
     * @param accountCode 账户编号
     * @return 证券持仓列表
     */
    List<SecuritiesPositionDTO> findPositionsByAccountCode(String accountCode);
    
    /**
     * 根据客户ID获取证券持仓
     * @param customerId 客户ID
     * @return 证券持仓列表
     */
    List<SecuritiesPositionDTO> findPositionsByCustomerId(Long customerId);
    
    /**
     * 更新证券持仓
     * @param positionDTO 持仓信息
     * @return 更新后的持仓信息
     */
    SecuritiesPositionDTO updatePosition(SecuritiesPositionDTO positionDTO);
    
    /**
     * 删除证券持仓
     * @param id 持仓ID
     */
    void deletePositionById(Long id);
    
    /**
     * 保存证券转托管
     * @param transferDTO 转托管信息
     * @return 保存后的转托管信息
     */
    SecuritiesTransferDTO saveTransfer(SecuritiesTransferDTO transferDTO);
    
    /**
     * 根据转托管编号获取证券转托管
     * @param transferCode 转托管编号
     * @return 转托管信息
     */
    Optional<SecuritiesTransferDTO> findTransferByCode(String transferCode);
    
    /**
     * 根据客户ID获取证券转托管记录
     * @param customerId 客户ID
     * @return 证券转托管记录列表
     */
    List<SecuritiesTransferDTO> findTransfersByCustomerId(Long customerId);
    
    /**
     * 更新证券转托管
     * @param transferDTO 转托管信息
     * @return 更新后的转托管信息
     */
    SecuritiesTransferDTO updateTransfer(SecuritiesTransferDTO transferDTO);
}