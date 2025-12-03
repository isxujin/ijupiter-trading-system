package net.ijupiter.trading.api.account.services;

import net.ijupiter.trading.api.account.commands.CloseAccountCommand;
import net.ijupiter.trading.api.account.commands.CreateAccountCommand;
import net.ijupiter.trading.api.account.commands.UpdateAccountCommand;
import net.ijupiter.trading.api.account.dto.AccountDTO;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import java.util.List;

/**
 * 账户服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface AccountService {

    /**
     * 创建账户
     * 
     * @param command 创建账户命令
     * @return 账户ID
     */
    String createAccount(CreateAccountCommand command);

    /**
     * 更新账户
     * 
     * @param command 更新账户命令
     * @return 是否成功
     */
    boolean updateAccount(UpdateAccountCommand command);

    /**
     * 关闭账户
     * 
     * @param command 关闭账户命令
     * @return 是否成功
     */
    boolean closeAccount(CloseAccountCommand command);

    /**
     * 查询账户信息
     * 
     * @param accountId 账户ID
     * @return 账户信息
     */
    AccountDTO getAccount(String accountId);

    /**
     * 查询用户的所有账户
     * 
     * @param userId 用户ID
     * @return 账户列表
     */
    List<AccountDTO> getAccountsByUserId(String userId);

    /**
     * 根据账户类型查询账户
     * 
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 账户列表
     */
    List<AccountDTO> getAccountsByType(String userId, AccountType accountType);

    /**
     * 根据账户状态查询账户
     * 
     * @param userId 用户ID
     * @param accountStatus 账户状态
     * @return 账户列表
     */
    List<AccountDTO> getAccountsByStatus(String userId, AccountStatus accountStatus);

    /**
     * 检查账户是否存在
     * 
     * @param accountId 账户ID
     * @return 是否存在
     */
    boolean accountExists(String accountId);

    /**
     * 检查用户是否已有指定类型的账户
     * 
     * @param userId 用户ID
     * @param accountType 账户类型
     * @return 是否已有
     */
    boolean hasAccountType(String userId, AccountType accountType);

    /**
     * 激活账户
     * 
     * @param accountId 账户ID
     * @return 是否成功
     */
    boolean activateAccount(String accountId);

    /**
     * 冻结账户
     * 
     * @param accountId 账户ID
     * @param reason 冻结原因
     * @return 是否成功
     */
    boolean freezeAccount(String accountId, String reason);

    /**
     * 解冻账户
     * 
     * @param accountId 账户ID
     * @param reason 解冻原因
     * @return 是否成功
     */
    boolean unfreezeAccount(String accountId, String reason);
}