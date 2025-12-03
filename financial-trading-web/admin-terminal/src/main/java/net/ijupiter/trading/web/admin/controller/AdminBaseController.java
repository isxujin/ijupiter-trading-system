package net.ijupiter.trading.web.admin.controller;

import net.ijupiter.trading.web.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理终端基础控制器
 * 继承common-terminal的BaseController，扩展管理端特有的功能
 */
public abstract class AdminBaseController extends BaseController {

    @Value("${admin.terminal.title:金融交易系统管理平台}")
    protected String systemTitle;

    @Value("${admin.terminal.version:1.0.1-SNAPSHOT}")
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

    /**
     * 获取顶部导航菜单
     */
    protected List<NavigationItem> getNavigationItems() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("首页", "/admin/dashboard"));
        items.add(new NavigationItem("产品", "/admin/products"));
        items.add(new NavigationItem("交易", "/admin/trades"));
        items.add(new NavigationItem("账户", "/admin/accounts"));
        items.add(new NavigationItem("系统", "/admin/settings"));
        return items;
    }

    /**
     * 获取侧边栏菜单
     */
    protected List<MenuItem> getSidebarItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("仪表盘", "/admin/dashboard", "bi bi-speedometer2", true));
        items.add(new MenuItem("产品管理", "/admin/products", "bi bi-box-seam"));
        items.add(new MenuItem("交易管理", "/admin/trades", "bi bi-cash-coin"));
        items.add(new MenuItem("账户管理", "/admin/accounts", "bi bi-people"));
        items.add(new MenuItem("订单查询", "/admin/orders", "bi bi-list-ul"));
        items.add(new MenuItem("系统设置", "/admin/settings", "bi bi-gear"));
        return items;
    }

    /**
     * 导航项DTO
     */
    public static class NavigationItem {
        private String name;
        private String url;

        public NavigationItem(String name, String url) {
            this.name = name;
            this.url = url;
        }

        // Getters
        public String getName() { return name; }
        public String getUrl() { return url; }
    }

    /**
     * 菜单项DTO
     */
    public static class MenuItem {
        private String name;
        private String url;
        private String icon;
        private boolean active;

        public MenuItem(String name, String url, String icon, boolean active) {
            this.name = name;
            this.url = url;
            this.icon = icon;
            this.active = active;
        }

        public MenuItem(String name, String url, String icon) {
            this(name, url, icon, false);
        }

        // Getters
        public String getName() { return name; }
        public String getUrl() { return url; }
        public String getIcon() { return icon; }
        public boolean isActive() { return active; }
    }
}