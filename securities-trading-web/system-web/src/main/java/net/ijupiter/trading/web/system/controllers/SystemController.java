package net.ijupiter.trading.web.system.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统模块基础控制器
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    /**
     * 系统首页/仪表盘
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 设置活跃菜单项
        model.addAttribute("activeMenu", "dashboard");

        return "system/dashboard";
    }

}