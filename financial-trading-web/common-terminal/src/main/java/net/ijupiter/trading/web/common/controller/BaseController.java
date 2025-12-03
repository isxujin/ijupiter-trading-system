package net.ijupiter.trading.web.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 终端公共基础控制器
 * 提供公共的模型属性和方法供所有终端模块继承使用
 */
@Slf4j
@Controller
public abstract class BaseController {

    /**
     * 添加全局模型属性
     */
    @ModelAttribute("appVersion")
    public String getAppVersion() {
        return "1.0.1-SNAPSHOT";
    }

    /**
     * 添加WebJars版本信息
     */
    @ModelAttribute("webjarsVersion")
    public WebJarsVersion getWebJarsVersion() {
        return new WebJarsVersion();
    }

    /**
     * WebJars版本信息DTO
     */
    public static class WebJarsVersion {
        public String getBootstrap() {
            return "5.2.3";
        }

        public String getJquery() {
            return "3.6.4";
        }
    }
}