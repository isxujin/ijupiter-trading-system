package net.ijupiter.trading.boot.web.menagement.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.boot.web.menagement.utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 管理端控制器
 */
@Slf4j
@Controller
@RequestMapping("/management")
public class ManagementController {


    

    
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
}