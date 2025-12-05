package net.ijupiter.trading.web.common.controller;

import net.ijupiter.trading.web.common.dtos.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 首页控制器示例
 * 展示如何使用BaseController和公共功能
 */
@Controller
public class HomeController extends BaseController {

    /**
     * 首页视图
     */
    @GetMapping("/")
    public String home(Model model) {
        // 添加基础模型属性
        model.addAttribute("title", "欢迎使用iJupiter交易系统");
        
        // 添加导航菜单项
        model.addAttribute("navigationItems", getNavigationItems());
        
        // 添加侧边栏菜单项
        model.addAttribute("sidebarItems", getSidebarItems());
        
        return "common/home";
    }

    /**
     * API示例
     */
    @GetMapping("/api/system/info")
    @ResponseBody
    public ApiResponse<Object> systemInfo() {
        return ApiResponse.success("获取系统信息成功", getSystemInfo());
    }

    /**
     * 获取导航菜单项
     */
    private List<MenuItem> getNavigationItems() {
        return Arrays.asList(
                new MenuItem("首页", "/"),
                new MenuItem("交易", "/trading"),
                new MenuItem("账户", "/account"),
                new MenuItem("查询", "/query")
        );
    }

    /**
     * 获取侧边栏菜单项
     */
    private List<MenuItem> getSidebarItems() {
        return Arrays.asList(
                new MenuItem("仪表盘", "/dashboard", "bi bi-speedometer2", true),
                new MenuItem("交易管理", "/trading", "bi bi-graph-up"),
                new MenuItem("账户管理", "/account", "bi bi-person"),
                new MenuItem("订单查询", "/orders", "bi bi-list-ul"),
                new MenuItem("报表统计", "/reports", "bi bi-bar-chart")
        );
    }

    /**
     * 获取系统信息
     */
    private Object getSystemInfo() {
        return new SystemInfo("iJupiter Trading System", "1.0.1-SNAPSHOT", "基于事件引擎的通用金融交易系统");
    }

    /**
     * 菜单项DTO
     */
    public static class MenuItem {
        private String name;
        private String url;
        private String icon;
        private boolean active;

        public MenuItem(String name, String url) {
            this(name, url, null, false);
        }

        public MenuItem(String name, String url, String icon) {
            this(name, url, icon, false);
        }

        public MenuItem(String name, String url, String icon, boolean active) {
            this.name = name;
            this.url = url;
            this.icon = icon;
            this.active = active;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }

    /**
     * 系统信息DTO
     */
    public static class SystemInfo {
        private String name;
        private String version;
        private String description;

        public SystemInfo(String name, String version, String description) {
            this.name = name;
            this.version = version;
            this.description = description;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}