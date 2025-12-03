package net.ijupiter.trading.api.fund.services;

import net.ijupiter.trading.api.fund.commands.CreateFundAccountCommand;
import net.ijupiter.trading.api.fund.commands.DepositFundCommand;
import net.ijupiter.trading.api.fund.commands.FreezeFundCommand;
import net.ijupiter.trading.api.fund.commands.UnfreezeFundCommand;
import net.ijupiter.trading.api.fund.commands.WithdrawFundCommand;
import net.ijupiter.trading.api.fund.dto.FundAccountDTO;
import net.ijupiter.trading.api.fund.dto.FundTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资金服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface FundService {

    /**
     * 创建资金账户
     * 
     * @param command 创建资金账户命令
     * @return 资金账户ID
     */
    String createFundAccount(CreateFundAccountCommand command);

    /**
     * 资金入金
     * 
     * @param command 入金命令
     * @return 是否成功
     */
    boolean depositFund(DepositFundCommand command);

    /**
     * 资金出金
     * 
     * @param command 出金命令
     * @return 是否成功
     */
    boolean withdrawFund(WithdrawFundCommand command);

    /**
     * 冻结资金
     * 
     * @param command 冻结资金命令
     * @return 是否成功
     */
    boolean freezeFund(FreezeFundCommand command);

    /**
     * 解冻资金
     * 
     * @param command 解冻资金命令
     * @return 是否成功
     */
    boolean unfreezeFund(UnfreezeFundCommand command);

    /**
     * 查询资金账户
     * 
     * @param fundAccountId 资金账户ID
     * @return 资金账户信息
     */
    FundAccountDTO getFundAccount(String fundAccountId);

    /**
     * 根据账户ID查询资金账户
     * 
     * @param accountId 账户ID
     * @return 资金账户信息
     */
    FundAccountDTO getFundAccountByAccountId(String accountId);

    /**
     * 查询交易记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    List<FundTransactionDTO> getTransactionsByTimeRange(String fundAccountId, 
                                                    LocalDateTime startTime, 
                                                    LocalDateTime endTime);

    /**
     * 查询入金记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 入金记录列表
     */
    List<FundTransactionDTO> getDepositsByTimeRange(String fundAccountId, 
                                                LocalDateTime startTime, 
                                                LocalDateTime endTime);

    /**
     * 查询出金记录
     * 
     * @param fundAccountId 资金账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 出金记录列表
     */
    List<FundTransactionDTO> getWithdrawalsByTimeRange(String fundAccountId, 
                                                  LocalDateTime startTime, 
                                                  LocalDateTime endTime);

    /**
     * 检查资金账户是否存在
     * 
     * @param fundAccountId 资金账户ID
     * @return 是否存在
     */
    boolean fundAccountExists(String fundAccountId);

    /**
     * 检查账户是否已有资金账户
     * 
     * @param accountId 账户ID
     * @return 是否已有
     */
    boolean hasFundAccount(String accountId);

    /**
     * 检查可用余额是否足够
     * 
     * @param fundAccountId 资金账户ID
     * @param amount 金额
     * @return 是否足够
     */
    boolean hasAvailableBalance(String fundAccountId, BigDecimal amount);

    /**
     * 查询账户余额
     * 
     * @param fundAccountId 资金账户ID
     * @return 账户余额
     */
    BigDecimal getBalance(String fundAccountId);

    /**
     * 查询可用余额
     * 
     * @param fundAccountId 资金账户ID
     * @return 可用余额
     */
    BigDecimal getAvailableBalance(String fundAccountId);

    /**
     * 查询冻结金额
     * 
     * @param fundAccountId 资金账户ID
     * @return 冻结金额
     */
    BigDecimal getFrozenAmount(String fundAccountId);

    /**
     * 冻结账户
     * 
     * @param fundAccountId 资金账户ID
     * @param reason 冻结原因
     * @return 是否成功
     */
    boolean freezeAccount(String fundAccountId, String reason);

    /**
     * 解冻账户
     * 
     * @param fundAccountId 资金账户ID
     * @param reason 解冻原因
     * @return 是否成功
     */
    boolean unfreezeAccount(String fundAccountId, String reason);

    /**
     * 批量冻结资金
     * 
     * @param fundAccountIds 资金账户ID列表
     * @param amount 冻结金额
     * @param reason 冻结原因
     * @return 是否成功
     */
    boolean batchFreezeFunds(List<String> fundAccountIds, BigDecimal amount, String reason);

    /**
     * 批量解冻资金
     * 
     * @param fundAccountIds 资金账户ID列表
     * @param amount 解冻金额
     * @param reason 解冻原因
     * @return 是否成功
     */
    boolean batchUnfreezeFunds(List<String> fundAccountIds, BigDecimal amount, String reason);
}