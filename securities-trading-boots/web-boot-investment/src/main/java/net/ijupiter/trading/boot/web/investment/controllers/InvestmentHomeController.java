package net.ijupiter.trading.boot.web.investment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/investment")
public class InvestmentHomeController extends InvestmentBaseController {

    /**
     * 显示投资系统仪表盘
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 添加仪表盘特定的模型属性
        model.addAttribute("pageTitle", "投资系统控制台");
        return "investment/dashboard_simple";
    }
    
    /**
     * 处理投资系统根路径，重定向到仪表盘
     */
    @GetMapping({"", "/"})
    public String investmentIndex() {
        return "redirect:/investment/dashboard";
    }
}