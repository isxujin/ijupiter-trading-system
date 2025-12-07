package net.ijupiter.trading.boot.web.investor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/investor")
public class InvestorLoginController extends InvestorBaseController {

    @GetMapping("/login")
    public String login() {
        // 正确返回值：无需写.html后缀，直接返回"investor/login"
        return "investor/login";
        // 错误示例：return "login" / return "investor/Login" / return "investor/login.html"
    }
}