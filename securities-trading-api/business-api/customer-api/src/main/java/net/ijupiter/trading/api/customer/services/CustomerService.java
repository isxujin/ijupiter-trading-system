package net.ijupiter.trading.api.customer.services;

import net.ijupiter.trading.api.customer.commands.*;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
// Deprecated full-account DTOs removed — use BasicInfo/Balance DTOs instead
import net.ijupiter.trading.api.customer.dtos.FundAccountDTO;
import net.ijupiter.trading.api.customer.dtos.FundAccountBalanceDTO;
import net.ijupiter.trading.api.customer.dtos.TradingAccountDTO;
import net.ijupiter.trading.api.customer.dtos.TradingAccountPositionDTO;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;


import java.util.List;
import java.util.Optional;

/**
 * 客户服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface CustomerService {

    // 客户管理方法
    /**
     * 创建客户
     * 
     * @param command 创建客户命令
     * @return 客户ID
     */
    String createCustomer(CreateCustomerCommand command);

    /**
     * 更新客户信息
     * 
     * @param command 更新客户信息命令
     */
    void updateCustomer(UpdateCustomerCommand command);

    /**
     * 冻结客户
     * 
     * @param command 冻结客户命令
     */
    void freezeCustomer(FreezeCustomerCommand command);

    /**
     * 解冻客户
     * 
     * @param command 解冻客户命令
     */
    void unfreezeCustomer(UnfreezeCustomerCommand command);

    /**
     * 注销客户
     * 
     * @param command 注销客户命令
     */
    void cancelCustomer(CancelCustomerCommand command);

    /**
     * 客户登录
     * 
     * @param customerId 客户ID
     * @return 是否成功
     */
    boolean login(String customerId);

    /**
     * 客户登出
     * 
     * @param customerId 客户ID
     */
    void logout(String customerId);

    /**
     * 根据客户ID查询客户信息
     * 
     * @param customerId 客户ID
     * @return 客户信息
     */
    Optional<CustomerDTO> getCustomerById(String customerId);

    /**
     * 根据客户编号查询客户信息
     * 
     * @param customerCode 客户编号
     * @return 客户信息
     */
    Optional<CustomerDTO> getCustomerByCode(String customerCode);

    /**
     * 根据客户状态查询客户列表
     * 
     * @param status 客户状态
     * @return 客户列表
     */
    List<CustomerDTO> getCustomersByStatus(CustomerStatus status);

    /**
     * 查询所有客户
     * 
     * @return 客户列表
     */
    List<CustomerDTO> getAllCustomers();

    // 交易账户管理方法
    /**
     * 创建交易账户（包含交易所账号信息）
     * 
     * @param command 创建交易账户命令
     * @return 交易账户ID
     */
    String createTradingAccount(CreateTradingAccountCommand command);

    /**
     * 注销交易账户
     * 
     * @param accountId 交易账户ID
     * @param reason 注销原因
     */
    void closeTradingAccount(String accountId, String reason);

    /**
     * 冻结交易账户
     * 
     * @param accountId 交易账户ID
     * @param reason 冻结原因
     */
    void freezeTradingAccount(String accountId, String reason);

    /**
     * 解冻交易账户
     * 
     * @param accountId 交易账户ID
     * @param reason 解冻原因
     */
    void unfreezeTradingAccount(String accountId, String reason);

    /**
     * 更新交易所账号信息
     * 
     * @param accountId 交易账户ID
     * @param exchangeAccountNumber 交易所账号
     * @param tradingPassword 交易密码
     * @param fundPassword 资金密码
     * @param apiKey API密钥
     * @param apiSecret API密钥密钥
     */
    void updateExchangeAccount(String accountId, String exchangeAccountNumber, 
                              String tradingPassword, String fundPassword,
                              String apiKey, String apiSecret);

    // Removed: getTradingAccountsByCustomerId / getTradingAccountById
    // Use getTradingAccountBasicInfoByCustomerId / getTradingAccountBasicInfoById

    // 交易账户基本信息和持仓信息拆分查询方法
    /**
     * 根据交易账户ID查询交易账户基本信息
     * 
     * @param accountId 交易账户ID
     * @return 交易账户基本信息
     */
    Optional<TradingAccountDTO> getTradingAccountBasicInfoById(String accountId);

    /**
     * 根据客户ID查询交易账户基本信息列表
     * 
     * @param customerId 客户ID
     * @return 交易账户基本信息列表
     */
    List<TradingAccountDTO> getTradingAccountBasicInfoByCustomerId(String customerId);

    /**
     * 根据交易账户ID查询交易账户持仓信息
     * 
     * @param accountId 交易账户ID
     * @return 交易账户持仓信息
     */
    Optional<TradingAccountPositionDTO> getTradingAccountPositionById(String accountId);

    /**
     * 根据客户ID查询交易账户持仓信息列表
     * 
     * @param customerId 客户ID
     * @return 交易账户持仓信息列表
     */
    List<TradingAccountPositionDTO> getTradingAccountPositionByCustomerId(String customerId);

    // 资金账户管理方法
    /**
     * 创建资金账户（包含银行卡信息）
     * 
     * @param command 创建资金账户命令
     * @return 资金账户ID
     */
    String createFundAccount(CreateFundAccountCommand command);

    /**
     * 注销资金账户
     * 
     * @param accountId 资金账户ID
     * @param reason 注销原因
     */
    void closeFundAccount(String accountId, String reason);

    /**
     * 冻结资金账户
     * 
     * @param accountId 资金账户ID
     * @param reason 冻结原因
     */
    void freezeFundAccount(String accountId, String reason);

    /**
     * 解冻资金账户
     * 
     * @param accountId 资金账户ID
     * @param reason 解冻原因
     */
    void unfreezeFundAccount(String accountId, String reason);

    /**
     * 更新银行卡信息
     * 
     * @param accountId 资金账户ID
     * @param bankCardNumber 银行卡号
     * @param bankCode 银行代码
     * @param bankName 银行名称
     * @param holderName 持卡人姓名
     */
    void updateBankCard(String accountId, String bankCardNumber, String bankCode,
                       String bankName, String holderName);

    // Removed: getFundAccountsByCustomerId / getFundAccountById
    // Use getFundAccountBasicInfoByCustomerId / getFundAccountBasicInfoById and
    // getFundAccountBalanceByCustomerId / getFundAccountBalanceById

    // 资金账户基本信息和余额信息拆分查询方法
    /**
     * 根据资金账户ID查询资金账户基本信息
     * 
     * @param accountId 资金账户ID
     * @return 资金账户基本信息
     */
    Optional<FundAccountDTO> getFundAccountBasicInfoById(String accountId);

    /**
     * 根据客户ID查询资金账户基本信息列表
     * 
     * @param customerId 客户ID
     * @return 资金账户基本信息列表
     */
    List<FundAccountDTO> getFundAccountBasicInfoByCustomerId(String customerId);

    /**
     * 根据资金账户ID查询资金账户余额信息
     * 
     * @param accountId 资金账户ID
     * @return 资金账户余额信息
     */
    Optional<FundAccountBalanceDTO> getFundAccountBalanceById(String accountId);

    /**
     * 根据客户ID查询资金账户余额信息列表
     * 
     * @param customerId 客户ID
     * @return 资金账户余额信息列表
     */
    List<FundAccountBalanceDTO> getFundAccountBalanceByCustomerId(String customerId);
}