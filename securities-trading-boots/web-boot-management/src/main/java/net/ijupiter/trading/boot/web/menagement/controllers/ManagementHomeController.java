package net.ijupiter.trading.boot.web.menagement.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.boot.web.menagement.utils.SecurityUtils;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.dtos.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/management")
public class ManagementHomeController extends BaseController {

    /**
     * 403无权限页面
     */
    @GetMapping("/403")
    public String accessDenied(Model model) {
        SecurityUtils.getCurrentUser().ifPresent(user -> {
            model.addAttribute("currentUser", user);
            log.warn("用户 {} 尝试访问无权限的资源", user.getUsername());
        });
        return "management/403";
    }

    /**
     * 系统首页/仪表盘
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // 设置页面标题
        model.addAttribute("title", "仪表盘");
        
        // 设置活跃菜单项
        model.addAttribute("activeMenu", "dashboard");
        
        // 添加当前用户信息到模型
        // 由于使用内存用户，直接使用认证信息
        if (authentication != null && authentication.isAuthenticated()) {
            // 创建一个简单的用户对象供模板使用
            java.util.Map<String, Object> currentUser = new java.util.HashMap<>();
            currentUser.put("username", authentication.getName());
            currentUser.put("userCode", "ADMIN");
            currentUser.put("realName", "系统管理员");
            currentUser.put("email", "admin@example.com");
            currentUser.put("phone", "13800138000");
            currentUser.put("status", 1);
            
            model.addAttribute("currentUser", currentUser);
            log.info("用户 {} 访问仪表盘", authentication.getName());
        }
        
        // 添加仪表盘数据
        model.addAttribute("dashboardStats", getDashboardStats());
        
        return "management/dashboard";
    }

    /**
     * 产品管理页面 - 重定向到实际产品列表页
     */
    @GetMapping("/products")
    public String productsRedirect() {
        return "redirect:/management/products/list";
    }

    /**
     * 交易管理页面 - 重定向到实际交易列表页
     */
    @GetMapping("/trades")
    public String tradesRedirect() {
        return "redirect:/management/trades/list";
    }

    /**
     * 系统设置页面 - 重定向到实际系统设置页
     */
    @GetMapping("/settings")
    public String settingsRedirect() {
        return "redirect:/management/settings/manage";
    }
    
    /**
     * API接口：获取仪表盘统计数据
     */
    @GetMapping("/api/dashboard/stats")
    @ResponseBody
    public ApiResponse<Object> getDashboardStatsApi() {
        return ApiResponse.success("获取统计数据成功", getDashboardStats());
    }
    
    /**
     * 获取仪表盘统计数据
     */
    private Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", 128);
        stats.put("todayTrades", 245890);
        stats.put("activeAccounts", 3842);
        stats.put("pendingTasks", 12);
        
        // 模拟最近7天的交易数据
        List<Integer> recentTradeData = Arrays.asList(125000, 198000, 156000, 245000, 210000, 178000, 245890);
        stats.put("recentTradeData", recentTradeData);
        
        // 模拟产品分布数据
        Map<String, Integer> productDistribution = new HashMap<>();
        productDistribution.put("股票", 35);
        productDistribution.put("基金", 25);
        productDistribution.put("期货", 20);
        productDistribution.put("债券", 15);
        productDistribution.put("其他", 5);
        stats.put("productDistribution", productDistribution);
        
        return stats;
    }
}