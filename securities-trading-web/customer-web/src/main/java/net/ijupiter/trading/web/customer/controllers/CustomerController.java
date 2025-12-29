package net.ijupiter.trading.web.customer.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.services.CustomerService;
import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.models.Result;
import net.ijupiter.trading.web.common.models.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 客户管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
    
    @Autowired
    private CustomerService customerService;

    /**
     * 客户仪表盘页面
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("customer/dashboard");

        try {
            // 获取客户统计信息
            CustomerService.CustomerStatistics statistics = customerService.getCustomerStatistics();
            modelAndView.addObject("statistics", statistics);
            
            // 获取最近添加的客户(取前10个)
            List<CustomerDTO> recentCustomers = customerService.findAll().stream()
                    .sorted((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()))
                    .limit(10)
                    .toList();
            modelAndView.addObject("recentCustomers", recentCustomers);
        } catch (Exception e) {
            log.error("获取客户仪表盘数据失败", e);
            modelAndView.addObject("error", "获取仪表盘数据失败");
        }

        modelAndView.addObject("activeModule", "customer");
        
        return modelAndView;
    }

    // 注意：账户相关功能已移至Funding模块和Securities模块

    /**
     * 获取客户统计数据
     */
    @GetMapping("/statistics")
    @ResponseBody
    public Result<CustomerService.CustomerStatistics> getStatistics() {
        try {
            CustomerService.CustomerStatistics statistics = customerService.getCustomerStatistics();
            return Result.success("获取统计数据成功", statistics);
        } catch (Exception e) {
            log.error("获取统计数据失败", e);
            return Result.fail("获取统计数据失败");
        }
    }

    /**
     * 获取客户列表数据
     */
    @GetMapping("/data")
    @ResponseBody
    public PageResult<CustomerDTO> getCustomerList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String customerCode,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer status) {
        
        try {
            Map<String, Object> searchParams = new HashMap<>();
            if (customerCode != null && !customerCode.trim().isEmpty()) {
                searchParams.put("customerCode", customerCode);
            }
            if (customerName != null && !customerName.trim().isEmpty()) {
                searchParams.put("customerName", customerName);
            }
            if (phone != null && !phone.trim().isEmpty()) {
                searchParams.put("phone", phone);
            }
            if (status != null) {
                searchParams.put("status", status);
            }
            
            // 使用基础服务方法获取所有数据
            List<CustomerDTO> allCustomers = customerService.findAll();
            
            // 应用搜索条件过滤
            if (!searchParams.isEmpty()) {
                allCustomers = allCustomers.stream()
                    .filter(customer -> {
                        boolean match = true;
                        if (searchParams.containsKey("customerCode") && 
                            customer.getCustomerCode() != null &&
                            !customer.getCustomerCode().contains((String) searchParams.get("customerCode"))) {
                            match = false;
                        }
                        if (searchParams.containsKey("customerName") && 
                            customer.getCustomerName() != null &&
                            !customer.getCustomerName().contains((String) searchParams.get("customerName"))) {
                            match = false;
                        }
                        if (searchParams.containsKey("phone") && 
                            customer.getPhone() != null &&
                            !customer.getPhone().contains((String) searchParams.get("phone"))) {
                            match = false;
                        }
                        if (searchParams.containsKey("status") && 
                            !customer.getStatus().equals(searchParams.get("status"))) {
                            match = false;
                        }
                        return match;
                    })
                    .toList();
            }
            
            // 模拟分页数据
            int total = allCustomers.size();
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<CustomerDTO> pageData = fromIndex < total ? 
                allCustomers.subList(fromIndex, toIndex) : List.of();
            
            return PageResult.success(pageNum, pageSize, total, pageData);
        } catch (Exception e) {
            log.error("获取客户列表失败", e);
            return PageResult.failPage("获取客户列表失败");
        }
    }

    // 注意：获取客户账户列表数据功能已移至Funding模块和Securities模块

    /**
     * 获取客户详情
     */
    @GetMapping("/detail/{id}")
    @ResponseBody
    public Result<CustomerDTO> getCustomerDetail(@PathVariable Long id) {
        try {
            CustomerDTO customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("客户不存在"));
            return Result.success("获取客户详情成功", customer);
        } catch (Exception e) {
            log.error("获取客户详情失败", e);
            return Result.fail("获取客户详情失败");
        }
    }

    // 注意：创建客户账户和更新账户状态功能已移至Funding模块和Securities模块
}