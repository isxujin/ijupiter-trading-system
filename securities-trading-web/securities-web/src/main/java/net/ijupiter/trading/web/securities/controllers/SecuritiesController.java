package net.ijupiter.trading.web.securities.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.securities.dtos.SecuritiesAccountDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesPositionDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesTransferDTO;
import net.ijupiter.trading.api.securities.services.SecuritiesService;
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
 * 证券管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/securities")
public class SecuritiesController extends BaseController {
    
    @Autowired
    private SecuritiesService securitiesService;

    /**
     * 证券仪表盘页面
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("securities/dashboard");

        try {
            // 获取证券统计信息
            SecuritiesService.SecuritiesStatistics statistics = securitiesService.getSecuritiesStatistics();
            modelAndView.addObject("statistics", statistics);
            
            // 获取最近添加的账户(取前10个)
            List<SecuritiesAccountDTO> recentAccounts = securitiesService.findAll().stream()
                    .sorted((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()))
                    .limit(10)
                    .toList();
            modelAndView.addObject("recentAccounts", recentAccounts);
        } catch (Exception e) {
            log.error("获取证券仪表盘数据失败", e);
            modelAndView.addObject("error", "获取仪表盘数据失败");
        }

        modelAndView.addObject("activeModule", "securities");
        
        return modelAndView;
    }

    /**
     * 证券账户页面
     */
    @GetMapping("/account")
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView("securities/account");
        modelAndView.addObject("activeModule", "securities");
        
        return modelAndView;
    }
    
    /**
     * 转托管页面
     */
    @GetMapping("/transfer")
    public ModelAndView transfer() {
        ModelAndView modelAndView = new ModelAndView("securities/transfer");
        modelAndView.addObject("activeModule", "securities");
        
        return modelAndView;
    }

    /**
     * 获取证券统计数据
     */
    @GetMapping("/statistics")
    @ResponseBody
    public Result<SecuritiesService.SecuritiesStatistics> getStatistics() {
        try {
            SecuritiesService.SecuritiesStatistics statistics = securitiesService.getSecuritiesStatistics();
            return Result.success("获取统计数据成功", statistics);
        } catch (Exception e) {
            log.error("获取统计数据失败", e);
            return Result.fail("获取统计数据失败");
        }
    }

    /**
     * 获取证券账户列表数据
     */
    @GetMapping("/account/data")
    @ResponseBody
    public PageResult<SecuritiesAccountDTO> getAccountList(
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
            List<SecuritiesAccountDTO> allAccounts = securitiesService.findAll();
            
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
            List<SecuritiesAccountDTO> pageData = fromIndex < total ? 
                allAccounts.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取证券账户列表失败", e);
            return PageResult.failPage("获取证券账户列表失败");
        }
    }

    /**
     * 获取证券持仓列表数据
     */
    @GetMapping("/position/data")
    @ResponseBody
    public PageResult<SecuritiesPositionDTO> getPositionList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String customerCode,
            @RequestParam(required = false) String securityCode,
            @RequestParam(required = false) Integer securityType) {
        
        try {
            List<SecuritiesPositionDTO> allPositions = List.of();
            
            // 如果指定了客户代码，获取该客户的持仓记录
            if (customerCode != null && !customerCode.trim().isEmpty()) {
                // 先获取客户ID（这里简化处理，实际应该从客户服务获取）
                List<SecuritiesAccountDTO> accounts = securitiesService.findAll().stream()
                        .filter(a -> customerCode.equals(a.getCustomerCode()))
                        .toList();
                
                if (!accounts.isEmpty()) {
                    Long customerId = accounts.get(0).getCustomerId();
                    allPositions = securitiesService.findPositionsByCustomerId(customerId);
                }
            }
            
            // 应用证券代码过滤
            if (securityCode != null && !securityCode.trim().isEmpty()) {
                allPositions = allPositions.stream()
                    .filter(position -> position.getSecurityCode() != null &&
                            position.getSecurityCode().contains(securityCode))
                    .toList();
            }
            
            // 应用证券类型过滤
            if (securityType != null) {
                allPositions = allPositions.stream()
                    .filter(position -> position.getSecurityType().equals(securityType))
                    .toList();
            }
            
            // 模拟分页数据
            int total = allPositions.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<SecuritiesPositionDTO> pageData = fromIndex < total ? 
                allPositions.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取证券持仓列表失败", e);
            return PageResult.failPage("获取证券持仓列表失败");
        }
    }

    /**
     * 获取证券转托管列表数据
     */
    @GetMapping("/transfer/data")
    @ResponseBody
    public PageResult<SecuritiesTransferDTO> getTransferList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String customerCode,
            @RequestParam(required = false) String transferCode,
            @RequestParam(required = false) Integer transferType,
            @RequestParam(required = false) Integer status) {
        
        try {
            List<SecuritiesTransferDTO> allTransfers = List.of();
            
            // 如果指定了客户代码，获取该客户的转托管记录
            if (customerCode != null && !customerCode.trim().isEmpty()) {
                // 先获取客户ID（这里简化处理，实际应该从客户服务获取）
                List<SecuritiesAccountDTO> accounts = securitiesService.findAll().stream()
                        .filter(a -> customerCode.equals(a.getCustomerCode()))
                        .toList();
                
                if (!accounts.isEmpty()) {
                    Long customerId = accounts.get(0).getCustomerId();
                    allTransfers = securitiesService.findTransfersByCustomerId(customerId);
                }
            }
            
            // 应用转托管编号过滤
            if (transferCode != null && !transferCode.trim().isEmpty()) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getTransferCode() != null &&
                            transfer.getTransferCode().contains(transferCode))
                    .toList();
            }
            
            // 应用转托管类型过滤
            if (transferType != null) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getTransferType().equals(transferType))
                    .toList();
            }
            
            // 应用状态过滤
            if (status != null) {
                allTransfers = allTransfers.stream()
                    .filter(transfer -> transfer.getStatus().equals(status))
                    .toList();
            }
            
            // 模拟分页数据
            int total = allTransfers.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<SecuritiesTransferDTO> pageData = fromIndex < total ? 
                allTransfers.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取证券转托管列表失败", e);
            return PageResult.failPage("获取证券转托管列表失败");
        }
    }

    /**
     * 创建转托管
     */
    @PostMapping("/transfer/create")
    @ResponseBody
    public Result<SecuritiesTransferDTO> createTransfer(@RequestBody SecuritiesTransferDTO transferDTO) {
        try {
            // 生成转托管编号
            transferDTO.setTransferCode(generateTransferCode());
            
            // 创建转托管
            SecuritiesTransferDTO createdTransfer = securitiesService.createTransfer(transferDTO);
            return Result.success("转托管创建成功", createdTransfer);
        } catch (Exception e) {
            log.error("创建转托管失败", e);
            return Result.fail("创建转托管失败");
        }
    }

    /**
     * 生成转托管编号
     */
    private String generateTransferCode() {
        // 简单生成规则: TS + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TS" + suffix + random;
    }
}