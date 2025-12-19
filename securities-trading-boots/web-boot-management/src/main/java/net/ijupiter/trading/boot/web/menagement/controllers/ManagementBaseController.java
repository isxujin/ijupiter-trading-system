package net.ijupiter.trading.boot.web.menagement.controllers;

import net.ijupiter.trading.web.common.controllers.BaseController;
import net.ijupiter.trading.web.common.dtos.MenuItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 管理终端基础控制器
 * 继承common-terminal的BaseController，扩展管理端特有的功能
 */
public abstract class ManagementBaseController extends BaseController {

    @Value("${management.terminal.title:证券系统管理平台}")
    protected String systemTitle;

    @Value("${management.terminal.version:1.0.1-SNAPSHOT}")
    protected String systemVersion;

    /**
     * 添加管理员页面通用模型属性
     */
    @ModelAttribute
    public void addAdminAttributes(Model model, Authentication authentication) {
        // 系统信息
        model.addAttribute("systemTitle", systemTitle);
        model.addAttribute("systemVersion", systemVersion);
        
        // 当前用户信息
        if (authentication != null) {
            model.addAttribute("currentUser", authentication.getName());
        }
        
        // 导航菜单
        model.addAttribute("navigationItems", getNavigationItems());
        
        // 侧边栏菜单
        model.addAttribute("sidebarItems", getSidebarItems());
    }

}