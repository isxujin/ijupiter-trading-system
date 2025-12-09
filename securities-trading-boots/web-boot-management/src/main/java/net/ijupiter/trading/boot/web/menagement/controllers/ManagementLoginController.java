package net.ijupiter.trading.boot.web.menagement.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.boot.web.menagement.utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 管理登录控制器
 */
@Slf4j
@Controller
@RequestMapping("/management")
public class ManagementLoginController extends ManagementBaseController {

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            HttpServletRequest request) {
        
        // 如果已经登录，重定向到仪表盘
        if (SecurityUtils.isAuthenticated()) {
            return "redirect:/management/dashboard";
        }
        
        // 添加错误信息
        if (error != null) {
            model.addAttribute("errorMessage", "用户名或密码错误，请重试");
            log.warn("登录失败，IP: {}", request.getRemoteAddr());
        }
        
        if (logout != null) {
            model.addAttribute("successMessage", "您已成功退出登录");
            log.info("用户退出登录，IP: {}", request.getRemoteAddr());
        }
        
        return "management/login";
        // 正确返回值：无需写.html后缀，直接返回"management/login"
        // 错误示例：return "login" / return "management/Login" / return "management/login.html"
    }
}