package net.ijupiter.trading.web.funding.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.funding.dtos.FundingAccountDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransferDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransactionDTO;
import net.ijupiter.trading.api.funding.services.FundingService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.models.Result;
import net.ijupiter.trading.web.common.models.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/funding")
public class FundingController extends BaseController {
    
    @Autowired
    private FundingService fundingService;

    /**
     * 资金仪表盘页面
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("funding/dashboard");

        try {
            // 获取资金统计信息
            FundingService.FundingStatistics statistics = fundingService.getFundingStatistics();
            modelAndView.addObject("statistics", statistics);
            
            // 获取最近添加的账户(取前10个)
            List<FundingAccountDTO> recentAccounts = fundingService.findAll().stream()
                    .sorted((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()))
                    .limit(10)
                    .toList();
            modelAndView.addObject("recentAccounts", recentAccounts);
        } catch (Exception e) {
            log.error("获取资金仪表盘数据失败", e);
            modelAndView.addObject("error", "获取仪表盘数据失败");
        }

        modelAndView.addObject("activeModule", "funding");
        
        return modelAndView;
    }

    /**
     * 资金账户页面
     */
    @GetMapping("/account")
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView("funding/account");
        modelAndView.addObject("activeModule", "funding");
        
        return modelAndView;
    }
    
    /**
     * 出入金页面
     */
    @GetMapping("/transfer")
    public ModelAndView transfer() {
        ModelAndView modelAndView = new ModelAndView("funding/transfer");
        modelAndView.addObject("activeModule", "funding");
        
        return modelAndView;
    }

    /**
     * 获取资金统计数据
     */
    @GetMapping("/statistics")
    @ResponseBody
    public Result<FundingService.FundingStatistics> getStatistics() {
        try {
            FundingService.FundingStatistics statistics = fundingService.getFundingStatistics();
            return Result.success("获取统计数据成功", statistics);
        } catch (Exception e) {
            log.error("获取统计数据失败", e);
            return Result.fail("获取统计数据失败");
        }
    }

    /**
     * 获取资金账户列表数据
     */
    @GetMapping("/account/data")
    @ResponseBody
    public PageResult<FundingAccountDTO> getAccountList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String customerCode,
            @RequestParam(required = false) String accountCode,
            @RequestParam(required = false) Integer status) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (customerCode != null && !customerCode.trim().isEmpty()) {
                searchParams.put("customerCode", customerCode);
            }
            if (accountCode != null && !accountCode.trim().isEmpty()) {
                searchParams.put("accountCode", accountCode);
            }
            if (status != null) {
                searchParams.put("status", status);
            }
            
            // 使用基础服务方法获取所有数据
            List<FundingAccountDTO> allAccounts = fundingService.findAll();
            
            // 应用搜索条件过滤
            if (!searchParams.isEmpty()) {
                allAccounts = allAccounts.stream()
                    .filter(account -> {
                        boolean match = true;
                        if (searchParams.containsKey("customerCode") && 
                            account.getCustomerCode() != null &&
                            !account.getCustomerCode().contains((String) searchParams.get("customerCode"))) {
                            match = false;
                        }
                        if (searchParams.containsKey("accountCode") && 
                            account.getAccountCode() != null &&
                            !account.getAccountCode().contains((String) searchParams.get("accountCode"))) {
                            match = false;
                        }
                        if (searchParams.containsKey("status") && 
                            !account.getStatus().equals(searchParams.get("status"))) {
                            match = false;
                        }
                        return match;
                    })
                    .toList();
            }
            
            // 模拟分页数据
            int total = allAccounts.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FundingAccountDTO> pageData = fromIndex < total ? 
                allAccounts.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取资金账户列表失败", e);
            return PageResult.failPage("获取资金账户列表失败");
        }
    }

    /**
     * 获取资金转账列表数据
     */
    @GetMapping("/transfer/data")
    @ResponseBody
    public PageResult<FundingTransferDTO> getTransferList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String customerCode,
            @RequestParam(required = false) String transferCode,
            @RequestParam(required = false) Integer transferType,
            @RequestParam(required = false) Integer status) {
        
        try {
            List<FundingTransferDTO> allTransfers = List.of();
            
            // 如果指定了客户代码，获取该客户的转账记录
            if (customerCode != null && !customerCode.trim().isEmpty()) {
                // 先获取客户ID（这里简化处理，实际应该从客户服务获取）
                List<FundingAccountDTO> accounts = fundingService.findAll().stream()
                        .filter(a -> customerCode.equals(a.getCustomerCode()))
                        .toList();
                
                if (!accounts.isEmpty()) {
                    Long customerId = accounts.get(0).getCustomerId();
                    allTransfers = fundingService.findTransfersByCustomerId(customerId);
                }
            }
            
            // 应用搜索条件过滤
            if (transferCode != null && !transferCode.trim().isEmpty()) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getTransferCode() != null &&
                            transfer.getTransferCode().contains(transferCode))
                    .toList();
            }
            if (transferType != null) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getTransferType().equals(transferType))
                    .toList();
            }
            if (status != null) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getStatus().equals(status))
                    .toList();
            }
            
            // 模拟分页数据
            int total = allTransfers.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FundingTransferDTO> pageData = fromIndex < total ? 
                allTransfers.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取资金转账列表失败", e);
            return PageResult.failPage("获取资金转账列表失败");
        }
    }

    /**
     * 获取资金流水列表数据
     */
    @GetMapping("/transaction/data")
    @ResponseBody
    public PageResult<FundingTransactionDTO> getTransactionList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String accountCode,
            @RequestParam(required = false) Integer transactionType) {
        
        try {
            List<FundingTransactionDTO> allTransactions = List.of();
            
            // 如果指定了账户代码，获取该账户的流水记录
            if (accountCode != null && !accountCode.trim().isEmpty()) {
                allTransactions = fundingService.findTransactionsByAccountCode(accountCode);
            }
            
            // 应用交易类型过滤
            if (transactionType != null) {
                allTransactions = allTransactions.stream()
                    .filter(transaction -> transaction.getTransactionType().equals(transactionType))
                    .toList();
            }
            
            // 模拟分页数据
            int total = allTransactions.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FundingTransactionDTO> pageData = fromIndex < total ? 
                allTransactions.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取资金流水列表失败", e);
            return PageResult.failPage("获取资金流水列表失败");
        }
    }

    /**
     * 创建转账
     */
    @PostMapping("/transfer/create")
    @ResponseBody
    public Result<FundingTransferDTO> createTransfer(@RequestBody FundingTransferDTO transferDTO) {
        try {
            // 生成转账编号
            transferDTO.setTransferCode(generateTransferCode());
            
            // 创建转账
            FundingTransferDTO createdTransfer = fundingService.createTransfer(transferDTO);
            return Result.success("转账创建成功", createdTransfer);
        } catch (Exception e) {
            log.error("创建转账失败", e);
            return Result.fail("创建转账失败");
        }
    }

    /**
     * 冻结资金
     */
    @PutMapping("/account/freeze/{accountCode}")
    @ResponseBody
    public Result<Void> freezeAccount(
            @PathVariable String accountCode,
            @RequestParam BigDecimal amount,
            @RequestParam String reason) {
        try {
            String operatorId = "system"; // 实际应该从会话获取
            boolean success = fundingService.freezeAccount(accountCode, amount, reason, operatorId);
            if (success) {
                return Result.success("资金冻结成功", (Void)null);
            } else {
                return Result.fail("账户不存在");
            }
        } catch (Exception e) {
            log.error("冻结资金失败", e);
            return Result.fail("冻结资金失败");
        }
    }

    /**
     * 解冻资金
     */
    @PutMapping("/account/unfreeze/{accountCode}")
    @ResponseBody
    public Result<Void> unfreezeAccount(
            @PathVariable String accountCode,
            @RequestParam BigDecimal amount) {
        try {
            String operatorId = "system"; // 实际应该从会话获取
            boolean success = fundingService.unfreezeAccount(accountCode, amount, operatorId);
            if (success) {
                return Result.success("资金解冻成功", (Void)null);
            } else {
                return Result.fail("账户不存在");
            }
        } catch (Exception e) {
            log.error("解冻资金失败", e);
            return Result.fail("解冻资金失败");
        }
    }

    /**
     * 生成转账编号
     */
    private String generateTransferCode() {
        // 简单生成规则: TF + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TF" + suffix + random;
    }
}