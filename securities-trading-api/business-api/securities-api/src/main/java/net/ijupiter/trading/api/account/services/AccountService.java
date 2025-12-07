package net.ijupiter.trading.api.account.services;

import net.ijupiter.trading.api.account.commands.CloseAccountCommand;
import net.ijupiter.trading.api.account.commands.CreateAccountCommand;
import net.ijupiter.trading.api.account.commands.UpdateAccountCommand;
import net.ijupiter.trading.api.account.dtos.AccountDTO;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import net.ijupiter.trading.common.services.BaseService;

import java.util.List;

/**
 * 账户服务接口
 *
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface AccountService extends BaseService<AccountDTO, String> {

    String createAccount(CreateAccountCommand command);

    boolean updateAccount(UpdateAccountCommand command);

    boolean closeAccount(CloseAccountCommand command);

    AccountDTO getAccount(String accountId);

    List<AccountDTO> getAccountsByUserId(String userId);

    List<AccountDTO> getAccountsByType(String userId, AccountType accountType);

    List<AccountDTO> getAccountsByStatus(String userId, AccountStatus accountStatus);

    boolean accountExists(String accountId);

    boolean hasAccountType(String userId, AccountType accountType);

    boolean activateAccount(String accountId);

    boolean freezeAccount(String accountId, String reason);

    boolean unfreezeAccount(String accountId, String reason);
}
