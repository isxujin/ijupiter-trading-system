package net.ijupiter.trading.web.management.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class SettingsController extends ManagementBaseController {

    @Value("${management.terminal.title:金融交易系统管理平台}")
    private String systemTitle;

    // 原路径：@GetMapping("/settings") → 改为唯一路径 /settings/manage
    @GetMapping("/settings/manage")
    public String settings(Model model) {
        model.addAttribute("systemTitle", systemTitle);
        model.addAttribute("pageTitle", "系统设置");
        // 其他业务逻辑保持不变
        return "management/settings"; // 对应 templates/management/settings.html 模板
    }

    // 若有其他设置相关方法（如保存/重置），需检查路径是否唯一
}