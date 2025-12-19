package net.ijupiter.trading.web.common.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.web.common.constants.SystemMenu;
import net.ijupiter.trading.web.common.dtos.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.webjars.WebJarAssetLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 终端公共基础控制器
 * 提供公共的模型属性和方法供所有终端模块继承使用
 */
@Slf4j
@Controller
public abstract class BaseController {
    @Value("${system.web.title: © iJupiter}")
    protected String systemTitle;

    @Value("${system.web.name:iJupiter Trading System}")
    protected String systemName;

    @Value("${system.web.version:1.0.1-SNAPSHOT}")
    protected String systemVersion;

    // 注入WebJarAssetLocator用于动态获取版本
    @Autowired
    private WebJarAssetLocator webJarAssetLocator;

    /**
     * 添加全局模型属性:系统标签
     */
    @ModelAttribute("systemTitle")
    public String getSystemTitle() {
        return systemTitle;
    }

    /**
     * 添加全局模型属性:系统名称
     */
    @ModelAttribute("systemName")
    public String getSystemName() {
        return systemName;
    }

    /**
     * 添加全局模型属性:系统版本
     */
    @ModelAttribute("systemVersion")
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * 添加全局模型属性:系统导航菜单项
     */
    @ModelAttribute("navigationItems")
    public List<MenuItem> getNavigationItems(){
        log.info("navigationItems: {}", SystemMenu.getNavigationItems());
        return SystemMenu.getNavigationItems();
    }

    /**
     * 添加全局模型属性:系统边栏菜单项
     */
    @ModelAttribute("sidebarItems")
    public List<MenuItem> getSidebarItems(){
        log.info("getSidebarItems: {}", SystemMenu.getSidebarItems());
        return SystemMenu.getSidebarItems();
    }

    /**
     * 添加WebJars版本信息
     */
    @ModelAttribute("webjarsVersion")
    public WebJarsVersion getWebJarsVersion() {
        return new WebJarsVersion();
    }

    /**
     * 获取通用侧边栏菜单项
     */
    protected List<Map<String, Object>> getCommonSidebarItems(String activeModule) {
        List<Map<String, Object>> sidebarItems = new ArrayList<>();

        // 客户管理菜单
        Map<String, Object> customerMenu = new HashMap<>();
        customerMenu.put("id", "customer");
        customerMenu.put("name", "客户管理");
        customerMenu.put("icon", "bi-person-badge");

        List<Map<String, Object>> customerItems = new ArrayList<>();

        Map<String, Object> customerList = new HashMap<>();
        customerList.put("id", "customer:list");
        customerList.put("name", "客户列表");
        customerList.put("url", "/customer/list");
        customerList.put("active", "customer:view".equals(activeModule));
        customerItems.add(customerList);

        customerMenu.put("items", customerItems);
        sidebarItems.add(customerMenu);

        return sidebarItems;
    }

    // 内部类：通过WebJarAssetLocator获取版本
    public class WebJarsVersion {
        // 获取bootstrap版本
        public String getBootstrap() {
            // 从getWebJars()返回的map中获取"bootstrap"对应的版本
            return webJarAssetLocator.getWebJars().get("bootstrap");
        }

        public String getJquery() {
            return webJarAssetLocator.getWebJars().get("jquery");
        }

        public String getChartjs() {
            return webJarAssetLocator.getWebJars().get("chart.js");
        }

        // 新增：获取 bootstrap-icons 版本
        public String getBootstrapIcons() {
            return webJarAssetLocator.getWebJars().get("bootstrap-icons");
        }
    }
}