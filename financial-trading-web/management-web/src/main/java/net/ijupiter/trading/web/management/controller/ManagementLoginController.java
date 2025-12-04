package net.ijupiter.trading.web.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class ManagementLoginController extends ManagementBaseController {

    @GetMapping("/login")
    public String login() {
        // 正确返回值：无需写.html后缀，直接返回"management/login"
        return "management/login";
        // 错误示例：return "login" / return "management/Login" / return "management/login.html"
    }
}