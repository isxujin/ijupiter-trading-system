package net.ijupiter.trading.web.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class TradeController extends AdminBaseController {

    @Value("${admin.terminal.title:金融交易系统管理平台}")
    private String systemTitle;

    // 原路径：@GetMapping("/trades") → 改为唯一路径 /trades/list
    @GetMapping("/trades/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize) {
        model.addAttribute("systemTitle", systemTitle);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("currentPage", pageNum); // 统一使用currentPage变量名
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", 8); // 模拟总页数，实际应从分页查询结果获取
        // 其他业务逻辑保持不变
        return "admin/trades"; // 对应 templates/admin/trades.html 模板
    }

    // 若有其他交易相关方法（如详情/新增），需检查路径唯一性
}