package net.ijupiter.trading.web.common.controllers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.common.dtos.SystemEnvironment;
import net.ijupiter.trading.web.common.constants.SystemMenu;
import net.ijupiter.trading.web.common.dtos.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.webjars.WebJarAssetLocator;

import java.util.List;

/**
 * 终端公共基础控制器
 * 提供公共的模型属性和方法供所有终端模块继承使用
 */
@Slf4j
@Controller
public abstract class BaseController {
    @Value("${system.web.title: © iJupiter}")
    private String systemTitle;

    @Value("${system.web.name:iJupiter Trading System}")
    private String systemName;

    @Value("${system.web.version:1.0.1-SNAPSHOT}")
    private String systemVersion;

    // 注入WebJarAssetLocator用于动态获取版本
    @Autowired
    private WebJarAssetLocator webJarAssetLocator;

    /**
     * 添加全局模型属性:系统环境信息
     */
    @ModelAttribute("systemEnvironment")
    public SystemEnvironment getSystemEnvironment() {
        SystemEnvironment systemEnvironment = new SystemEnvironment();
        systemEnvironment.setSystemName(systemName);
        systemEnvironment.setSystemVersion(systemVersion);
        systemEnvironment.setSystemTitle(systemTitle);
        systemEnvironment.setCurrentUserName("Admin");
        return systemEnvironment;
    }

    /**
     * 添加全局模型属性:系统导航菜单项
     */
    @ModelAttribute("navigationItems")
    public List<MenuItem> getNavigationItems(){
        return SystemMenu.getNavigationItems();
    }

    /**
     * 添加全局模型属性:系统边栏菜单项
     */
    @ModelAttribute("sidebarItems")
    public List<MenuItem> getSidebarItems(){
        return SystemMenu.getSidebarItems();
    }

    /**
     * 添加WebJars版本信息
     */
    @ModelAttribute("webjarsVersion")
    public WebJarsVersion getWebJarsVersion() {
        return new WebJarsVersion();
    }

    // 内部类：通过WebJarAssetLocator获取版本
    public class WebJarsVersion {
        // 获取bootstrap版本
        public String getBootstrap() {return webJarAssetLocator.getWebJars().get("bootstrap");}

        public String getJquery() {
            return webJarAssetLocator.getWebJars().get("jquery");
        }

        public String getChartjs() {
            return webJarAssetLocator.getWebJars().get("chartjs");
        }

        public String getBootstrapIcons() {
            return webJarAssetLocator.getWebJars().get("bootstrap-icons");
        }
    }
}