package net.ijupiter.trading.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminLoginController extends AdminBaseController {

    @GetMapping("/login")
    public String login() {
        // 正确返回值：无需写.html后缀，直接返回"admin/login"
        return "admin/login";
        // 错误示例：return "login" / return "admin/Login" / return "admin/login.html"
    }
}