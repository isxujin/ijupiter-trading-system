package net.ijupiter.trading.web.common.controllers;

import net.ijupiter.trading.web.common.models.Result;
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
        return "common/dashboard";
    }

}