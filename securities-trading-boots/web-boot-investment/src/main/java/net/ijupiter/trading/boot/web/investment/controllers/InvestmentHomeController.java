package net.ijupiter.trading.boot.web.investment.controllers;

import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/investment")
public class InvestmentHomeController extends BaseController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 仪表盘核心数据
        model.addAttribute("totalInvestment", "¥ 12,580,000.00");
        model.addAttribute("investmentGrowth", "+2.5%");
        model.addAttribute("pendingProjects", 8);
        model.addAttribute("totalProfit", "¥ 1,890,000.00");
        model.addAttribute("profitRate", "15.0%");
        model.addAttribute("highRiskProjects", 2);

        // 图表数据
        model.addAttribute("monthLabels", List.of("1月", "2月", "3月", "4月", "5月", "6月"));
        model.addAttribute("investmentData", List.of(1500000, 2000000, 1800000, 2500000, 2200000, 2800000));
        model.addAttribute("profitData", List.of(225000, 300000, 270000, 375000, 330000, 420000));
        model.addAttribute("projectTypeData", List.of(
                new ProjectTypeDTO(40, "新能源"),
                new ProjectTypeDTO(30, "房地产"),
                new ProjectTypeDTO(20, "科技创新"),
                new ProjectTypeDTO(10, "其他")
        ));

        // 近期项目列表
        List<ProjectTypeDTO> recentProjects = new ArrayList<>();
        recentProjects.add(new ProjectTypeDTO(1, "新能源产业园"));
        recentProjects.add(new ProjectTypeDTO(2, "智慧物流园"));
        recentProjects.add(new ProjectTypeDTO(3, "AI研发中心"));
        model.addAttribute("recentProjects", recentProjects);

        return "investment/dashboard"; // 指向templates/investment/dashboard.html
    }

    // 内部数据传输类
    static class ProjectTypeDTO {
        private int value;
        private String name;

        public ProjectTypeDTO(int value, String name) {
            this.value = value;
            this.name = name;
        }

        // getter/setter
        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    /**
     * 处理投资系统根路径，重定向到仪表盘
     */
    @GetMapping({"", "/"})
    public String investmentIndex() {
        return "redirect:/investment/dashboard";
    }
}