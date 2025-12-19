package net.ijupiter.trading.boot.web.investment.controllers;

import net.ijupiter.trading.web.common.controllers.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 管理终端基础控制器
 * 继承common-terminal的BaseController，扩展管理端特有的功能
 */
public abstract class InvestmentBaseController extends BaseController {
    /**
     * 添加管理员页面通用模型属性
     */
    @ModelAttribute
    public void addAdminAttributes(Model model) {
        // 当前用户信息
        model.addAttribute("currentUser", "admin");
        model.addAttribute("navigationItems",getNavigationItems());
    }
}