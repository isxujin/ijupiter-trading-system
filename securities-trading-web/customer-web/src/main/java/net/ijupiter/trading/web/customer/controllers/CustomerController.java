package net.ijupiter.trading.web.customer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.customer.commands.*;
import net.ijupiter.trading.api.customer.dtos.*;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.api.customer.services.CustomerService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.models.Result;
 
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 客户管理控制器
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    /**
     * 客户管理页面
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('customer:view')")
    public String customerListPage(Model model) {
        model.addAttribute("title", "客户管理");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        return "customer/list";
    }

    /**
     * 客户详情页面
     */
    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('customer:view')")
    public String customerDetailPage(@PathVariable String id, Model model) {
        model.addAttribute("title", "客户详情");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        model.addAttribute("customerId", id);
        return "customer/detail";
    }

    /**
     * 交易账户管理页面
     */
    @GetMapping("/trading-account/{customerId}")
    @PreAuthorize("hasAuthority('customer:account:view')")
    public String tradingAccountPage(@PathVariable String customerId, Model model) {
        model.addAttribute("title", "交易账户管理");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        model.addAttribute("customerId", customerId);
        return "customer/trading-account";
    }

    /**
     * 资金账户管理页面
     */
    @GetMapping("/fund-account/{customerId}")
    @PreAuthorize("hasAuthority('customer:account:view')")
    public String fundAccountPage(@PathVariable String customerId, Model model) {
        model.addAttribute("title", "资金账户管理");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        model.addAttribute("customerId", customerId);
        return "customer/fund-account";
    }

    /**
     * 银行卡管理页面
     */
    @GetMapping("/bank-card/{customerId}")
    @PreAuthorize("hasAuthority('customer:card:view')")
    public String bankCardPage(@PathVariable String customerId, Model model) {
        model.addAttribute("title", "银行卡管理");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        model.addAttribute("customerId", customerId);
        return "customer/bank-card";
    }

    /**
     * 交易所账号管理页面
     */
    @GetMapping("/exchange-account/{tradingAccountId}")
    @PreAuthorize("hasAuthority('customer:exchange:view')")
    public String exchangeAccountPage(@PathVariable String tradingAccountId, Model model) {
        model.addAttribute("title", "交易所账号管理");
        model.addAttribute("sidebarItems", getCommonSidebarItems("customer"));
        model.addAttribute("tradingAccountId", tradingAccountId);
        return "customer/exchange-account";
    }

    // REST API 接口

    /**
     * 查询客户列表
     */
    @GetMapping("/api/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:view')")
    public Result<List<CustomerDTO>> getCustomerList(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword) {
        try {
            List<CustomerDTO> customers;
            
            if (status != null && !status.isEmpty()) {
                CustomerStatus customerStatus = CustomerStatus.valueOf(status);
                customers = customerService.getCustomersByStatus(customerStatus);
            } else {
                customers = customerService.getAllCustomers();
            }
            
            return Result.success(customers);
        } catch (Exception e) {
            log.error("查询客户列表失败", e);
            return Result.fail("查询客户列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询客户详情
     */
    @GetMapping("/api/detail/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:view')")
    public Result<CustomerDTO> getCustomerDetail(@PathVariable String id) {
        try {
            Optional<CustomerDTO> customer = customerService.getCustomerById(id);
            return customer.map(Result::success)
                    .orElse(Result.fail("客户不存在: " + id));
        } catch (Exception e) {
            log.error("查询客户详情失败", e);
            return Result.fail("查询客户详情失败: " + e.getMessage());
        }
    }

    /**
     * 创建客户
     */
    @PostMapping("/api/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:create')")
    public Result<String> createCustomer(@RequestBody CreateCustomerCommand command) {
        try {
            String customerId = customerService.createCustomer(command);
            return Result.success(customerId);
        } catch (Exception e) {
            log.error("创建客户失败", e);
            return Result.fail("创建客户失败: " + e.getMessage());
        }
    }

    /**
     * 更新客户信息
     */
    @PostMapping("/api/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:update')")
    public Result<Void> updateCustomer(@RequestBody UpdateCustomerCommand command) {
        try {
            customerService.updateCustomer(command);
            return Result.success();
        } catch (Exception e) {
            log.error("更新客户信息失败", e);
            return Result.fail("更新客户信息失败: " + e.getMessage());
        }
    }

    /**
     * 冻结客户
     */
    @PostMapping("/api/freeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:freeze')")
    public Result<Void> freezeCustomer(@RequestBody FreezeCustomerCommand command) {
        try {
            customerService.freezeCustomer(command);
            return Result.success();
        } catch (Exception e) {
            log.error("冻结客户失败", e);
            return Result.fail("冻结客户失败: " + e.getMessage());
        }
    }

    /**
     * 解冻客户
     */
    @PostMapping("/api/unfreeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:unfreeze')")
    public Result<Void> unfreezeCustomer(@RequestBody UnfreezeCustomerCommand command) {
        try {
            customerService.unfreezeCustomer(command);
            return Result.success();
        } catch (Exception e) {
            log.error("解冻客户失败", e);
            return Result.fail("解冻客户失败: " + e.getMessage());
        }
    }

    /**
     * 注销客户
     */
    @PostMapping("/api/cancel")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:cancel')")
    public Result<Void> cancelCustomer(@RequestBody CancelCustomerCommand command) {
        try {
            customerService.cancelCustomer(command);
            return Result.success();
        } catch (Exception e) {
            log.error("注销客户失败", e);
            return Result.fail("注销客户失败: " + e.getMessage());
        }
    }

    // 交易账户相关API

    /**
     * 查询客户的交易账户列表
     */
    @GetMapping("/api/trading-account/list/{customerId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:view')")
    public Result<List<TradingAccountDTO>> getTradingAccountList(@PathVariable String customerId) {
        try {
            List<TradingAccountDTO> accounts = customerService.getTradingAccountBasicInfoByCustomerId(customerId);
            return Result.success(accounts);
        } catch (Exception e) {
            log.error("查询交易账户列表失败", e);
            return Result.fail("查询交易账户列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询交易账户详情
     */
    @GetMapping("/api/trading-account/detail/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:view')")
    public Result<TradingAccountDTO> getTradingAccountDetail(@PathVariable String id) {
        try {
            Optional<TradingAccountDTO> account = customerService.getTradingAccountBasicInfoById(id);
            return account.map(Result::success)
                    .orElse(Result.fail("交易账户不存在: " + id));
        } catch (Exception e) {
            log.error("查询交易账户详情失败", e);
            return Result.fail("查询交易账户详情失败: " + e.getMessage());
        }
    }

    /**
     * 创建交易账户
     */
    @PostMapping("/api/trading-account/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:create')")
    public Result<String> createTradingAccount(@RequestBody CreateTradingAccountCommand command) {
        try {
            String accountId = customerService.createTradingAccount(command);
            return Result.success(accountId);
        } catch (Exception e) {
            log.error("创建交易账户失败", e);
            return Result.fail("创建交易账户失败: " + e.getMessage());
        }
    }

    /**
     * 冻结交易账户
     */
    @PostMapping("/api/trading-account/freeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:freeze')")
    public Result<Void> freezeTradingAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.freezeTradingAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("冻结交易账户失败", e);
            return Result.fail("冻结交易账户失败: " + e.getMessage());
        }
    }

    /**
     * 解冻交易账户
     */
    @PostMapping("/api/trading-account/unfreeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:unfreeze')")
    public Result<Void> unfreezeTradingAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.unfreezeTradingAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("解冻交易账户失败", e);
            return Result.fail("解冻交易账户失败: " + e.getMessage());
        }
    }

    /**
     * 注销交易账户
     */
    @PostMapping("/api/trading-account/close")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:close')")
    public Result<Void> closeTradingAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.closeTradingAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("注销交易账户失败", e);
            return Result.fail("注销交易账户失败: " + e.getMessage());
        }
    }

    // 资金账户相关API

    /**
     * 查询客户的资金账户列表
     */
    @GetMapping("/api/fund-account/list/{customerId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:view')")
    public Result<List<FundAccountDTO>> getFundAccountList(@PathVariable String customerId) {
        try {
            List<FundAccountDTO> accounts = customerService.getFundAccountBasicInfoByCustomerId(customerId);
            return Result.success(accounts);
        } catch (Exception e) {
            log.error("查询资金账户列表失败", e);
            return Result.fail("查询资金账户列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询资金账户详情
     */
    @GetMapping("/api/fund-account/detail/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:view')")
    public Result<FundAccountDTO> getFundAccountDetail(@PathVariable String id) {
        try {
            Optional<FundAccountDTO> account = customerService.getFundAccountBasicInfoById(id);
            return account.map(Result::success)
                    .orElse(Result.fail("资金账户不存在: " + id));
        } catch (Exception e) {
            log.error("查询资金账户详情失败", e);
            return Result.fail("查询资金账户详情失败: " + e.getMessage());
        }
    }

    /**
     * 创建资金账户
     */
    @PostMapping("/api/fund-account/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:create')")
    public Result<String> createFundAccount(@RequestBody CreateFundAccountCommand command) {
        try {
            String accountId = customerService.createFundAccount(command);
            return Result.success(accountId);
        } catch (Exception e) {
            log.error("创建资金账户失败", e);
            return Result.fail("创建资金账户失败: " + e.getMessage());
        }
    }

    /**
     * 冻结资金账户
     */
    @PostMapping("/api/fund-account/freeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:freeze')")
    public Result<Void> freezeFundAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.freezeFundAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("冻结资金账户失败", e);
            return Result.fail("冻结资金账户失败: " + e.getMessage());
        }
    }

    /**
     * 解冻资金账户
     */
    @PostMapping("/api/fund-account/unfreeze")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:unfreeze')")
    public Result<Void> unfreezeFundAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.unfreezeFundAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("解冻资金账户失败", e);
            return Result.fail("解冻资金账户失败: " + e.getMessage());
        }
    }

    /**
     * 注销资金账户
     */
    @PostMapping("/api/fund-account/close")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:account:close')")
    public Result<Void> closeFundAccount(@RequestParam String accountId, @RequestParam String reason) {
        try {
            customerService.closeFundAccount(accountId, reason);
            return Result.success();
        } catch (Exception e) {
            log.error("注销资金账户失败", e);
            return Result.fail("注销资金账户失败: " + e.getMessage());
        }
    }

    /**
     * 绑定银行卡
     */
    @PostMapping("/api/bank-card/bind")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:card:bind')")
    public Result<String> bindBankCard(@RequestParam String fundAccountId, @RequestBody String bankCardId) {
        try {
            return Result.success(bankCardId);
        } catch (Exception e) {
            log.error("绑定银行卡失败", e);
            return Result.fail("绑定银行卡失败: " + e.getMessage());
        }
    }

    /**
     * 解绑银行卡
     */
    @PostMapping("/api/bank-card/unbind")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:card:unbind')")
    public Result<Void> unbindBankCard(@RequestParam String bankCardId, @RequestParam String reason) {
        try {
            return Result.success();
        } catch (Exception e) {
            log.error("解绑银行卡失败", e);
            return Result.fail("解绑银行卡失败: " + e.getMessage());
        }
    }

    /**
     * 绑定交易所账号
     */
    @PostMapping("/api/exchange-account/bind")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:exchange:bind')")
    public Result<String> bindExchangeAccount(@RequestParam String tradingAccountId, @RequestBody String exchangeAccountId) {
        try {
            return Result.success(exchangeAccountId);
        } catch (Exception e) {
            log.error("绑定交易所账号失败", e);
            return Result.fail("绑定交易所账号失败: " + e.getMessage());
        }
    }

    /**
     * 解绑交易所账号
     */
    @PostMapping("/api/exchange-account/unbind")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:exchange:unbind')")
    public Result<Void> unbindExchangeAccount(@RequestParam String exchangeAccountId, @RequestParam String reason) {
        try {
            return Result.success();
        } catch (Exception e) {
            log.error("解绑交易所账号失败", e);
            return Result.fail("解绑交易所账号失败: " + e.getMessage());
        }
    }

    /**
     * 获取客户状态枚举值
     */
    @GetMapping("/api/status/enum")
    @ResponseBody
    @PreAuthorize("hasAuthority('customer:view')")
    public Result<List<CustomerStatus>> getCustomerStatusEnum() {
        try {
            List<CustomerStatus> statuses = Arrays.asList(CustomerStatus.values());
            return Result.success(statuses);
        } catch (Exception e) {
            log.error("获取客户状态枚举值失败", e);
            return Result.fail("获取客户状态枚举值失败: " + e.getMessage());
        }
    }
}